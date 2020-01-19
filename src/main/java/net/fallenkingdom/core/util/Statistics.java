package net.fallenkingdom.core.util;

import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;

// WIP

public class Statistics {

    String uuid, join_time;
    int player_kills,
    mob_kills,
    deaths,
    WITHER,
    ENDER_DRAGON,
    NAGA,
    LICH,
    MINOSHROOM,
    HYDRA,
    KNIGHT_PHANTOM,
    UR_GHAST,
    ALPHA_YETI,
    SNOW_QUEEN,
    TROLL,
    CHAOS_GUARDIAN;

    private Player player;

    public void Statistics(Player p) {
        this.player = p;
    }

    public void addPlayerKill() {

    }

    public void addMobKill() {

    }

    public void addBossKill() {
        // using boss types to website can display kills for every boss
    }

    public void addDeath() {

    }

    public void addBlockPlaced() {

    }

    public void addBlockTraveled() {

    }

    public void firstTimeJoin() {

    }

    public void usedChat() {

    }

}
