package me.injusttice.neutron.api.events;

import me.injusttice.neutron.api.events.impl.EventType;

import java.lang.reflect.InvocationTargetException;

public abstract class Event {

    protected boolean pre = true;
    public EventType type;

    private boolean cancelled;

    public enum State {
        PRE("PRE", 0),

        POST("POST", 1);

        private State(final String string, final int number) {

        }
    }

    public boolean isPre(){
        if (type == null)
            return false;

        return type == EventType.PRE;
    }
    public boolean isPost(){
        if (type == null)
            return false;

        return type == EventType.POST;
    }

    public Event call() {

        this.cancelled = false;
        call(this);
        return this;
    }

    public boolean isCancelled() {

        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    private static final void call(final Event event) {

        final ArrayHelper<Data> dataList = EventManager.get(event.getClass());

        if (dataList != null) {
            for (final Data data : dataList) {

                try {
                    data.target.invoke(data.source, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
