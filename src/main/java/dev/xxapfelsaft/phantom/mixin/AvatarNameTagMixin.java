package dev.xxapfelsaft.phantom.mixin;

import dev.xxapfelsaft.phantom.feature.modules.CustomNameTagModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AvatarNameTagMixin {

    @Inject(at = @At("TAIL"), method = "submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
    private void afterSubmitNameTag(AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, CallbackInfo ci) {
        String text = CustomNameTagModule.customText;
        if (text.isEmpty()) return;
        if (state.nameTag == null) return;

        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var connection = mc.getConnection();
        if (connection == null) return;

        var localInfo = connection.getPlayerInfo(mc.player.getUUID());
        if (localInfo == null) return;

        String profileName = localInfo.getProfile().name();
        if (!state.nameTag.getString().contains(profileName)) return;

        Vec3 attachment = state.nameTagAttachment;
        double yShift = CustomNameTagModule.yOffset;

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
