package dev.xxapfelsaft.phantom.feature.modules;

import com.dwarslooper.cactus.client.event.EventHandler;
import com.dwarslooper.cactus.client.event.impl.ClientTickEvent;
import com.dwarslooper.cactus.client.feature.module.Module;
import com.dwarslooper.cactus.client.feature.module.Module.Options;
import com.dwarslooper.cactus.client.systems.config.settings.impl.IntegerSetting;
import com.dwarslooper.cactus.client.systems.config.settings.impl.StringSetting;
import dev.xxapfelsaft.phantom.PhantomAddon;

public class CustomNameTagModule extends Module {

    // Static fields read by AvatarNameTagMixin
    public static String customText = "";
    public static double yOffset = 0.35;

    private final StringSetting textSetting;
    private final IntegerSetting yOffsetSetting;

    public CustomNameTagModule() {
        super("custom_name_tag", PhantomAddon.CATEGORY, new Options());

        textSetting = new StringSetting("Text", "Sub to me!");
        textSetting.setMaxLength(64);
        mainGroup.add(textSetting);

        yOffsetSetting = new IntegerSetting("YOffset", 35).min(-300).max(300);
        mainGroup.add(yOffsetSetting);
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
        yOffset = yOffsetSetting.get() / 100.0;
    }
}
