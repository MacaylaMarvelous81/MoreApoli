package io.github.macaylamarvelous81.moreapoli.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.macaylamarvelous81.moreapoli.MoreApoli;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnItemDropPower extends Power {
    private final Predicate<ItemStack> itemCondition;
    private final Consumer<Entity> entityAction;
    private final Consumer<ItemStack> itemAction;
    private final Consumer<ItemEntity> itemEntityAction;

    public ActionOnItemDropPower(
            PowerType<?> type,
            LivingEntity entity,
            Predicate<ItemStack> itemCondition,
            Consumer<Entity> entityAction,
            Consumer<ItemStack> itemAction,
            Consumer<ItemEntity> itemEntityAction
    ) {
        super(type, entity);

        this.itemCondition = itemCondition;
        this.entityAction = entityAction;
        this.itemAction = itemAction;
        this.itemEntityAction = itemEntityAction;
    }

    public boolean doesApply(ItemStack itemStack) {
        return itemCondition == null || itemCondition.test(itemStack);
    }

    public void executeActions(ItemStack itemStack, ItemEntity itemEntity) {
        if (itemAction != null) {
            itemAction.accept(itemStack);
        }

        if (entityAction != null) {
            entityAction.accept(entity);
        }

        if (itemEntityAction != null) {
            itemEntityAction.accept(itemEntity);
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MoreApoli.identifier("action_on_item_drop"),
                new SerializableData()
                        .add("item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("item_action", ApoliDataTypes.ITEM_ACTION, null)
                        .add("item_entity_action", ApoliDataTypes.ENTITY_ACTION, null),
                data -> (type, player) -> new ActionOnItemDropPower(type, player,
                        data.get("item_condition"),
                        data.get("entity_action"),
                        data.get("item_action"),
                        data.get("item_entity_action")))
                .allowCondition();
    }
}
