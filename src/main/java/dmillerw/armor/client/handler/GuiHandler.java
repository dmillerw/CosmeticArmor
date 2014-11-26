package dmillerw.armor.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.lwjgl.input.Mouse;

/**
 * @author dmillerw
 */
public class GuiHandler {

    private static final int CHAT_ARRAY_LENGTH = 9;
    private static final int VISUAL_ARRAY_LENGTH = 1;

    private static GuiButton buttonToggleArmor;

    private static boolean mouseClicked = false;

    public static boolean showArmor = true;

    @SubscribeEvent
    public void initGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.gui instanceof ScreenChatOptions))
            return;

        int i = 0;
        int j = CHAT_ARRAY_LENGTH;
        int k;

        for (k = 0; k < j; ++k) {
            ++i;
        }

        if (i % 2 == 1) {
            ++i;
        }

        i += 2;
        j = VISUAL_ARRAY_LENGTH;

        for (k = 0; k < j; ++k) {
            ++i;
        }

        buttonToggleArmor = (new GuiButton(999, event.gui.width / 2 - 155 + i % 2 * 160, event.gui.height / 6 + 24 * (i >> 1), 150, 20, ""));
        updateText();
    }

    @SubscribeEvent
    public void drawGui(GuiScreenEvent.DrawScreenEvent event) {
        if (!(event.gui instanceof ScreenChatOptions))
            return;

        if (buttonToggleArmor != null)
            buttonToggleArmor.drawButton(event.gui.mc, event.mouseX, event.mouseY);

        if (!mouseClicked) {
            if (Mouse.isButtonDown(0)) {
                mouseClicked = true;

                if (buttonToggleArmor.mousePressed(event.gui.mc, event.mouseX, event.mouseY)) {
                    showArmor = !showArmor;
                    updateText();
                    buttonToggleArmor.func_146113_a(event.gui.mc.getSoundHandler());
                }
            }
        } else {
            if (!Mouse.isButtonDown(0))
                mouseClicked = false;
        }
    }

    private void updateText() {
        buttonToggleArmor.displayString = StatCollector.translateToLocalFormatted("options.showArmor", StatCollector.translateToLocal("cosmetic." + (showArmor ? "on" : "off")));
    }
}
