package dmillerw.armor.core.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

/**
 * @author dmillerw
 */
public class EntityEventHandler {

    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent event) {

    }

    @SubscribeEvent
    public void onPlayerSwitchedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {

    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {

    }
}
