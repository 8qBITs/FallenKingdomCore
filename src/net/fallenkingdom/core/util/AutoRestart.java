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
			Main.getMain().getLogger().warn("INDEED I WORK! ~AR CONSTRUCTOR");
			//TimeChecker();
			Task task = Task.builder().execute(new TimeCheckerClass())
					.async()
			        .submit(Main.getMain());
		}
	}
	
	private Text iCanHasColor(String input) {
		return Text.of(TextSerializers.FORMATTING_CODE.deserialize(input));
	}
	
	int timer = 301;
	
    public void StartRestartTimer() {
    	final int[] times = new int[] {300,150,60,30,10,0};
		
		Task.builder().execute(() -> {
			Main.getMain().getLogger().error("INDEED I WORK! ~start restart timer");
			timer--;
			if(!(Arrays.stream(times).anyMatch(i -> i == timer))) {
				return;
			} else {
				int minutes = (int)Math.floor(timer/60), seconds = timer%60;
				Sponge.getServer().getBroadcastChannel().send(iCanHasColor(String.format(" &4&lWARNING! &ethis server is scheduled to restart in %d minute%s %d second%s!", minutes, minutes>1 ? "s" : "", seconds, seconds>1 ? "s" : "")));
				if(this.timer == 0) {
					Sponge.getServer().shutdown(iCanHasColor("&eServer is restarting."));
					Main.getMain().getLogger().error("Achievement Unlocked: How did we get here?");
				}
		
			}
			
		}).async()
				.interval(1, TimeUnit.SECONDS)
				.submit(Main.getMain());
		
	}

    private class TimeCheckerClass implements Consumer<Task> {
    	private int seconds = 60;
        @Override
        public void accept(Task task) {
            seconds--;
            Sponge.getServer()
                .getBroadcastChannel()
                .send(Text.of("Remaining Time: "+seconds+"s"));
            if (seconds < 1) {
                task.cancel();
            }
        }
    }
	
}
