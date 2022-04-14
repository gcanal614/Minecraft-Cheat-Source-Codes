package today.flux.addon.api.event.events;

import lombok.Getter;
import lombok.Setter;
import today.flux.addon.api.event.AddonEvent;

public class EventMove extends AddonEvent {
    @Getter @Setter
    private double x, y, z;
    @Getter @Setter
    private boolean onGround, safeWalk;
}
