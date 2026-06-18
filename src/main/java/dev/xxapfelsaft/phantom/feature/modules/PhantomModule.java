package dev.xxapfelsaft.phantom.feature.modules;

import dev.xxapfelsaft.phantom.PhantomAddon;
import com.dwarslooper.cactus.client.event.EventHandler;
import com.dwarslooper.cactus.client.event.impl.ClientTickEvent;
import com.dwarslooper.cactus.client.feature.module.Category;
import com.dwarslooper.cactus.client.feature.module.Module;
import com.dwarslooper.cactus.client.feature.module.Module.Options;
import com.dwarslooper.cactus.client.util.game.ChatUtils;

public class PhantomModule extends Module {

    private boolean hasShownGreeting = false;

    public PhantomModule() {
        super("phantomModule", PhantomAddon.CATEGORY, new Options());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ChatUtils.infoPrefix("Phantom", "Phantom mode activated.");
        hasShownGreeting = false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        ChatUtils.infoPrefix("Phantom", "Phantom mode deactivated.");
    }

    @EventHandler
    public void onTick(ClientTickEvent event) {
        if (!active()) return;

        if (!hasShownGreeting) {
            hasShownGreeting = true;
            // Future phantom-related tick logic goes here
        }
    }
}
