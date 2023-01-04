package io.github.macaylamarvelous81.moreapoli.power.factory;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.macaylamarvelous81.moreapoli.power.ActionOnItemDropPower;
import net.minecraft.util.registry.Registry;

public class PowerFactories {
	public static void register() {
		register(ActionOnItemDropPower.createFactory());
	}

	private static void register(PowerFactory<?> powerFactory) {
		Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
	}
}
