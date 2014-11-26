package dmillerw.armor.client.gui;

import cpw.mods.fml.client.FMLClientHandler;
import dmillerw.armor.client.handler.KeyHandler;
import dmillerw.armor.core.handler.PlayerHandler;
import dmillerw.armor.core.inventory.ContainerCosmeticArmor;
import dmillerw.armor.core.inventory.InventoryArmor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.Arrays;


public class GuiCosmeticArmor extends InventoryEffectRenderer {

    public static final ResourceLocation background = new ResourceLocation("cosmeticarmor", "textures/gui/expanded_inventory.png");

    private EntityPlayer entityPlayer;

    private float xSizeFloat;
    private float ySizeFloat;

    public GuiCosmeticArmor(EntityPlayer entityPlayer) {
        super(new ContainerCosmeticArmor(entityPlayer, !entityPlayer.worldObj.isRemote));
        this.entityPlayer = entityPlayer;
        this.allowUserInput = true;
    }

    @Override
    public void updateScreen() {
        try {
            ((ContainerCosmeticArmor) inventorySlots).inventoryArmor.blockEvents = false;
        } catch (Exception e) {
        }
    }


    @Override
    public void initGui() {
        this.buttonList.clear();
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial) {
        super.drawScreen(mouseX, mouseY, partial);
        this.xSizeFloat = (float) mouseX;
        this.ySizeFloat = (float) mouseY;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        drawPlayerModel(k + 51, l + 75, 30, (float) (k + 51) - this.xSizeFloat, (float) (l + 75 - 50) - this.ySizeFloat, this.mc.thePlayer);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 106, 16, 4210752);
    }

    public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entityLivingBase) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, 50.0F);
        GL11.glScalef((float) (-scale), (float) scale, (float) scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entityLivingBase.renderYawOffset;
        float f3 = entityLivingBase.rotationYaw;
        float f4 = entityLivingBase.rotationPitch;
        float f5 = entityLivingBase.prevRotationYawHead;
        float f6 = entityLivingBase.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entityLivingBase.renderYawOffset = (float) Math.atan((double) (yaw / 40.0F)) * 20.0F;
        entityLivingBase.rotationYaw = (float) Math.atan((double) (yaw / 40.0F)) * 40.0F;
        entityLivingBase.rotationPitch = -((float) Math.atan((double) (pitch / 40.0F))) * 20.0F;
        entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
        entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
        GL11.glTranslatef(0.0F, entityLivingBase.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entityLivingBase.renderYawOffset = f2;
        entityLivingBase.rotationYaw = f3;
        entityLivingBase.rotationPitch = f4;
        entityLivingBase.prevRotationYawHead = f5;
        entityLivingBase.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    private Slot getSlotAtPosition(int mouseX, int mouseY) {
        for (int k = 0; k < this.inventorySlots.inventorySlots.size(); ++k) {
            Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);

            if (this.isMouseOverSlot(slot, mouseX, mouseY)) {
                return slot;
            }
        }

        return null;
    }

    private boolean isMouseOverSlot(Slot slot, int mouseX, int mouseY) {
        return this.func_146978_c(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, mouseX, mouseY);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        } else  if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }

    @Override
    protected void keyTyped(char character, int keycode) {
        if (keycode == KeyHandler.KEY_COSMETIC_INVENTORY.getKeyCode()) {
            this.mc.thePlayer.closeScreen();
        } else
            super.keyTyped(character, keycode);
    }
}
