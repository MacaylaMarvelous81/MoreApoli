package io.github.macaylamarvelous81.moreapoli.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.macaylamarvelous81.moreapoli.power.ActionOnItemDropPower;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Inject(at = @At("TAIL"), method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;")
	private void callActionOnItemDrop(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
		PlayerEntity self = (PlayerEntity)(Object)this;
		ItemEntity itemEntity = cir.getReturnValue();
		// powers of this PlayerEntity
		PowerHolderComponent component = PowerHolderComponent.KEY.get(self);

		for (ActionOnItemDropPower power : component.getPowers(ActionOnItemDropPower.class)) {
			if (power.doesApply(stack)) {
				power.executeActions(stack, itemEntity);
			}
		}
	}
}
