package io.github.macaylamarvelous81.moreapoli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.macaylamarvelous81.moreapoli.power.ActionOnItemDroppedPower;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * A mixin into the player entity class.
 *
 * @see PlayerEntity
 * @version v0.0.1-SNAPSHOT
 * @since v0.0.1-SNAPSHOT
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	/**
	 * Injected at the 'tail' of the dropItem method. Triggers the actions of the player's 'action on item dropped'
	 * powers, if they have any.
	 *
	 * @see PlayerEntity#dropItem(ItemStack, boolean, boolean)
	 * @see ActionOnItemDroppedPower
	 * @param stack The item stack that was dropped. Note that because this is injected at the 'tail' of the method,
	 *              this stack won't affect the dropped items in the world.
	 * @param throwRandomly Whether the item should be thrown into a random direction.
	 * @param retainOwnership Whether the player should be considered the item's 'thrower'.
	 * @param cir The callback info.
	 */
	@Inject(at = @At("TAIL"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
	private void callActionOnItemDrop(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
		PlayerEntity self = (PlayerEntity)(Object)this;
		ItemEntity itemEntity = cir.getReturnValue();
		// powers of this PlayerEntity
		PowerHolderComponent component = PowerHolderComponent.KEY.get(self);

		for (ActionOnItemDroppedPower power : component.getPowers(ActionOnItemDroppedPower.class)) {
			if (power.shouldExecute(stack)) {
				power.executeActions(stack, itemEntity);
			}
		}
	}
}
