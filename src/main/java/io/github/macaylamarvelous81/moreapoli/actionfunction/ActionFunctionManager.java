package io.github.macaylamarvelous81.moreapoli.actionfunction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.github.apace100.calio.data.MultiJsonDataLoader;
import io.github.macaylamarvelous81.moreapoli.MoreApoli;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Listens for action functions being loaded by datapacks and registers them.
 */
public class ActionFunctionManager extends MultiJsonDataLoader implements IdentifiableResourceReloadListener {
	/**
	 * The logger belonging to the action function manager.
	 */
	private static final Logger logger = LogManager.getLogger(ActionFunctionManager.class);

	/**
	 * The GSON object for Calio's MultiJsonDataLoader.
	 */
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	/**
	 * Constructs a new instance of the action function manager.
	 */
	public ActionFunctionManager() {
		super(GSON, "action_function");
	}

	/**
	 * Reads the provided JSON elements, parses them into action functions, and registers them. Any elements that fail
	 * will get skipped, and an error will be logged.
	 *
	 * @param loader A map of identifiers and JSON elements which represent action functons.
	 * @param manager The resource manager.
	 * @param profiler The profiler (frankly; I don't know what this is, but it's from the overridden method)
	 */
	@Override
	protected void apply(Map<Identifier, List<JsonElement>> loader, ResourceManager manager, Profiler profiler) {
		loader.forEach((id, elements) -> {
			elements.forEach(element -> {
				try {
					ActionFunction function = ActionFunction.fromJson(id, element.getAsJsonObject());

					if (!ActionFunctionRegistry.contains(id)) {
						// If no action function by this identifier is registered, register this action function.
						ActionFunctionRegistry.register(id, function);
					} else {
						// If there is an action function already registered with this identifier...
						ActionFunction existing = ActionFunctionRegistry.get(id);

						// ...check if this action function has a higher loading priority.
						if (function.getLoadingPriority() > existing.getLoadingPriority()) {
							// If it does, replace the old action function.
							ActionFunctionRegistry.update(id, function);
						}
					}
				} catch (Exception e) {
					logger.error(String.format("Encountered a problem reading action function %s: %s", id, e));
				}
			});
		});
	}

	/**
	 * Retrieves the unique identifier for this listener, for IdentifiableResourceReloadListener. Other listeners that
	 * want to depend on this listener should return this identifier in the getFabricDependencies() method.
	 *
	 * @return The unique identifier of this action function.
	 */
	@Override
	public Identifier getFabricId() {
		return MoreApoli.identifier("action_functions");
	}
}
