package stellar.skid.events.events;

import stellar.skid.events.events.callables.CancellableEvent;

public class EventPostUpdate extends CancellableEvent {
        private float yaw;
        public static float pitch;
        public static boolean rotatingPitch;

        public EventPostUpdate(float yaw, float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public float getYaw() {
            return this.yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getPitch() {
            return this.pitch;
        }

        public void setPitch(float pitch) {
            this.pitch = pitch;
            rotatingPitch = true;
        }
    }

