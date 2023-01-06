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

/**
 * This power enables triggering actions when the player drops an item. Actions can be triggered on the item, the
 * player dropping the item, and the item entity spawned into the world. Although the power can be given to any living
 * entity, it will only function correctly on players.
 *
 * @version v0.0.1-SNAPSHOT
 * @since v0.0.1-SNAPSHOT
 */
public class ActionOnItemDroppedPower extends Power {
    /**
     * The item condition to test for. The actions will only be triggered if this condition is not specified or
     * evaluates to true.
     */
    private final Predicate<ItemStack> itemCondition;

    /**
     * The entity action to trigger on the player dropping the item. It will not be triggered if it is not specified.
     */
    private final Consumer<Entity> entityAction;

    /**
     * The item action to trigger on a copy of the dropped stack of items.
     */
    private final Consumer<ItemStack> itemAction;

    /**
     * The entity action to trigger on the item entity. An item entity is spawned into the world when a player drops
     * an item, in order to represent it in the game world.
     */
    private final Consumer<ItemEntity> itemEntityAction;

    /**
     * Constructs a new instance of the power.
     *
     * @param type The power type of this instance.
     * @param entity The entity to apply this power to.
     * @param itemCondition The item condition to test for.
     * @param entityAction The entity action to trigger on the player dropping the item.
     * @param itemAction The item action to trigger on a copy of the stack of dropped items.
     * @param itemEntityAction The entity action to trigger on the new item entity.
     */
    public ActionOnItemDroppedPower(
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

    /**
     * Determines whether the actions should be executed at this time.
     *
     * @param itemStack A copy of the stack of items dropped.
     * @return true if the actions should be executed, false otherwise.
     */
    public boolean shouldExecute(ItemStack itemStack) {
        return itemCondition == null || itemCondition.test(itemStack);
    }

    /**
     * Executes the actions provided. Note that the actions are executed, even if the conditions specified are not
     * met.
     *
     * @param itemStack A copy of the stack of items dropped.
     * @param itemEntity The item entity created from the item being dropped.
     */
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

    /**
     * Creates a new power factory to be registered to Apoli's registry.
     *
     * @return The created power factory.
     */
    public static PowerFactory createFactory() {
        return new PowerFactory<>(
                MoreApoli.identifier("action_on_item_dropped"),
                new SerializableData()
                        .add("item_condition", ApoliDataTypes.ITEM_CONDITION, null)
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("item_action", ApoliDataTypes.ITEM_ACTION, null)
                        .add("item_entity_action", ApoliDataTypes.ENTITY_ACTION, null),
                data -> (type, player) -> new ActionOnItemDroppedPower(
                        type,
                        player,
                        data.get("item_condition"),
                        data.get("entity_action"),
                        data.get("item_action"),
                        data.get("item_entity_action"))
        )
                .allowCondition();
    }
}
