package net.fallenkingdom.core.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Text.Builder;
import org.spongepowered.api.text.action.TextActions;
import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public class Chat {
	@Listener
	public void onChat(MessageChannelEvent.Chat e, @First Player p) {
		if(Main.getMain().LuckpermsApi!=null) {
			User u = Main.getMain().LuckpermsApi.getUserManager().getUser(p.getUniqueId());
			String prefix = Main.getMain().LuckpermsApi.getGroupManager().getGroup(u.getPrimaryGroup()).getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefix();
			String name_color = prefix.trim()!=null && prefix.trim().length()>=2 && prefix.trim().substring(prefix.trim().length()-2).matches("&[0-9a-f]") ? prefix.trim().substring(prefix.trim().length()-2) : "";
			Builder msg_name = Text.builder();
			Text msg_msg = e.getMessage().getChildren().get(1).getChildren().get(0).getChildren().get(0).getChildren().get(0).toText();
			System.out.println(name_color.toString());
			msg_name.append(Messenger.iCanHasColor(name_color+p.getName())).onHover(TextActions.showEntity(p.getUniqueId(),p.getName())).onClick(TextActions.suggestCommand("/msg "+p.getName()+" ")).onShiftClick(TextActions.insertText(p.getName()));
			e.setMessage(Text.join(Messenger.iCanHasColor(prefix!=null ? prefix : ""), msg_name.build(), Messenger.iCanHasColor("&f > "), p.hasPermission("core.chatcolor") ? Messenger.iCanHasColor(msg_msg) : msg_msg));
		}
	}
}
