package dmillerw.armor.client.handler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.armor.core.network.PacketHandler;
import dmillerw.armor.core.network.packet.PacketOpenGUI;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * @author dmillerw
 */
public class KeyHandler {

    public static final KeyBinding KEY_COSMETIC_INVENTORY = new KeyBinding("Cosmetic Inventory", Keyboard.KEY_C, "key.categories.inventory");

    public KeyHandler() {
        ClientRegistry.registerKeyBinding(KEY_COSMETIC_INVENTORY);
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.SERVER) return;
        if (event.phase == TickEvent.Phase.START) {
            if (KEY_COSMETIC_INVENTORY.getIsKeyPressed() && FMLClientHandler.instance().getClient().inGameHasFocus) {
                PacketHandler.INSTANCE.sendToServer(new PacketOpenGUI());
            }
        }
    }
}
