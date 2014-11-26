package dmillerw.armor.core.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.armor.CosmeticArmor;
import io.netty.buffer.ByteBuf;

/**
 * @author dmillerw
 */
public class PacketOpenGUI implements IMessage, IMessageHandler<PacketOpenGUI, IMessage> {

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketOpenGUI message, MessageContext ctx) {
        ctx.getServerHandler().playerEntity.openGui(CosmeticArmor.instance, 0, ctx.getServerHandler().playerEntity.worldObj, 0, 0, 0);
        return null;
    }
}
