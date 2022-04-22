package non.asset.module.impl.player;

import net.minecraft.entity.player.EntityPlayer;
import non.asset.Clarinet;
import non.asset.event.bus.Handler;
import non.asset.event.impl.input.MouseEvent;
import non.asset.module.Module;
import non.asset.utils.OFC.Printer;

import java.awt.*;

public class MCF extends Module {
    public MCF() {
        super("MCF", Category.PLAYER);
    }
    @Handler
    public void onMouse(MouseEvent event) {
        if (event.getButton() == 2 && getMc().objectMouseOver != null && getMc().objectMouseOver.entityHit instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) getMc().objectMouseOver.entityHit;
            String name = player.getName();
            if (Clarinet.INSTANCE.getFriendManager().isFriend(name)) {
                Clarinet.INSTANCE.getFriendManager().removeFriend(name);
                Printer.print("Removed " + name + " as a friend!");
            } else {
                Clarinet.INSTANCE.getFriendManager().addFriend(name);
                Printer.print("Added " + name + " as a friend!");
            }
        }
    }
}
