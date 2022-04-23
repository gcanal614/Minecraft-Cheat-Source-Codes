/*
 * Decompiled with CFR 0.152.
 */
package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import kotlin.reflect.jvm.internal.impl.protobuf.ByteString;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedInputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.LiteralByteString;

class RopeByteString
extends ByteString {
    private static final int[] minLengthByDepth;
    private final int totalLength;
    private final ByteString left;
    private final ByteString right;
    private final int leftLength;
    private final int treeDepth;
    private int hash = 0;

    private RopeByteString(ByteString left, ByteString right) {
        this.left = left;
        this.right = right;
        this.leftLength = left.size();
        this.totalLength = this.leftLength + right.size();
        this.treeDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
    }

    static ByteString concatenate(ByteString left, ByteString right) {
        ByteString result2;
        RopeByteString leftRope;
        RopeByteString ropeByteString = leftRope = left instanceof RopeByteString ? (RopeByteString)left : null;
        if (right.size() == 0) {
            result2 = left;
        } else if (left.size() == 0) {
            result2 = right;
        } else {
            int newLength = left.size() + right.size();
            if (newLength < 128) {
                result2 = RopeByteString.concatenateBytes(left, right);
            } else if (leftRope != null && leftRope.right.size() + right.size() < 128) {
                LiteralByteString newRight = RopeByteString.concatenateBytes(leftRope.right, right);
                result2 = new RopeByteString(leftRope.left, newRight);
            } else if (leftRope != null && leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope.getTreeDepth() > right.getTreeDepth()) {
                RopeByteString newRight = new RopeByteString(leftRope.right, right);
                result2 = new RopeByteString(leftRope.left, newRight);
            } else {
                int newDepth = Math.max(left.getTreeDepth(), right.getTreeDepth()) + 1;
                result2 = newLength >= minLengthByDepth[newDepth] ? new RopeByteString(left, right) : new Balancer().balance(left, right);
            }
        }
        return result2;
    }

    private static LiteralByteString concatenateBytes(ByteString left, ByteString right) {
        int leftSize = left.size();
        int rightSize = right.size();
        byte[] bytes = new byte[leftSize + rightSize];
        left.copyTo(bytes, 0, 0, leftSize);
        right.copyTo(bytes, 0, leftSize, rightSize);
        return new LiteralByteString(bytes);
    }

    @Override
    public int size() {
        return this.totalLength;
    }

    @Override
    protected int getTreeDepth() {
        return this.treeDepth;
    }

    @Override
    protected boolean isBalanced() {
        return this.totalLength >= minLengthByDepth[this.treeDepth];
    }

    @Override
    protected void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        if (sourceOffset + numberToCopy <= this.leftLength) {
            this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        } else if (sourceOffset >= this.leftLength) {
            this.right.copyToInternal(target, sourceOffset - this.leftLength, targetOffset, numberToCopy);
        } else {
            int leftLength = this.leftLength - sourceOffset;
            this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength);
            this.right.copyToInternal(target, 0, targetOffset + leftLength, numberToCopy - leftLength);
        }
    }

    @Override
    void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        if (sourceOffset + numberToWrite <= this.leftLength) {
            this.left.writeToInternal(out, sourceOffset, numberToWrite);
        } else if (sourceOffset >= this.leftLength) {
            this.right.writeToInternal(out, sourceOffset - this.leftLength, numberToWrite);
        } else {
            int numberToWriteInLeft = this.leftLength - sourceOffset;
            this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
            this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }

    @Override
    public String toString(String charsetName) throws UnsupportedEncodingException {
        return new String(this.toByteArray(), charsetName);
    }

    @Override
    public boolean isValidUtf8() {
        int leftPartial = this.left.partialIsValidUtf8(0, 0, this.leftLength);
        int state = this.right.partialIsValidUtf8(leftPartial, 0, this.right.size());
        return state == 0;
    }

    @Override
    protected int partialIsValidUtf8(int state, int offset, int length) {
        int toIndex = offset + length;
        if (toIndex <= this.leftLength) {
            return this.left.partialIsValidUtf8(state, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialIsValidUtf8(state, offset - this.leftLength, length);
        }
        int leftLength = this.leftLength - offset;
        int leftPartial = this.left.partialIsValidUtf8(state, offset, leftLength);
        return this.right.partialIsValidUtf8(leftPartial, 0, length - leftLength);
    }

    public boolean equals(Object other) {
        int cachedOtherHash;
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString otherByteString = (ByteString)other;
        if (this.totalLength != otherByteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        if (this.hash != 0 && (cachedOtherHash = otherByteString.peekCachedHashCode()) != 0 && this.hash != cachedOtherHash) {
            return false;
        }
        return this.equalsFragments(otherByteString);
    }

    private boolean equalsFragments(ByteString other) {
        int thisOffset = 0;
        PieceIterator thisIter = new PieceIterator(this);
        LiteralByteString thisString = (LiteralByteString)thisIter.next();
        int thatOffset = 0;
        PieceIterator thatIter = new PieceIterator(other);
        LiteralByteString thatString = (LiteralByteString)thatIter.next();
        int pos = 0;
        while (true) {
            boolean stillEqual;
            int thisRemaining = thisString.size() - thisOffset;
            int thatRemaining = thatString.size() - thatOffset;
            int bytesToCompare = Math.min(thisRemaining, thatRemaining);
            boolean bl = stillEqual = thisOffset == 0 ? thisString.equalsRange(thatString, thatOffset, bytesToCompare) : thatString.equalsRange(thisString, thisOffset, bytesToCompare);
            if (!stillEqual) {
                return false;
            }
            if ((pos += bytesToCompare) >= this.totalLength) {
                if (pos == this.totalLength) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (bytesToCompare == thisRemaining) {
                thisOffset = 0;
                thisString = (LiteralByteString)thisIter.next();
            } else {
                thisOffset += bytesToCompare;
            }
            if (bytesToCompare == thatRemaining) {
                thatOffset = 0;
                thatString = (LiteralByteString)thatIter.next();
                continue;
            }
            thatOffset += bytesToCompare;
        }
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0) {
            h = this.totalLength;
            if ((h = this.partialHash(h, 0, this.totalLength)) == 0) {
                h = 1;
            }
            this.hash = h;
        }
        return h;
    }

    @Override
    protected int peekCachedHashCode() {
        return this.hash;
    }

    @Override
    protected int partialHash(int h, int offset, int length) {
        int toIndex = offset + length;
        if (toIndex <= this.leftLength) {
            return this.left.partialHash(h, offset, length);
        }
        if (offset >= this.leftLength) {
            return this.right.partialHash(h, offset - this.leftLength, length);
        }
        int leftLength = this.leftLength - offset;
        int leftPartial = this.left.partialHash(h, offset, leftLength);
        return this.right.partialHash(leftPartial, 0, length - leftLength);
    }

    @Override
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(new RopeInputStream());
    }

    @Override
    public ByteString.ByteIterator iterator() {
        return new RopeByteIterator();
    }

    static {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int f1 = 1;
        int f2 = 1;
        while (f2 > 0) {
            numbers.add(f2);
            int temp = f1 + f2;
            f1 = f2;
            f2 = temp;
        }
        numbers.add(Integer.MAX_VALUE);
        minLengthByDepth = new int[numbers.size()];
        for (int i = 0; i < minLengthByDepth.length; ++i) {
            RopeByteString.minLengthByDepth[i] = (Integer)numbers.get(i);
        }
    }

    private class RopeInputStream
    extends InputStream {
        private PieceIterator pieceIterator;
        private LiteralByteString currentPiece;
        private int currentPieceSize;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int mark;

        public RopeInputStream() {
            this.initialize();
        }

        @Override
        public int read(byte[] b2, int offset, int length) {
            if (b2 == null) {
                throw new NullPointerException();
            }
            if (offset < 0 || length < 0 || length > b2.length - offset) {
                throw new IndexOutOfBoundsException();
            }
            return this.readSkipInternal(b2, offset, length);
        }

        @Override
        public long skip(long length) {
            if (length < 0L) {
                throw new IndexOutOfBoundsException();
            }
            if (length > Integer.MAX_VALUE) {
                length = Integer.MAX_VALUE;
            }
            return this.readSkipInternal(null, 0, (int)length);
        }

        private int readSkipInternal(byte[] b2, int offset, int length) {
            int bytesRemaining;
            int count;
            for (bytesRemaining = length; bytesRemaining > 0; bytesRemaining -= count) {
                this.advanceIfCurrentPieceFullyRead();
                if (this.currentPiece == null) {
                    if (bytesRemaining != length) break;
                    return -1;
                }
                int currentPieceRemaining = this.currentPieceSize - this.currentPieceIndex;
                count = Math.min(currentPieceRemaining, bytesRemaining);
                if (b2 != null) {
                    this.currentPiece.copyTo(b2, this.currentPieceIndex, offset, count);
                    offset += count;
                }
                this.currentPieceIndex += count;
            }
            return length - bytesRemaining;
        }

        @Override
        public int read() throws IOException {
            this.advanceIfCurrentPieceFullyRead();
            if (this.currentPiece == null) {
                return -1;
            }
            return this.currentPiece.byteAt(this.currentPieceIndex++) & 0xFF;
        }

        @Override
        public int available() throws IOException {
            int bytesRead = this.currentPieceOffsetInRope + this.currentPieceIndex;
            return RopeByteString.this.size() - bytesRead;
        }

        @Override
        public boolean markSupported() {
            return true;
        }

        @Override
        public void mark(int readAheadLimit) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        @Override
        public synchronized void reset() {
            this.initialize();
            this.readSkipInternal(null, 0, this.mark);
        }

        private void initialize() {
            this.pieceIterator = new PieceIterator(RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private void advanceIfCurrentPieceFullyRead() {
            if (this.currentPiece != null && this.currentPieceIndex == this.currentPieceSize) {
                this.currentPieceOffsetInRope += this.currentPieceSize;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                } else {
                    this.currentPiece = null;
                    this.currentPieceSize = 0;
                }
            }
        }
    }

    private class RopeByteIterator
    implements ByteString.ByteIterator {
        private final PieceIterator pieces;
        private ByteString.ByteIterator bytes;
        int bytesRemaining;

        private RopeByteIterator() {
            this.pieces = new PieceIterator(RopeByteString.this);
            this.bytes = this.pieces.next().iterator();
            this.bytesRemaining = RopeByteString.this.size();
        }

        @Override
        public boolean hasNext() {
            return this.bytesRemaining > 0;
        }

        @Override
        public Byte next() {
            return this.nextByte();
        }

        @Override
        public byte nextByte() {
            if (!this.bytes.hasNext()) {
                this.bytes = this.pieces.next().iterator();
            }
            --this.bytesRemaining;
            return this.bytes.nextByte();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class PieceIterator
    implements Iterator<LiteralByteString> {
        private final Stack<RopeByteString> breadCrumbs = new Stack();
        private LiteralByteString next;

        private PieceIterator(ByteString root) {
            this.next = this.getLeafByLeft(root);
        }

        private LiteralByteString getLeafByLeft(ByteString root) {
            ByteString pos = root;
            while (pos instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString)pos;
                this.breadCrumbs.push(rbs);
                pos = rbs.left;
            }
            return (LiteralByteString)pos;
        }

        private LiteralByteString getNextNonEmptyLeaf() {
            LiteralByteString result2;
            do {
                if (!this.breadCrumbs.isEmpty()) continue;
                return null;
            } while ((result2 = this.getLeafByLeft(this.breadCrumbs.pop().right)).isEmpty());
            return result2;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public LiteralByteString next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            LiteralByteString result2 = this.next;
            this.next = this.getNextNonEmptyLeaf();
            return result2;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static class Balancer {
        private final Stack<ByteString> prefixesStack = new Stack();

        private Balancer() {
        }

        private ByteString balance(ByteString left, ByteString right) {
            this.doBalance(left);
            this.doBalance(right);
            ByteString partialString = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                ByteString newLeft = this.prefixesStack.pop();
                partialString = new RopeByteString(newLeft, partialString);
            }
            return partialString;
        }

        private void doBalance(ByteString root) {
            if (root.isBalanced()) {
                this.insert(root);
            } else if (root instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString)root;
                this.doBalance(rbs.left);
                this.doBalance(rbs.right);
            } else {
                String string = String.valueOf(String.valueOf(root.getClass()));
                throw new IllegalArgumentException(new StringBuilder(49 + string.length()).append("Has a new type of ByteString been created? Found ").append(string).toString());
            }
        }

        private void insert(ByteString byteString) {
            int depthBin = this.getDepthBinForLength(byteString.size());
            int binEnd = minLengthByDepth[depthBin + 1];
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= binEnd) {
                this.prefixesStack.push(byteString);
            } else {
                ByteString left;
                int binStart = minLengthByDepth[depthBin];
                ByteString newTree = this.prefixesStack.pop();
                while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < binStart) {
                    left = this.prefixesStack.pop();
                    newTree = new RopeByteString(left, newTree);
                }
                newTree = new RopeByteString(newTree, byteString);
                while (!this.prefixesStack.isEmpty()) {
                    depthBin = this.getDepthBinForLength(newTree.size());
                    binEnd = minLengthByDepth[depthBin + 1];
                    if (this.prefixesStack.peek().size() >= binEnd) break;
                    left = this.prefixesStack.pop();
                    newTree = new RopeByteString(left, newTree);
                }
                this.prefixesStack.push(newTree);
            }
        }

        private int getDepthBinForLength(int length) {
            int depth = Arrays.binarySearch(minLengthByDepth, length);
            if (depth < 0) {
                int insertionPoint = -(depth + 1);
                depth = insertionPoint - 1;
            }
            return depth;
        }
    }
}

