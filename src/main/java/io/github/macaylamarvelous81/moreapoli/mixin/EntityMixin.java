package io.github.macaylamarvelous81.moreapoli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.macaylamarvelous81.moreapoli.power.StepHeightPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * A mixin which targets the entity class.
 *
 * @see Entity
 * @version v0.0.2-SNAPSHOT
 * @since v0.0.2-SNAPSHOT
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow public float stepHeight;

	/**
	 * The saved step height, to be restored later. The step height may be modified by other mods or the game
	 * (probably?)
	 */
	private float originalStepHeight;

	/**
	 * Modifies the step height based on step height powers.
	 *
	 * @see Entity#adjustMovementForCollisions(Vec3d)
	 * @param movement Movement before adjusted for collisions.
	 * @param cir The callback info.
	 */
	@Inject(at = @At("HEAD"), method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;")
	private void modifyStepHeight(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
		// Check if this is a living entity.
		Entity self = (Entity)(Object)this;
		if (!(self instanceof LivingEntity livingSelf)) {
			// Only living entities can have powers, so we don't want to affect non-living entities.
			return;
		}

		// Saves the step height to be restored later.
		this.originalStepHeight = this.stepHeight;

		// Get the powers of this entity.
		PowerHolderComponent component = PowerHolderComponent.KEY.get(livingSelf);

		// Find the step height to use.
		float resultHeight = 0.6f;

		for (StepHeightPower power : component.getPowers(StepHeightPower.class)) {
			float height = power.getStepHeight();

			// Skip powers with the default value.
			if (height == 0.6f) {
				continue;
			}

			resultHeight = height;
		}

		// Set the step height.
		this.stepHeight = resultHeight;
	}

	/**
	 * Restores the step height, modified by the modifyStepHeight inject.
	 * 
	 * @see #modifyStepHeight(Vec3d, CallbackInfoReturnable)
	 * @see Entity#adjustMovementForCollisions(Vec3d) 
	 * @param movement
	 * @param cir
	 */
	@Inject(at = @At("TAIL"), method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;")
	private void restoreStepHeight(Vec3d movement, CallbackInfoReturnable<Vec3d> cir) {
		// Check if this is a living entity.
		Entity self = (Entity)(Object)this;
		if (!(self instanceof LivingEntity)) {
			// Only living entities can have powers, so we don't want to affect non-living entities.
			return;
		}

		this.stepHeight = this.originalStepHeight;
	}
}
