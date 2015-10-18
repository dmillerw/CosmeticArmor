package dmillerw.armor.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.armor.asm.StaticMethods;
import net.minecraftforge.client.event.RenderPlayerEvent;

/**
 * @author dmillerw
 */
public class PlayerRenderHandler {

    @SubscribeEvent
    public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
        StaticMethods.renderingArmor = GuiHandler.showArmor;
    }

    @SubscribeEvent
    public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
        StaticMethods.renderingArmor = false;
    }
}
