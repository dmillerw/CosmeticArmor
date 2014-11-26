package dmillerw.armor;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.armor.core.CommonProxy;

/**
 * @author dmillerw
 */
@Mod(modid = "CosmeticArmor", name = "Cosmetic Armor", version = "%MOD_VERSION%")
public class CosmeticArmor {

    @Mod.Instance("CosmeticArmor")
    public static CosmeticArmor instance;

    @SidedProxy(serverSide = "dmillerw.armor.core.CommonProxy", clientSide = "dmillerw.armor.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
