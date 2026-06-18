package dev.xxapfelsaft.phantom.mixin;

import dev.xxapfelsaft.phantom.feature.modules.CustomNameTagModule;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class NameTagMixin {

    private static final double TAG_SPACING = 0.35;

    @Inject(at = @At("TAIL"), method = "submitNameTag")
    private <S extends EntityRenderState> void afterSubmitNameTag(S state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        String text = CustomNameTagModule.customText;
        if (text.isEmpty() || state.nameTag == null) return;

        Vec3 attachment = state.nameTagAttachment;
        double yShift = CustomNameTagModule.customTextAbove ? TAG_SPACING : -TAG_SPACING;

        collector.submitNameTag(
            poseStack,
            new Vec3(attachment.x, attachment.y + yShift, attachment.z),
            0,
            Component.literal(text.replace("&", "§")),
            !state.isDiscrete,
            state.lightCoords,
            state.distanceToCameraSq,
            camera
        );
    }
}
