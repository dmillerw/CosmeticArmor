package dmillerw.armor.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.armor.client.gui.GuiCosmeticArmor;
import dmillerw.armor.core.inventory.ContainerCosmeticArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerCosmeticArmor(player, !player.worldObj.isRemote);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new GuiCosmeticArmor(player);
    }
}
