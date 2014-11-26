package dmillerw.armor.core;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import dmillerw.armor.CosmeticArmor;
import dmillerw.armor.core.event.EntityEventHandler;
import dmillerw.armor.core.event.PlayerDataHandler;
import dmillerw.armor.core.handler.GuiHandler;
import dmillerw.armor.core.network.PacketHandler;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        EntityEventHandler entityEventHandler = new EntityEventHandler();
        FMLCommonHandler.instance().bus().register(entityEventHandler);
        MinecraftForge.EVENT_BUS.register(entityEventHandler);

        MinecraftForge.EVENT_BUS.register(new PlayerDataHandler());

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
