package dev.xxapfelsaft.phantom;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PhantomAttackHandler {

    private static final List<Consumer<Entity>> listeners = new ArrayList<>();

    public static void register(Consumer<Entity> listener) {
        listeners.add(listener);
    }

    public static void unregister(Consumer<Entity> listener) {
        listeners.remove(listener);
    }

    public static void onAttack(Entity target) {
        for (Consumer<Entity> listener : listeners) {
            listener.accept(target);
        }
    }
}
