package dev.xxapfelsaft.phantom.mixin;

import dev.xxapfelsaft.phantom.feature.modules.CustomNameTagModule;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class NameTagMixin {

    @Inject(at = @At("RETURN"), method = "getNameTag", cancellable = true)
    private <T extends Entity> void onGetNameTag(T entity, CallbackInfoReturnable<Component> cir) {
        String customText = CustomNameTagModule.customText;
        if (customText == null || customText.isEmpty()) return;

        Component original = cir.getReturnValue();
        if (original == null) return;

        Component customLine = Component.literal(customText);

        if (CustomNameTagModule.customTextAbove) {
            cir.setReturnValue(Component.empty().append(customLine).append("\n").append(original));
        } else {
            cir.setReturnValue(Component.empty().append(original).append("\n").append(customLine));
        }
    }
}
