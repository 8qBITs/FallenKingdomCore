package fallenkingdomcore.fallenkingdomcore;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "fallenkingdom-core",
        name = "FallenKingdom Core",
        description = "Main plugin for Fallen Kingdom",
        url = "https://fallenkingdom.net",
        authors = {
                "8qBIT"
        }
)
public class FallenKingdomCore {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }
}
