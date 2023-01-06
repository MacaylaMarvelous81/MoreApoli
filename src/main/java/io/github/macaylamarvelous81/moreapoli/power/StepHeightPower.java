package io.github.macaylamarvelous81.moreapoli.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.macaylamarvelous81.moreapoli.MoreApoli;
import net.minecraft.entity.LivingEntity;

/**
 * This power aids in modifying the step height of an entity. The step height of an entity determines whether they
 * are able to walk up a certain height of blocks. For example, the player, which uses the default step height of 0.6,
 * can walk up blocks like carpets and slabs, but can't walk up a full block. A horse, which has a step height of 1,
 * can walk up full blocks.
 *
 * @version 0.0.2-SNAPSHOT
 * @since 0.0.2-SNAPSHOT
 */
public class StepHeightPower extends Power {
	/**
	 * The step height provided to the power.
	 */
	private final float stepHeight;

	/**
	 * Constructs a new instance of the power.
	 *
	 * @param type The power type of this instance.
	 * @param entity The entity to apply this power to.
	 * @param stepHeight The step height provided to the power.
	 */
	public StepHeightPower(
			PowerType<?> type,
			LivingEntity entity,
			float stepHeight
	) {
		super(type, entity);

		this.stepHeight = stepHeight;
	}

	/**
	 * Retrieves the step height stored in the power.
	 *
	 * @return The step height.
	 */
	public float getStepHeight() {
		return stepHeight;
	}

	/**
	 * Creates a new power factory to be registered to Apoli's registry.
	 *
	 * @return The created power factory.
	 */
	public static PowerFactory createFactory() {
		return new PowerFactory<>(
				MoreApoli.identifier("step_height"),
				new SerializableData()
						.add("height", SerializableDataTypes.FLOAT, 0.6f),
				data -> (type, entity) -> new StepHeightPower(
						type,
						entity,
						data.get("height")
				)
		);
	}
}
