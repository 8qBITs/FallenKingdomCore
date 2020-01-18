package net.fallenkingdom.core.util;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.text.title.Title;

public class Messenger {
	
	Player p;
	
	public Messenger(Player p) {
		this.p = p;
	}
	
	// Make things accept color formatting
	public static Text iCanHasColor(String input) {
		return Text.of(TextSerializers.FORMATTING_CODE.deserialize(input));
	}
	public static Text iCanHasColor(Text input) {
		return TextSerializers.FORMATTING_CODE.deserialize(input.toPlain());
	}
	
	public void sendFullTitle(String title, String subtitle) {
		Title build_title = Title.builder()
				.title(iCanHasColor(title))
				.subtitle(iCanHasColor(subtitle))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendTitle(String title) {
		Title build_title = Title.builder()
				.title(iCanHasColor(title))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendSubTitle(String subtitle) {
		Title build_title = Title.builder()
				.subtitle(iCanHasColor(subtitle))
				.fadeOut(100).build();
		p.sendTitle(build_title);
	}
	
	public void sendAction(String action) {
		Title build_title = Title.builder()
				.actionBar(iCanHasColor(action))
				.stay(100).build();
		p.sendTitle(build_title);
	}

}
