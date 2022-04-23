/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.message;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.message.Message;

public class ParameterizedMessage
implements Message {
    public static final String RECURSION_PREFIX = "[...";
    public static final String RECURSION_SUFFIX = "...]";
    public static final String ERROR_PREFIX = "[!!!";
    public static final String ERROR_SEPARATOR = "=>";
    public static final String ERROR_MSG_SEPARATOR = ":";
    public static final String ERROR_SUFFIX = "!!!]";
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final char ESCAPE_CHAR = '\\';
    private final String messagePattern;
    private final String[] stringArgs;
    private transient Object[] argArray;
    private transient String formattedMessage;
    private transient Throwable throwable;

    public ParameterizedMessage(String messagePattern, String[] stringArgs, Throwable throwable) {
        this.messagePattern = messagePattern;
        this.stringArgs = stringArgs;
        this.throwable = throwable;
    }

    public ParameterizedMessage(String messagePattern, Object[] objectArgs, Throwable throwable) {
        this.messagePattern = messagePattern;
        this.throwable = throwable;
        this.stringArgs = this.parseArguments(objectArgs);
    }

    public ParameterizedMessage(String messagePattern, Object[] arguments2) {
        this.messagePattern = messagePattern;
        this.stringArgs = this.parseArguments(arguments2);
    }

    public ParameterizedMessage(String messagePattern, Object arg) {
        this(messagePattern, new Object[]{arg});
    }

    public ParameterizedMessage(String messagePattern, Object arg1, Object arg2) {
        this(messagePattern, new Object[]{arg1, arg2});
    }

    private String[] parseArguments(Object[] arguments2) {
        String[] strArgs;
        if (arguments2 == null) {
            return null;
        }
        int argsCount = ParameterizedMessage.countArgumentPlaceholders(this.messagePattern);
        int resultArgCount = arguments2.length;
        if (argsCount < arguments2.length && this.throwable == null && arguments2[arguments2.length - 1] instanceof Throwable) {
            this.throwable = (Throwable)arguments2[arguments2.length - 1];
            --resultArgCount;
        }
        this.argArray = new Object[resultArgCount];
        for (int i = 0; i < resultArgCount; ++i) {
            this.argArray[i] = arguments2[i];
        }
        if (argsCount == 1 && this.throwable == null && arguments2.length > 1) {
            strArgs = new String[]{ParameterizedMessage.deepToString(arguments2)};
        } else {
            strArgs = new String[resultArgCount];
            for (int i = 0; i < strArgs.length; ++i) {
                strArgs[i] = ParameterizedMessage.deepToString(arguments2[i]);
            }
        }
        return strArgs;
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = this.formatMessage(this.messagePattern, this.stringArgs);
        }
        return this.formattedMessage;
    }

    @Override
    public String getFormat() {
        return this.messagePattern;
    }

    @Override
    public Object[] getParameters() {
        if (this.argArray != null) {
            return this.argArray;
        }
        return this.stringArgs;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    protected String formatMessage(String msgPattern, String[] sArgs) {
        return ParameterizedMessage.format(msgPattern, sArgs);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ParameterizedMessage that = (ParameterizedMessage)o;
        if (this.messagePattern != null ? !this.messagePattern.equals(that.messagePattern) : that.messagePattern != null) {
            return false;
        }
        return Arrays.equals(this.stringArgs, that.stringArgs);
    }

    public int hashCode() {
        int result2 = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
        result2 = 31 * result2 + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
        return result2;
    }

    public static String format(String messagePattern, Object[] arguments2) {
        if (messagePattern == null || arguments2 == null || arguments2.length == 0) {
            return messagePattern;
        }
        StringBuilder result2 = new StringBuilder();
        int escapeCounter = 0;
        int currentArgument = 0;
        for (int i = 0; i < messagePattern.length(); ++i) {
            char curChar = messagePattern.charAt(i);
            if (curChar == '\\') {
                ++escapeCounter;
                continue;
            }
            if (curChar == '{' && i < messagePattern.length() - 1 && messagePattern.charAt(i + 1) == '}') {
                int escapedEscapes = escapeCounter / 2;
                for (int j = 0; j < escapedEscapes; ++j) {
                    result2.append('\\');
                }
                if (escapeCounter % 2 == 1) {
                    result2.append('{');
                    result2.append('}');
                } else {
                    if (currentArgument < arguments2.length) {
                        result2.append(arguments2[currentArgument]);
                    } else {
                        result2.append('{').append('}');
                    }
                    ++currentArgument;
                }
                ++i;
                escapeCounter = 0;
                continue;
            }
            if (escapeCounter > 0) {
                for (int j = 0; j < escapeCounter; ++j) {
                    result2.append('\\');
                }
                escapeCounter = 0;
            }
            result2.append(curChar);
        }
        return result2.toString();
    }

    public static int countArgumentPlaceholders(String messagePattern) {
        if (messagePattern == null) {
            return 0;
        }
        int delim = messagePattern.indexOf(123);
        if (delim == -1) {
            return 0;
        }
        int result2 = 0;
        boolean isEscaped = false;
        for (int i = 0; i < messagePattern.length(); ++i) {
            char curChar = messagePattern.charAt(i);
            if (curChar == '\\') {
                isEscaped = !isEscaped;
                continue;
            }
            if (curChar == '{') {
                if (!isEscaped && i < messagePattern.length() - 1 && messagePattern.charAt(i + 1) == '}') {
                    ++result2;
                    ++i;
                }
                isEscaped = false;
                continue;
            }
            isEscaped = false;
        }
        return result2;
    }

    public static String deepToString(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return (String)o;
        }
        StringBuilder str = new StringBuilder();
        HashSet<String> dejaVu = new HashSet<String>();
        ParameterizedMessage.recursiveDeepToString(o, str, dejaVu);
        return str.toString();
    }

    private static void recursiveDeepToString(Object o, StringBuilder str, Set<String> dejaVu) {
        if (o == null) {
            str.append("null");
            return;
        }
        if (o instanceof String) {
            str.append(o);
            return;
        }
        Class<?> oClass = o.getClass();
        if (oClass.isArray()) {
            if (oClass == byte[].class) {
                str.append(Arrays.toString((byte[])o));
            } else if (oClass == short[].class) {
                str.append(Arrays.toString((short[])o));
            } else if (oClass == int[].class) {
                str.append(Arrays.toString((int[])o));
            } else if (oClass == long[].class) {
                str.append(Arrays.toString((long[])o));
            } else if (oClass == float[].class) {
                str.append(Arrays.toString((float[])o));
            } else if (oClass == double[].class) {
                str.append(Arrays.toString((double[])o));
            } else if (oClass == boolean[].class) {
                str.append(Arrays.toString((boolean[])o));
            } else if (oClass == char[].class) {
                str.append(Arrays.toString((char[])o));
            } else {
                String id = ParameterizedMessage.identityToString(o);
                if (dejaVu.contains(id)) {
                    str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
                } else {
                    dejaVu.add(id);
                    Object[] oArray = (Object[])o;
                    str.append("[");
                    boolean first = true;
                    for (Object current : oArray) {
                        if (first) {
                            first = false;
                        } else {
                            str.append(", ");
                        }
                        ParameterizedMessage.recursiveDeepToString(current, str, new HashSet<String>(dejaVu));
                    }
                    str.append("]");
                }
            }
        } else if (o instanceof Map) {
            String id = ParameterizedMessage.identityToString(o);
            if (dejaVu.contains(id)) {
                str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
            } else {
                dejaVu.add(id);
                Map oMap = (Map)o;
                str.append("{");
                boolean isFirst = true;
                Iterator i$ = oMap.entrySet().iterator();
                while (i$.hasNext()) {
                    Map.Entry o1;
                    Map.Entry current = o1 = i$.next();
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        str.append(", ");
                    }
                    Object key = current.getKey();
                    Object value = current.getValue();
                    ParameterizedMessage.recursiveDeepToString(key, str, new HashSet<String>(dejaVu));
                    str.append("=");
                    ParameterizedMessage.recursiveDeepToString(value, str, new HashSet<String>(dejaVu));
                }
                str.append("}");
            }
        } else if (o instanceof Collection) {
            String id = ParameterizedMessage.identityToString(o);
            if (dejaVu.contains(id)) {
                str.append(RECURSION_PREFIX).append(id).append(RECURSION_SUFFIX);
            } else {
                dejaVu.add(id);
                Collection oCol = (Collection)o;
                str.append("[");
                boolean isFirst = true;
                for (Object anOCol : oCol) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        str.append(", ");
                    }
                    ParameterizedMessage.recursiveDeepToString(anOCol, str, new HashSet<String>(dejaVu));
                }
                str.append("]");
            }
        } else if (o instanceof Date) {
            Date date = (Date)o;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            str.append(format.format(date));
        } else {
            try {
                str.append(o.toString());
            }
            catch (Throwable t) {
                str.append(ERROR_PREFIX);
                str.append(ParameterizedMessage.identityToString(o));
                str.append(ERROR_SEPARATOR);
                String msg = t.getMessage();
                String className = t.getClass().getName();
                str.append(className);
                if (!className.equals(msg)) {
                    str.append(ERROR_MSG_SEPARATOR);
                    str.append(msg);
                }
                str.append(ERROR_SUFFIX);
            }
        }
    }

    public static String identityToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
    }

    public String toString() {
        return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString(this.stringArgs) + ", throwable=" + this.throwable + "]";
    }
}

