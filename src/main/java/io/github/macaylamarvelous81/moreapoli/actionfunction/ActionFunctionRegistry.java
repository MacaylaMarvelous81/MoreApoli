package io.github.macaylamarvelous81.moreapoli.actionfunction;

import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * The registry for action functions.
 */
public class ActionFunctionRegistry {
	/**
	 * The logger belonging to the action function registry.
	 */
	private static final Logger logger = LogManager.getLogger(ActionFunctionRegistry.class);

	/**
	 * The action functions registered in this registry, indexed by identifier.
	 */
	private static final HashMap<Identifier, ActionFunction> actionFunctions =
			new HashMap<Identifier, ActionFunction>();

	/**
	 * Registers a new action function to the registry. If an action function with this identifier is already
	 * registered, then an error will be logged and the action function will not be registered.
	 *
	 * @param id The identifier of the action function to register.
	 * @param actionFunction The action function to register.
	 */
	public static void register(Identifier id, ActionFunction actionFunction) {
		if (contains(id)) {
			logger.error(String.format("Attempted to register action function %s, but it's already registered.", id));
			return;
		}

		actionFunctions.put(id, actionFunction);
	}

	/**
	 * Unregisters a registered action function. If there is no action function by the provided identifier registered,
	 * then an error will be logged.
	 *
	 * @param id The identifier of the action function to unregister.
	 */
	public static void unregister(Identifier id) {
		if (!contains(id)) {
			logger.error(String.format("Attempted to unregister action function %s, but it isn't registered."), id);
			return;
		}

		actionFunctions.remove(id);
	}

	/**
	 * Registers an action function if there is no action function by the provided identifier registered. If there is
	 * an action function registered by the provided identifier, it will be replaced.
	 *
	 * @param id The identifier of the action function.
	 * @param actionFunction The new action function.
	 */
	public static void update(Identifier id, ActionFunction actionFunction) {
		if (contains(id)) {
			unregister(id);
		}

		register(id, actionFunction);
	}

	/**
	 * Retrieves a registered action function by its identifier. If there is no action function registered by the
	 * provided identifier, an error will be logged and null will be returned.
	 *
	 * @param id The identifier of the action function to retrieve.
	 * @return The action function, or null if it isn't registered.
	 */
	public static ActionFunction get(Identifier id) {
		if (!contains(id)) {
			logger.error(String.format("Attempted to get action function %s, but it isn't registered."), id);
			return null;
		}

		return actionFunctions.get(id);
	}

	/**
	 * Determines whether an action function is registered by the provided identifier.
	 *
	 * @param id The identifier to check.
	 * @return true if this identifier is registered, false otherwise.
	 */
	public static boolean contains(Identifier id) {
		return actionFunctions.containsKey(id);
	}
}
