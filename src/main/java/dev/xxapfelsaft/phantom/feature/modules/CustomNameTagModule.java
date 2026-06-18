package dev.xxapfelsaft.phantom.feature.modules;

import com.dwarslooper.cactus.client.event.EventHandler;
import com.dwarslooper.cactus.client.event.impl.ClientTickEvent;
import com.dwarslooper.cactus.client.feature.module.Module;
import com.dwarslooper.cactus.client.feature.module.Module.Options;
import com.dwarslooper.cactus.client.systems.config.settings.impl.EnumSetting;
import com.dwarslooper.cactus.client.systems.config.settings.impl.EnumSetting.INamespaceOverrides;
import com.dwarslooper.cactus.client.systems.config.settings.impl.StringSetting;
import dev.xxapfelsaft.phantom.PhantomAddon;

public class CustomNameTagModule extends Module {

    public enum Position implements INamespaceOverrides {
        below,
        above;

        @Override
        public String getNamespace() {
            return "modules.custom_name_tag.settings.position";
        }
    }

    // Static fields read by NameTagMixin
    public static String customText = "";
    public static boolean customTextAbove = false;

    private final StringSetting textSetting;
    private final EnumSetting<Position> positionSetting;

    public CustomNameTagModule() {
        super("custom_name_tag", PhantomAddon.CATEGORY, new Options());

        textSetting = new StringSetting("Text", "Sub to me!");
        textSetting.setMaxLength(64);
        mainGroup.add(textSetting);

        positionSetting = new EnumSetting<>("Position", Position.below);
        mainGroup.add(positionSetting);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        syncSettings();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        customText = "";
    }

    @EventHandler
    public void onTick(ClientTickEvent event) {
        if (!active()) return;
        syncSettings();
    }

    private void syncSettings() {
        customText = textSetting.get();
        customTextAbove = positionSetting.get() == Position.above;
    }
}
