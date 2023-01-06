package io.github.macaylamarvelous81.moreapoli.actionfunction;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

/**
 * An action function, similar to mcfunctions, enable triggering actions in succession. They can be invoked by their
 * identifier and be easily reused.
 */
public class ActionFunction {
	/**
	 * The identifier of this action function.
	 */
	private Identifier identifier;

	/**
	 * The bi-entity actions in this action function.
	 */
	private List<ActionFactory<Pair<Entity, Entity>>.Instance> bientityActions;

	/**
	 * The entity actions in this action function.
	 */
	private List<ActionFactory<Entity>.Instance> entityActions;

	/**
	 * The block actions in this action function.
	 */
	private List<ActionFactory<Triple<World, BlockPos, Direction>>.Instance> blockActions;

	/**
	 * The item actions in this action function.
	 */
	private List<ActionFactory<Pair<World, ItemStack>>.Instance> itemActions;

	/**
	 * The loading priority of this action function.
	 */
	private int loadingPriority;

	/**
	 * The data structure of an action function.
	 */
	public static final SerializableData DATA = new SerializableData()
			.add("priority", SerializableDataTypes.INT, 0)
			.add("bientity_actions", ApoliDataTypes.BIENTITY_ACTIONS, Lists.newArrayList())
			.add("entity_actions", ApoliDataTypes.ENTITY_ACTIONS, Lists.newArrayList())
			.add("block_actions", ApoliDataTypes.BLOCK_ACTIONS, Lists.newArrayList())
			.add("item_actions", ApoliDataTypes.ITEM_ACTIONS, Lists.newArrayList());

	/**
	 * Constructs a new action function with the identifier and actions specified.
	 *
	 * @param id The identifier of the action function.
	 * @param bientityActions A list of bi-entity actions to assign to this action function.
	 * @param entityActions A list of entity actions to assign to this action function.
	 * @param blockActions A list of block actions to assign to this action function.
	 * @param itemActions A list of item actions to assign to this action function.
	 */
	public ActionFunction(
			Identifier id,
			int loadingPriority,
			List<ActionFactory<Pair<Entity, Entity>>.Instance> bientityActions,
			List<ActionFactory<Entity>.Instance> entityActions,
			List<ActionFactory<Triple<World, BlockPos, Direction>>.Instance> blockActions,
			List<ActionFactory<Pair<World, ItemStack>>.Instance> itemActions
	) {
		this.identifier = id;
		this.loadingPriority = loadingPriority;
		this.bientityActions = bientityActions;
		this.entityActions = entityActions;
		this.blockActions = blockActions;
		this.itemActions = itemActions;
	}

	/**
	 * Triggers the bi-entity actions in this action function, if any.
	 *
	 * @param target The pair of entities the actions will target.
	 */
	public void executeBientityActions(Pair<Entity, Entity> target) {
		for (ActionFactory<Pair<Entity, Entity>>.Instance action : this.bientityActions) {
			action.accept(target);
		}
	}

	/**
	 * Triggers the entity actions in this function action, if any.
	 *
	 * @param target The entity the actions will target.
	 */
	public void executeEntityActions(Entity target) {
		for (ActionFactory<Entity>.Instance action : this.entityActions) {
			action.accept(target);
		}
	}

	/**
	 * Triggers the block actions in this function action, if any.
	 *
	 * @param target The block the actions will target.
	 */
	public void executeBlockActions(Triple<World, BlockPos, Direction> target) {
		for (ActionFactory<Triple<World, BlockPos, Direction>>.Instance action : this.blockActions) {
			action.accept(target);
		}
	}

	/**
	 * Triggers the item actions in this function action, if any.
	 *
	 * @param target The item the actions will target.
	 */
	public void executeItemActions(Pair<World, ItemStack> target) {
		for (ActionFactory<Pair<World, ItemStack>>.Instance action : this.itemActions) {
			action.accept(target);
		}
	}

	public int getLoadingPriority() {
		return this.loadingPriority;
	}

	/**
	 * Writes the action function to a buffer.
	 *
	 * @param buffer The buffer to write to.
	 */
	public void write(PacketByteBuf buffer) {
		SerializableData.Instance data = DATA.new Instance();

		data.set("priority", this.loadingPriority);
		data.set("bientity_actions", this.bientityActions);
		data.set("entity_actions", this.entityActions);
		data.set("block_actions", this.blockActions);
		data.set("item_actions", this.itemActions);

		DATA.write(buffer, data);
	}

	/**
	 * Creates a new action function from a data instance.
	 *
	 * @param id The identifier of the action function.
	 * @param data The data instance.
	 * @return The action function.
	 */
	public static ActionFunction createFromData(Identifier id, SerializableData.Instance data) {
		return new ActionFunction(
				id,
				data.get("priority"),
				data.get("bientity_actions"),
				data.get("entity_actions"),
				data.get("block_actions"),
				data.get("item_actions")
		);
	}

	/**
	 * Creates a new action function from the data in a buffer. Only exists on the physical client.
	 *
	 * @param buffer The buffer to use.
	 * @return The action function.
	 */
	@Environment(EnvType.CLIENT)
	public static ActionFunction read(PacketByteBuf buffer) {
		Identifier id = Identifier.tryParse(buffer.readString(32767));
		return createFromData(id, DATA.read(buffer));
	}

	/**
	 * Creates a new action function from a JSON object.
	 *
	 * @param id The identifier of the action function.
	 * @param json The JSON object to use.
	 * @return The action function.
	 */
	public static ActionFunction fromJson(Identifier id, JsonObject json) {
		return createFromData(id, DATA.read(json));
	}
}
