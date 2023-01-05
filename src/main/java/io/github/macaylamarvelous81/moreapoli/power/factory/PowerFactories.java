package io.github.macaylamarvelous81.moreapoli.power.factory;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.macaylamarvelous81.moreapoli.power.ActionOnItemDroppedPower;
import net.minecraft.util.registry.Registry;

/**
 * Class which registers the mod's power factories into the power factory registry.
 *
 * @version v0.0.1-SNAPSHOT
 * @since v0.0.1-SNAPSHOT
 */
public class PowerFactories {
	/**
	 * Registers the mod's power factories into the power factory registry.
	 *
	 * @version v0.0.1-SNAPSHOT
	 * @since v0.0.1-SNAPSHOT
	 */
	public static void register() {
		register(ActionOnItemDroppedPower.createFactory());
	}

	/**
	 * Registers the provided power factory to Apoli's power factory registry.
	 *
	 * @param powerFactory The power factory to register.
	 */
	private static void register(PowerFactory<?> powerFactory) {
		Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
	}
}
