/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;
import javax.annotation.Nullable;

@Beta
@GwtCompatible
public abstract class Converter<A, B>
implements Function<A, B> {
    private final boolean handleNullAutomatically;
    private transient Converter<B, A> reverse;

    protected Converter() {
        this(true);
    }

    Converter(boolean handleNullAutomatically) {
        this.handleNullAutomatically = handleNullAutomatically;
    }

    protected abstract B doForward(A var1);

    protected abstract A doBackward(B var1);

    @Nullable
    public final B convert(@Nullable A a2) {
        return this.correctedDoForward(a2);
    }

    @Nullable
    B correctedDoForward(@Nullable A a2) {
        if (this.handleNullAutomatically) {
            return a2 == null ? null : (B)Preconditions.checkNotNull(this.doForward(a2));
        }
        return this.doForward(a2);
    }

    @Nullable
    A correctedDoBackward(@Nullable B b2) {
        if (this.handleNullAutomatically) {
            return b2 == null ? null : (A)Preconditions.checkNotNull(this.doBackward(b2));
        }
        return this.doBackward(b2);
    }

    public Iterable<B> convertAll(final Iterable<? extends A> fromIterable) {
        Preconditions.checkNotNull(fromIterable, "fromIterable");
        return new Iterable<B>(){

            @Override
            public Iterator<B> iterator() {
                return new Iterator<B>(){
                    private final Iterator<? extends A> fromIterator;
                    {
                        this.fromIterator = fromIterable.iterator();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.fromIterator.hasNext();
                    }

                    @Override
                    public B next() {
                        return Converter.this.convert(this.fromIterator.next());
                    }

                    @Override
                    public void remove() {
                        this.fromIterator.remove();
                    }
                };
            }
        };
    }

    public Converter<B, A> reverse() {
        Converter<A, B> result2 = this.reverse;
        return result2 == null ? (this.reverse = new ReverseConverter(this)) : result2;
    }

    public <C> Converter<A, C> andThen(Converter<B, C> secondConverter) {
        return new ConverterComposition(this, Preconditions.checkNotNull(secondConverter));
    }

    @Override
    @Deprecated
    @Nullable
    public final B apply(@Nullable A a2) {
        return this.convert(a2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return super.equals(object);
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
        return new FunctionBasedConverter(forwardFunction, backwardFunction);
    }

    public static <T> Converter<T, T> identity() {
        return IdentityConverter.INSTANCE;
    }

    private static final class IdentityConverter<T>
    extends Converter<T, T>
    implements Serializable {
        static final IdentityConverter INSTANCE = new IdentityConverter();
        private static final long serialVersionUID = 0L;

        private IdentityConverter() {
        }

        @Override
        protected T doForward(T t) {
            return t;
        }

        @Override
        protected T doBackward(T t) {
            return t;
        }

        public IdentityConverter<T> reverse() {
            return this;
        }

        @Override
        public <S> Converter<T, S> andThen(Converter<T, S> otherConverter) {
            return Preconditions.checkNotNull(otherConverter, "otherConverter");
        }

        public String toString() {
            return "Converter.identity()";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    private static final class FunctionBasedConverter<A, B>
    extends Converter<A, B>
    implements Serializable {
        private final Function<? super A, ? extends B> forwardFunction;
        private final Function<? super B, ? extends A> backwardFunction;

        private FunctionBasedConverter(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
            this.forwardFunction = Preconditions.checkNotNull(forwardFunction);
            this.backwardFunction = Preconditions.checkNotNull(backwardFunction);
        }

        @Override
        protected B doForward(A a2) {
            return this.forwardFunction.apply(a2);
        }

        @Override
        protected A doBackward(B b2) {
            return this.backwardFunction.apply(b2);
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof FunctionBasedConverter) {
                FunctionBasedConverter that = (FunctionBasedConverter)object;
                return this.forwardFunction.equals(that.forwardFunction) && this.backwardFunction.equals(that.backwardFunction);
            }
            return false;
        }

        public int hashCode() {
            return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
        }

        public String toString() {
            return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
        }
    }

    private static final class ConverterComposition<A, B, C>
    extends Converter<A, C>
    implements Serializable {
        final Converter<A, B> first;
        final Converter<B, C> second;
        private static final long serialVersionUID = 0L;

        ConverterComposition(Converter<A, B> first, Converter<B, C> second) {
            this.first = first;
            this.second = second;
        }

        @Override
        protected C doForward(A a2) {
            throw new AssertionError();
        }

        @Override
        protected A doBackward(C c) {
            throw new AssertionError();
        }

        @Override
        @Nullable
        C correctedDoForward(@Nullable A a2) {
            return this.second.correctedDoForward(this.first.correctedDoForward(a2));
        }

        @Override
        @Nullable
        A correctedDoBackward(@Nullable C c) {
            return this.first.correctedDoBackward(this.second.correctedDoBackward(c));
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ConverterComposition) {
                ConverterComposition that = (ConverterComposition)object;
                return this.first.equals(that.first) && this.second.equals(that.second);
            }
            return false;
        }

        public int hashCode() {
            return 31 * this.first.hashCode() + this.second.hashCode();
        }

        public String toString() {
            return this.first + ".andThen(" + this.second + ")";
        }
    }

    private static final class ReverseConverter<A, B>
    extends Converter<B, A>
    implements Serializable {
        final Converter<A, B> original;
        private static final long serialVersionUID = 0L;

        ReverseConverter(Converter<A, B> original) {
            this.original = original;
        }

        @Override
        protected A doForward(B b2) {
            throw new AssertionError();
        }

        @Override
        protected B doBackward(A a2) {
            throw new AssertionError();
        }

        @Override
        @Nullable
        A correctedDoForward(@Nullable B b2) {
            return this.original.correctedDoBackward(b2);
        }

        @Override
        @Nullable
        B correctedDoBackward(@Nullable A a2) {
            return this.original.correctedDoForward(a2);
        }

        @Override
        public Converter<A, B> reverse() {
            return this.original;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof ReverseConverter) {
                ReverseConverter that = (ReverseConverter)object;
                return this.original.equals(that.original);
            }
            return false;
        }

        public int hashCode() {
            return ~this.original.hashCode();
        }

        public String toString() {
            return this.original + ".reverse()";
        }
    }
}

