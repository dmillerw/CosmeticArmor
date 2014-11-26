package dmillerw.armor.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.armor.core.handler.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author dmillerw
 */
public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent event) {
        if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote && !event.entity.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            PlayerHandler.getArmor(event.entityPlayer).dropItemsAt(event.drops, event.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onPlayerDataLoad(PlayerEvent.LoadFromFile event) {
        PlayerHandler.loadPlayerArmor(event.entityPlayer, event.getPlayerFile("armor"), event.getPlayerFile("armor.bak"));
    }

    @SubscribeEvent
    public void onPlayerDataSaved(PlayerEvent.SaveToFile event) {
        PlayerHandler.savePlayerArmor(event.entityPlayer, event.getPlayerFile("armor"), event.getPlayerFile("armor.bak"));
    }
}
