package io.github.macaylamarvelous81.moreapoli;

import io.github.macaylamarvelous81.moreapoli.power.factory.PowerFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class MoreApoli implements ModInitializer {
    @Override
    public void onInitialize() {
        PowerFactories.register();
    }

    public static Identifier identifier(String path) {
        return new Identifier("more_apoli", path);
    }
}
