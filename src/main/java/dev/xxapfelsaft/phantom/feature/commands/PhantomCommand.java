package dev.xxapfelsaft.phantom.feature.commands;

import com.dwarslooper.cactus.client.feature.command.Command;
import com.dwarslooper.cactus.client.util.game.ChatUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.SharedSuggestionProvider;

public class PhantomCommand extends Command {

    public PhantomCommand() {
        super("phantom");
    }

    @Override
    public void build(LiteralArgumentBuilder<SharedSuggestionProvider> builder) {
        builder.then(argument("message", StringArgumentType.greedyString()).executes(context -> {
            String message = StringArgumentType.getString(context, "message");
            ChatUtils.infoPrefix("Phantom", "You whisper: " + message);
            return SINGLE_SUCCESS;
        }));
    }
}
