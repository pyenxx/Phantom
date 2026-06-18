package dev.xxapfelsaft.phantom.feature.modules;

import com.dwarslooper.cactus.client.feature.module.Module;
import com.dwarslooper.cactus.client.feature.module.Module.Options;
import com.dwarslooper.cactus.client.systems.config.settings.impl.EnumSetting;
import com.dwarslooper.cactus.client.systems.config.settings.impl.EnumSetting.INamespaceOverrides;
import dev.xxapfelsaft.phantom.PhantomAddon;
import dev.xxapfelsaft.phantom.PhantomAttackHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public class HitEffectModule extends Module {

    public enum ParticleMode implements INamespaceOverrides {
        flame(ParticleTypes.FLAME),
        crit(ParticleTypes.CRIT),
        enchantedHit(ParticleTypes.ENCHANTED_HIT),
        heart(ParticleTypes.HEART),
        soulFire(ParticleTypes.SOUL_FIRE_FLAME),
        explosion(ParticleTypes.EXPLOSION),
        cloud(ParticleTypes.CLOUD),
        soul(ParticleTypes.SOUL),
        copperFlame(ParticleTypes.COPPER_FIRE_FLAME),
        snowflake(ParticleTypes.SNOWFLAKE),
        electricSpark(ParticleTypes.ELECTRIC_SPARK),
        enchant(ParticleTypes.ENCHANT),
        endRod(ParticleTypes.END_ROD),
        poof(ParticleTypes.POOF),
        portal(ParticleTypes.PORTAL),
        sweepAttack(ParticleTypes.SWEEP_ATTACK),
        totem(ParticleTypes.TOTEM_OF_UNDYING),
        sculkSoul(ParticleTypes.SCULK_SOUL),
        ominousSpawn(ParticleTypes.OMINOUS_SPAWNING);

        private final ParticleOptions particle;

        ParticleMode(ParticleOptions particle) {
            this.particle = particle;
        }

        public ParticleOptions getParticle() {
            return particle;
        }

        @Override
        public String getNamespace() {
            return "modules.hit_effect.settings.particle";
        }
    }

    private final Consumer<Entity> hitListener = this::onHit;
    private final EnumSetting<ParticleMode> particleMode;

    public HitEffectModule() {
        super("hit_effect", PhantomAddon.CATEGORY, new Options());

        particleMode = new EnumSetting<>("Particle", ParticleMode.flame);
        mainGroup.add(particleMode);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        PhantomAttackHandler.register(hitListener);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        PhantomAttackHandler.unregister(hitListener);
    }

    private void onHit(Entity target) {
        if (!active()) return;
        if (Minecraft.getInstance().level == null) return;

        ParticleOptions particle = particleMode.get().getParticle();
        var pos = target.position();
        var level = Minecraft.getInstance().level;

        for (int i = 0; i < 16; i++) {
            double dx = (Math.random() - 0.5) * target.getBbWidth() * 1.5;
            double dy = Math.random() * target.getBbHeight() * 1.5;
            double dz = (Math.random() - 0.5) * target.getBbWidth() * 1.5;
            double vx = (Math.random() - 0.5) * 0.25;
            double vy = Math.random() * 0.2 + 0.05;
            double vz = (Math.random() - 0.5) * 0.25;
            level.addParticle(particle, false, true,
                pos.x + dx, pos.y + dy, pos.z + dz, vx, vy, vz);
        }
    }
}
