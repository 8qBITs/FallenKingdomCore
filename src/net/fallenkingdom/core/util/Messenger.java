package net.fallenkingdom.core.util;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;

public class Messenger {
	
	
	// Make things accept color formatting
	private Text iCanHasColor(String input) {
		return Text.of(TextSerializers.FORMATTING_CODE.deserialize(input));
	}
	
	public void sendFullTitle(Player p, String title, String subtitle) {
		Title build_title = Title.builder()
				.title(iCanHasColor(title))
				.subtitle(iCanHasColor(subtitle))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendTitle(Player p, String title) {
		Title build_title = Title.builder()
				.title(iCanHasColor(title))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendSubTitle(Player p, String subtitle) {
		Title build_title = Title.builder()
				.subtitle(iCanHasColor(subtitle))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendAction(Player p, String action) {
		Title build_title = Title.builder()
				.actionBar(iCanHasColor(action))
				.stay(100).build();
		p.sendTitle(build_title);
	}

}