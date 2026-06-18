package dev.xxapfelsaft.phantom.feature.modules;

import com.dwarslooper.cactus.client.gui.hud.element.DynamicHudElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Vector2i;

public class ServerIPElement extends DynamicHudElement<ServerIPElement> {

    private String cachedIP = "";
    private long lastIPCheck = 0;

    public ServerIPElement() {
        super("server_ip");
    }

    @Override
    public ServerIPElement duplicate() {
        return new ServerIPElement();
    }

    @Override
    public boolean canResize() {
        return false;
    }

    @Override
    public void renderContent(GuiGraphicsExtractor context, int x, int y, int width, int height, int mouseX, int mouseY, float tickDelta, boolean focused) {
        updateIP();
        if (cachedIP.isEmpty()) return;

        var mc = Minecraft.getInstance();
        if (mc.font == null) return;

        int color = textColor.get().color();
        context.text(mc.font, cachedIP, x + 2, y + 2, color, textShadows());
    }

    @Override
    public Vector2i getMinSize() {
        updateIP();
        var mc = Minecraft.getInstance();
        if (mc.font == null || cachedIP.isEmpty()) return new Vector2i(80, 14);
        return new Vector2i(mc.font.width(cachedIP) + 4, 14);
    }

    private void updateIP() {
        long now = System.currentTimeMillis();
        if (now - lastIPCheck > 1000) {
            lastIPCheck = now;
            Minecraft mc = Minecraft.getInstance();
            if (mc.getCurrentServer() != null && mc.getCurrentServer().ip != null) {
                cachedIP = mc.getCurrentServer().ip;
            } else if (mc.isLocalServer()) {
                cachedIP = "Singleplayer";
            } else {
                cachedIP = "";
            }
        }
    }
}
