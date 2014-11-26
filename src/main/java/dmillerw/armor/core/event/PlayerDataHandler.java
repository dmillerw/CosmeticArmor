package dmillerw.armor.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.armor.core.handler.PlayerHandler;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author dmillerw
 */
public class PlayerDataHandler {

    @SubscribeEvent
    public void onPlayerDataLoad(PlayerEvent.LoadFromFile event) {
        PlayerHandler.loadPlayerArmor(event.entityPlayer, event.getPlayerFile("armor"), event.getPlayerFile("armor.bak"));
    }

    @SubscribeEvent
    public void onPlayerDataSaved(PlayerEvent.SaveToFile event) {
        PlayerHandler.savePlayerArmor(event.entityPlayer, event.getPlayerFile("armor"), event.getPlayerFile("armor.bak"));
    }
}
