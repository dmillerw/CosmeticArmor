package dmillerw.armor.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.armor.client.handler.GuiHandler;
import dmillerw.armor.client.handler.KeyHandler;
import dmillerw.armor.client.handler.PlayerRenderHandler;
import dmillerw.armor.core.CommonProxy;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        FMLCommonHandler.instance().bus().register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerRenderHandler());
        MinecraftForge.EVENT_BUS.register(new GuiHandler());

        localizationUpdater.registerListener();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getWorldClient();
    }
}
