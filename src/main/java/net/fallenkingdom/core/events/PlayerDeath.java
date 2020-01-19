package net.fallenkingdom.core.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DestructEntityEvent;

import java.util.Optional;

public class PlayerDeath {

    @Listener
    public void onPlayerDeath(DestructEntityEvent.Death e) {
        Player p = (Player) e.getSource();
        Player killer;
        Optional<DamageSource> dsOptional = e.getCause().first(DamageSource.class);

        if (dsOptional.isPresent() && dsOptional.get() instanceof Player) {
            killer = (Player) dsOptional.get();
        }

    }

}
