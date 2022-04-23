/*
 * Decompiled with CFR 0.152.
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.ThreadInformation;

class BasicThreadInformation
implements ThreadInformation {
    private static final int HASH_SHIFT = 32;
    private static final int HASH_MULTIPLIER = 31;
    private final long id;
    private final String name;
    private final String longName;
    private final Thread.State state;
    private final int priority;
    private final boolean isAlive;
    private final boolean isDaemon;
    private final String threadGroupName;

    public BasicThreadInformation(Thread thread2) {
        this.id = thread2.getId();
        this.name = thread2.getName();
        this.longName = thread2.toString();
        this.state = thread2.getState();
        this.priority = thread2.getPriority();
        this.isAlive = thread2.isAlive();
        this.isDaemon = thread2.isDaemon();
        ThreadGroup group = thread2.getThreadGroup();
        this.threadGroupName = group == null ? null : group.getName();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        BasicThreadInformation that = (BasicThreadInformation)o;
        if (this.id != that.id) {
            return false;
        }
        return !(this.name != null ? !this.name.equals(that.name) : that.name != null);
    }

    public int hashCode() {
        int result2 = (int)(this.id ^ this.id >>> 32);
        result2 = 31 * result2 + (this.name != null ? this.name.hashCode() : 0);
        return result2;
    }

    @Override
    public void printThreadInfo(StringBuilder sb) {
        sb.append("\"").append(this.name).append("\" ");
        if (this.isDaemon) {
            sb.append("daemon ");
        }
        sb.append("prio=").append(this.priority).append(" tid=").append(this.id).append(" ");
        if (this.threadGroupName != null) {
            sb.append("group=\"").append(this.threadGroupName).append("\"");
        }
        sb.append("\n");
        sb.append("\tThread state: ").append(this.state.name()).append("\n");
    }

    @Override
    public void printStack(StringBuilder sb, StackTraceElement[] trace) {
        for (StackTraceElement element : trace) {
            sb.append("\tat ").append(element).append("\n");
        }
    }
}

