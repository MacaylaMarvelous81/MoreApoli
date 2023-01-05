package io.github.macaylamarvelous81.moreapoli.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * An entrypoint of the mod unique to the client. Currently, it goes unused.
 *
 * @version v0.0.1-SNAPSHOT
 * @since v0.0.1-SNAPSHOT
 */
@Environment(EnvType.CLIENT)
public class MoreApoliClient implements ClientModInitializer {
    /**
     * Does whatever the mod needs to do to initialize in the client. Currently, that means nothing.
     */
    @Override
    public void onInitializeClient() {

    }
}
