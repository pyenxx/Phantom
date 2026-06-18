package dev.xxapfelsaft.phantom.feature.modules;

import com.dwarslooper.cactus.client.event.EventHandler;
import com.dwarslooper.cactus.client.event.impl.RawKeyEvent;
import com.dwarslooper.cactus.client.event.impl.MouseClickEvent;
import com.dwarslooper.cactus.client.event.impl.render.RenderHUDEvent;
import com.dwarslooper.cactus.client.feature.module.Module;
import com.dwarslooper.cactus.client.feature.module.Module.Options;
import dev.xxapfelsaft.phantom.PhantomAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class KeystrokesModule extends Module {

    private static final int KEY_SIZE = 22;
    private static final int GAP = 2;

    private final Map<String, Boolean> keyStates = new HashMap<>();
    private int mouseLmb = 0; // 0 = up, >0 = down (with cooldown ticks)
    private int mouseRmb = 0;

    public KeystrokesModule() {
        super("keystrokes", PhantomAddon.CATEGORY, new Options());
        keyStates.put("W", false);
        keyStates.put("A", false);
        keyStates.put("S", false);
        keyStates.put("D", false);
        keyStates.put("Space", false);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        keyStates.replaceAll((k, v) -> false);
        mouseLmb = 0;
        mouseRmb = 0;
    }

    @EventHandler
    public void onKey(RawKeyEvent event) {
        if (!active()) return;

        int key = event.getKey();
        boolean pressed = event.getAction() != 0;

        switch (key) {
            case GLFW.GLFW_KEY_W -> keyStates.put("W", pressed);
            case GLFW.GLFW_KEY_A -> keyStates.put("A", pressed);
            case GLFW.GLFW_KEY_S -> keyStates.put("S", pressed);
            case GLFW.GLFW_KEY_D -> keyStates.put("D", pressed);
            case GLFW.GLFW_KEY_SPACE -> keyStates.put("Space", pressed);
        }
    }

    @EventHandler
    public void onMouse(MouseClickEvent event) {
        if (!active()) return;

        boolean pressed = event.getAction() != 0;
        if (event.getButton() == 0) {
            mouseLmb = pressed ? 4 : 0;
        } else if (event.getButton() == 1) {
            mouseRmb = pressed ? 4 : 0;
        }
    }

    @EventHandler
    public void onRenderHUD(RenderHUDEvent event) {
        if (!active()) return;

        // Decay mouse state over a few ticks so release doesn't vanish instantly
        if (mouseLmb > 0) mouseLmb--;
        if (mouseRmb > 0) mouseRmb--;

        GuiGraphics ctx = event.context();
        Minecraft mc = Minecraft.getInstance();

        int x = 4;
        int y = mc.getWindow().getGuiScaledHeight() - 4 - KEY_SIZE * 2 - GAP;

        // WASD row
        drawKey(ctx, x + KEY_SIZE + GAP, y, "W", keyStates.getOrDefault("W", false));
        drawKey(ctx, x, y + KEY_SIZE + GAP, "A", keyStates.getOrDefault("A", false));
        drawKey(ctx, x + KEY_SIZE + GAP, y + KEY_SIZE + GAP, "S", keyStates.getOrDefault("S", false));
        drawKey(ctx, x + (KEY_SIZE + GAP) * 2, y + KEY_SIZE + GAP, "D", keyStates.getOrDefault("D", false));

        // Space bar (wide)
        int spaceY = y + (KEY_SIZE + GAP) * 2;
        int spaceWidth = KEY_SIZE * 3 + GAP * 2;
        drawRect(ctx, x, spaceY, spaceWidth, KEY_SIZE, keyStates.getOrDefault("Space", false));

        // Mouse buttons
        int mouseX = x + spaceWidth + GAP + 4;
        drawRect(ctx, mouseX, y, KEY_SIZE, KEY_SIZE, mouseLmb > 0);
        drawRect(ctx, mouseX, y + KEY_SIZE + GAP, KEY_SIZE, KEY_SIZE, mouseRmb > 0);

        if (mc.font != null) {
            ctx.drawString(mc.font, "LMB", mouseX + 1, y + 7, mouseLmb > 0 ? 0xFFFFFFFF : 0xFF888888, false);
            ctx.drawString(mc.font, "RMB", mouseX + 1, y + KEY_SIZE + GAP + 7, mouseRmb > 0 ? 0xFFFFFFFF : 0xFF888888, false);
        }
    }

    private void drawKey(GuiGraphics ctx, int x, int y, String label, boolean pressed) {
        drawRect(ctx, x, y, KEY_SIZE, KEY_SIZE, pressed);
        if (Minecraft.getInstance().font != null) {
            ctx.drawString(Minecraft.getInstance().font, label, x + 7, y + 7, pressed ? 0xFFFFFFFF : 0xFF888888, false);
        }
    }

    private void drawRect(GuiGraphics ctx, int x, int y, int w, int h, boolean pressed) {
        int bg = pressed ? 0x80FFFFFF : 0x40000000;
        int border = pressed ? 0xFFFFFFFF : 0xFF666666;
        ctx.fill(x, y, x + w, y + h, bg);
        ctx.renderOutline(x, y, w, h, border);
    }
}
