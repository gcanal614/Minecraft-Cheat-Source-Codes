package stellar.skid.gui.screen.test;

import stellar.skid.StellarWare;
import stellar.skid.gui.NovoGuiScreen;
import stellar.skid.gui.button.FunctionalButton;
import stellar.skid.gui.group2.BasicRoundedGroupWithTitle;
import stellar.skid.gui.label.BasicLabel;
import static stellar.skid.utils.fonts.impl.Fonts.SFBOLD.SFBOLD_16.SFBOLD_16;
import stellar.skid.utils.notifications.NotificationType;

/**
 * @author xDelsy
 */
public class TestScreen extends NovoGuiScreen {

    @Override
    protected void onInitialize() {
        register(new FunctionalButton(new BasicLabel("Test label", 0xFF00FF00, SFBOLD_16), 10, 10, a -> {
            StellarWare.getInstance().getNotificationManager().pop("Clicked " + a, NotificationType.SUCCESS);
        }));
            register(new BasicRoundedGroupWithTitle(new BasicLabel("", 0xFF00FF00, SFBOLD_16), 8, 10, 40, 400, 200));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
