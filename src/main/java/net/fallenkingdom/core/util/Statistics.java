package net.fallenkingdom.core.util;

import org.spongepowered.api.entity.living.player.Player;

// WIP

public class Statistics {

    protected String uuid, join_time;
    protected int player_kills,
    mob_kills,
    deaths,
    blocks_placed,
    blocks_traveled,
    chat_messages,
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

    public void Statistics(Player p) {
        this.uuid = p.getIdentifier();
    }

    public void addPlayerKill() {
        this.player_kills++;
    }

    public void addMobKill() {
        this.mob_kills++;
    }

    public void addBossKill(BossType boss) {
        switch(boss) {
            case WITHER:
                this.WITHER++;
                break;
            case ENDER_DRAGON:
                this.ENDER_DRAGON++;
                break;
            case NAGA:
                this.NAGA++;
                break;
            case LICH:
                this.LICH++;
                break;
            case MINOSHROOM:
                this.MINOSHROOM++;
                break;
            case HYDRA:
                this.HYDRA++;
                break;
            case KNIGHT_PHANTOM:
                this.KNIGHT_PHANTOM++;
                break;
            case UR_GHAST:
                this.UR_GHAST++;
                break;
            case ALPHA_YETI:
                this.ALPHA_YETI++;
                break;
            case SNOW_QUEEN:
                this.SNOW_QUEEN++;
                break;
            case TROLL:
                this.TROLL++;
                break;
            case CHAOS_GUARDIAN:
                this.CHAOS_GUARDIAN++;
                break;
            default:
                break;
        }
    }

    public void addDeath() {
        this.deaths++;
    }

    public void addBlockPlaced() {
        this.blocks_placed++;
    }

    public void addBlockTraveled() {
        this.blocks_traveled++;
    }

    public void firstTimeJoin(String time) {
        this.join_time = time;
    }

    public void usedChat() {
        this.chat_messages++;
    }

    public enum BossType {
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
        CHAOS_GUARDIAN
    }

}
