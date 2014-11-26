package dmillerw.armor.client.handler;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.armor.core.handler.PlayerHandler;
import dmillerw.armor.core.inventory.InventoryArmor;
import dmillerw.armor.core.item.ItemArmorSkin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.Map;

/**
 * @author dmillerw
 */
public class PlayerRenderHandler {

    private Map<String, ItemStack[]> armorCache = Maps.newHashMap();

    @SubscribeEvent
    public void renderPlayerEventPre(RenderPlayerEvent.Pre event) {
        ItemStack[] cache = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
            cache[i] = event.entityPlayer.inventory.armorItemInSlot(3 - i).copy();
        }

        armorCache.put(event.entityPlayer.getCommandSenderName(), cache);

        InventoryArmor inventoryArmor = PlayerHandler.getArmor(event.entityPlayer);

        for (int i = 0; i < 4; i++) {
            ItemStack cosmetic = inventoryArmor.getStackInSlot(i);
            if (cosmetic != null) {
                if (cosmetic.getItem() instanceof ItemArmorSkin)
                    event.entityPlayer.inventory.armorInventory[3 - i] = null;
                else
                    event.entityPlayer.inventory.armorInventory[3 - i] = cosmetic;
            }
        }
    }

    @SubscribeEvent
    public void renderPlayerEventPost(RenderPlayerEvent.Post event) {
        ItemStack[] cache = armorCache.get(event.entityPlayer.getCommandSenderName());
        for (int i = 0; i < 4; i++) {
            event.entityPlayer.inventory.armorInventory[3 - i] = cache[i];
        }

        armorCache.remove(event.entityPlayer.getCommandSenderName());
    }
}
