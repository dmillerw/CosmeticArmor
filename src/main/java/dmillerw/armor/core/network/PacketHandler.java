package dmillerw.armor.core.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.armor.core.network.packet.PacketOpenGUI;
import dmillerw.armor.core.network.packet.PacketSyncArmor;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dmillerw
 */
public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("CosmeticArmor");

    public static void initialize() {
        INSTANCE.registerMessage(PacketOpenGUI.class, PacketOpenGUI.class, 0, Side.SERVER);
        INSTANCE.registerMessage(PacketSyncArmor.class, PacketSyncArmor.class, 1, Side.CLIENT);
    }

    public static void sendToPlayer(IMessage message, EntityPlayerMP entityPlayerMP) {
        INSTANCE.sendTo(message, entityPlayerMP);
    }

    public static void sendToDimension(IMessage message, int dimension) {
        INSTANCE.sendToDimension(message, dimension);
    }
}
