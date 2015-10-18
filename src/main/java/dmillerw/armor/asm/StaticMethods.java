package dmillerw.armor.asm;

import dmillerw.armor.core.handler.PlayerHandler;
import dmillerw.armor.core.item.ItemArmorSkin;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class StaticMethods {

    public static boolean renderingArmor = false;

    public static ItemStack armorInSlot(InventoryPlayer inventoryPlayer, int slot) {
        if (renderingArmor) {
            ItemStack armor = PlayerHandler.getArmor(inventoryPlayer.player).getStackInSlot(3 - slot);
            if (armor == null)
                return inventoryPlayer.armorInventory[slot];
            if (armor.getItem() instanceof ItemArmorSkin)
                return null;
            return armor;
        } else {
            return inventoryPlayer.armorInventory[slot];
        }
    }
}
