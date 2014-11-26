package dmillerw.armor.core.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class ItemArmorSkin extends ItemArmor {

    public ItemArmorSkin(ArmorMaterial armorMaterial, int materialType, int armorType) {
        super(armorMaterial, materialType, armorType);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, int armorType, Entity entity) {
        return false;
    }
}
