package dmillerw.armor.core.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.armor.CosmeticArmor;
import dmillerw.armor.core.handler.PlayerHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import java.io.IOException;

/**
 * @author dmillerw
 */
public class PacketSyncArmor implements IMessage, IMessageHandler<PacketSyncArmor, IMessage> {

    public int entityId;
    public int slot;

    public ItemStack itemStack;

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeByte(slot);
        buffer.writeInt(entityId);
        PacketBuffer pb = new PacketBuffer(buffer);
        try {
            pb.writeItemStackToBuffer(itemStack);
        } catch (IOException e) {
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        slot = buffer.readByte();
        entityId = buffer.readInt();
        PacketBuffer pb = new PacketBuffer(buffer);
        try {
            itemStack = pb.readItemStackFromBuffer();
        } catch (IOException e) {
        }
    }

    @Override
    public IMessage onMessage(PacketSyncArmor message, MessageContext ctx) {
        World world = CosmeticArmor.proxy.getClientWorld();
        if (world == null) return null;
        Entity p = world.getEntityByID(message.entityId);
        if (p != null && p instanceof EntityPlayer) {
            PlayerHandler.getArmor((EntityPlayer) p).stackList[message.slot] = message.itemStack;
        }
        return null;
    }
}
