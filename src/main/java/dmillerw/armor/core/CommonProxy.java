package dmillerw.armor.core;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.armor.CosmeticArmor;
import dmillerw.armor.core.event.PlayerEventHandler;
import dmillerw.armor.core.handler.GuiHandler;
import dmillerw.armor.core.handler.LocalizationUpdater;
import dmillerw.armor.core.item.ItemArmorSkin;
import dmillerw.armor.core.network.PacketHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public static Item armorSkinHelmet;
    public static Item armorSkinChestplate;
    public static Item armorSkinLeggings;
    public static Item armorSkinBoots;

    public static Configuration configuration;

    public void preInit(FMLPreInitializationEvent event) {
        armorSkinHelmet = new ItemArmorSkin(ItemArmor.ArmorMaterial.CLOTH, 0, 0).setUnlocalizedName("skinHelmet").setTextureName("leather_helmet");
        armorSkinChestplate = new ItemArmorSkin(ItemArmor.ArmorMaterial.CLOTH, 0, 1).setUnlocalizedName("skinChestplate").setTextureName("leather_chestplate");
        armorSkinLeggings = new ItemArmorSkin(ItemArmor.ArmorMaterial.CLOTH, 0, 2).setUnlocalizedName("skinLeggings").setTextureName("leather_leggings");
        armorSkinBoots = new ItemArmorSkin(ItemArmor.ArmorMaterial.CLOTH, 0, 3).setUnlocalizedName("skinBoots").setTextureName("leather_boots");

        GameRegistry.registerItem(armorSkinHelmet, "skinHelmet");
        GameRegistry.registerItem(armorSkinChestplate, "skinChestplate");
        GameRegistry.registerItem(armorSkinLeggings, "skinLeggings");
        GameRegistry.registerItem(armorSkinBoots, "skinBoots");

        GameRegistry.addShapedRecipe(
                new ItemStack(armorSkinHelmet),
                "WWW",
                "WLW",
                'W', Items.wheat,
                'L', Items.leather_helmet
        );

        GameRegistry.addShapedRecipe(
                new ItemStack(armorSkinChestplate),
                "WLW",
                "WWW",
                "WWW",
                'W', Items.wheat,
                'L', Items.leather_chestplate
        );

        GameRegistry.addShapedRecipe(
                new ItemStack(armorSkinLeggings),
                "WWW",
                "WLW",
                "W W",
                'W', Items.wheat,
                'L', Items.leather_leggings
        );

        GameRegistry.addShapedRecipe(
                new ItemStack(armorSkinBoots),
                "WLW",
                "W W",
                'W', Items.wheat,
                'L', Items.leather_boots
        );

        configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        LocalizationUpdater.initializeThread(configuration);

        if (configuration.hasChanged())
            configuration.save();

        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(CosmeticArmor.instance, new GuiHandler());

        PacketHandler.initialize();
    }

    public void init(FMLInitializationEvent event) {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public World getClientWorld() {
        return null;
    }
}
