package dmillerw.armor.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.server.FMLServerHandler;
import dmillerw.armor.core.CommonProxy;
import dmillerw.armor.core.handler.PlayerHandler;
import dmillerw.armor.core.inventory.InventoryArmor;
import dmillerw.armor.core.network.PacketHandler;
import dmillerw.armor.core.network.packet.PacketSyncArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author dmillerw
 */
public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.player.worldObj.isRemote) {
            for (int i=0; i<4; i++) {
                // Send logged in Player's armor to all other players
                PlayerHandler.getArmor(event.player).syncSlotToClients(i);

                // Send all other Player's armor to logged in player
                for (Object obj : FMLServerHandler.instance().getServer().getConfigurationManager().playerEntityList) {
                    InventoryArmor inventoryArmor = PlayerHandler.getArmor((EntityPlayer) obj);
                    PacketSyncArmor packetSyncArmor = new PacketSyncArmor();
                    packetSyncArmor.entityId = ((EntityPlayer)obj).getEntityId();
                    packetSyncArmor.slot = i;
                    packetSyncArmor.itemStack = inventoryArmor.getStackInSlot(i);
                    PacketHandler.INSTANCE.sendTo(packetSyncArmor, (EntityPlayerMP) obj);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent event) {
        if (event.entity instanceof EntityPlayer &&
            !event.entity.worldObj.isRemote &&
            !event.entity.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory") &&
            CommonProxy.dropOnDeath) {

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
