package net.fallenkingdom.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import net.fallenkingdom.core.Main;

public class AutoRestart {

	public AutoRestart(Boolean run) {
		if(run) {
			if(!Main.getMain().restart) {
				Task task = Task.builder().execute(new TimeChecker())
						.async()
						.interval(30, TimeUnit.SECONDS)
						.submit(Main.getMain());
				Main.getMain().restart = true;
			}
		}
	}
	
	private Text iCanHasColor(String input) {
		return Text.of(TextSerializers.FORMATTING_CODE.deserialize(input));
	}
	private boolean started = false;

    private class TimeChecker implements Consumer<Task> {
        @Override
		public void accept(Task task) {
			DateFormat dateformat = new SimpleDateFormat("HH:mm");
			Date date = new Date();

			String[] times = new String[] {"7:50", "15:50", "23:50"};

			if (Arrays.stream(times).anyMatch(i -> i.equals(dateformat.format(date)))) {
				if (!started) {
					Task task2 = Task.builder().execute(new Restarter())
							.interval(1, TimeUnit.SECONDS)
							.submit(Main.getMain());
					started = true;
				}
			}

		}
    }

	private class Restarter implements Consumer<Task> {
		@Override
		public void accept(Task task) {

			int[] times = new int[] {600, 300, 150, 60, 30, 0};
			if(Arrays.stream(times).anyMatch(i -> i == Main.getMain().restart_timer)) {

				if(Main.getMain().restart_timer == 0) {
					Task.builder()
							.execute(() -> {
								Sponge.getServer().shutdown(iCanHasColor("&eServer is restarting."));
								Main.getMain().getLogger().error("Achievement Unlocked: How did we get here?");
							})
							.submit(Main.getMain());
					return;
				}
				sendAnnouncment(Main.getMain().restart_timer);
			}

			Main.getMain().restart_timer--;
		}

		private void sendAnnouncment(int timer) {
			int minutes = (int)Math.floor(timer/60), seconds = timer%60;
			Sponge.getServer().getBroadcastChannel().send(iCanHasColor(String.format("\n &4&lWARNING! &ethis server is scheduled to restart in %d minutes %d seconds!\n", minutes, seconds)));
		}

	}
	
}
