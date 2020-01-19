package net.fallenkingdom.core.events;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

import java.util.Optional;

public class Chat {
	@Listener
	public void onChat(MessageChannelEvent.Chat e, @First Player p) {
		if(Main.getMain().LuckpermsApi!=null) {
			User u = Main.getMain().LuckpermsApi.getUserManager().getUser(p.getUniqueId());
			String prefix = Main.getMain().LuckpermsApi.getGroupManager().getGroup(u.getPrimaryGroup()).getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefix();
			String name_color = prefix.trim()!=null && prefix.trim().length()>=2 && prefix.trim().substring(prefix.trim().length()-2).matches("&[0-9a-f]") ? prefix.trim().substring(prefix.trim().length()-2) : "";
			Builder msg_name = Text.builder();
			Text.Builder msg_msg = Text.builder();
			String msg_str = e.getMessage().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).toPlain();
			if(msg_str.contains("%item%")) {
				String part_stitched = "";
				for(String part : msg_str.split(" ")) {
					if(part.equalsIgnoreCase("%item%")) {
						msg_msg.append(p.hasPermission("core.chatcolor") ? Messenger.iCanHasColor(part_stitched) : Text.of(part_stitched));
						Optional<ItemStack> item = p.getItemInHand(HandTypes.MAIN_HAND);
						if(item.isPresent()) {
							msg_msg.append(Text.builder().append(Messenger.iCanHasColor("&b["+item.get().get(Keys.DISPLAY_NAME).orElse(Text.of(item.get().getTranslation().get())).toPlain()+"]&f ")).onHover(TextActions.showItem(item.get().createSnapshot())).build());
						} else {
							msg_msg.append(Messenger.iCanHasColor(" &b[Empty Hand]&f "));
						}
						part_stitched = "";
					} else {
						part_stitched += part + " ";
					}
				}
				msg_msg.append(p.hasPermission("core.chatcolor") ? Messenger.iCanHasColor(part_stitched) : Text.of(part_stitched));
			} else {
				msg_msg.append(p.hasPermission("core.chatcolor") ? Messenger.iCanHasColor(msg_str) : Text.of(msg_str));
			}
			msg_name.append(Messenger.iCanHasColor(name_color+p.getName())).onHover(TextActions.showEntity(p.getUniqueId(),p.getName())).onClick(TextActions.suggestCommand("/msg "+p.getName()+" ")).onShiftClick(TextActions.insertText(p.getName()));
			e.setMessage(Text.join(Messenger.iCanHasColor(prefix!=null ? prefix : ""), msg_name.build(), Messenger.iCanHasColor("&f > "), msg_msg.build()));
		}
	}
}
