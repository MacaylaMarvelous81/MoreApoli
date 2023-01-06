package io.github.macaylamarvelous81.moreapoli;

import io.github.apace100.calio.resource.OrderedResourceListenerInitializer;
import io.github.apace100.calio.resource.OrderedResourceListenerManager;
import io.github.macaylamarvelous81.moreapoli.actionfunction.ActionFunctionManager;
import io.github.macaylamarvelous81.moreapoli.power.factory.PowerFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

/**
 * The main entrypoint for the mod.
 *
 * @version v0.0.1-SNAPSHOT
 * @since v0.0.1-SNAPSHOT
 */
public class MoreApoli implements ModInitializer, OrderedResourceListenerInitializer {
    /**
     * Does whatever the mod needs to do to initialize.
     */
    @Override
    public void onInitialize() {
        PowerFactories.register();
    }

    /**
     * Registers the resource listeners of this mod.
     *
     * @param manager The resource listener manager to register the resource listeners to.
     */
    @Override
    public void registerResourceListeners(OrderedResourceListenerManager manager) {
        // Register action function resource listener
        ActionFunctionManager actionFunctionManager = new ActionFunctionManager();
        manager.register(ResourceType.SERVER_DATA, actionFunctionManager);
    }

    /**
     * Gets a namespaced identifier unique to the mod. For example, if the path provided is 'path', then an identifier
     * 'more_apoli:path' would be returned.
     *
     * @param path The path of the identifier.
     * @return The namespaced identifier.
     */
    public static Identifier identifier(String path) {
        return new Identifier("more_apoli", path);
    }
}
