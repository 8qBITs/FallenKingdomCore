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
			TimeChecker();
		}
	}
	
	private Text iCanHasColor(String input) {
		return Text.of(TextSerializers.FORMATTING_CODE.deserialize(input));
	}
	
	int timer = 601;
	boolean countdown = false;

    private void StartRestartTimer() {
		final int[] times = new int[] {600,300,60,30,10};
		
		System.out.println("RESTART COUNTDOWN START");
		
		Task task = Task.builder().execute(() -> {
			timer--;
			if(!(Arrays.stream(times).anyMatch(i -> i == timer))) {
				return;
			} else {
				int minutes = (int)Math.floor(timer/60), seconds = timer%60;
				Sponge.getServer().getBroadcastChannel().send(iCanHasColor(String.format(" &4&lWARNING! &ethis server is scheduled to restart in %d minute(s)%s %d second(s)%s!", minutes, minutes>1 ? "s" : "", seconds, seconds>1 ? "s" : "")));
				if(this.timer == 0) {
					Sponge.getServer().shutdown(iCanHasColor("&eServer is restarting."));
					Main.getMain().getLogger().error("Achievement Unlocked: How did we get here?");
				}
		
			}
			
		}).async()
				.interval(500, TimeUnit.MILLISECONDS)
				.name("Auto restart timer")
				.submit(Main.getMain());
		
	}
    
    private void TimeChecker() {
    	Task task = Task.builder().execute(new TimeCheckerClass())
    	        .interval(1, TimeUnit.SECONDS)
    	        .name("Self-Cancelling Timer Task").submit(Main.getMain());
    	
    }
    
    private class TimeCheckerClass implements Consumer<Task> {
        @Override
        public void accept(Task task) {
            DateFormat dateformat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            String first = "8:00";
            String second = "16:00";
            String third = "23:07";
            if (dateformat.format(date).equals(first) || dateformat.format(date).equals(second) || dateformat.format(date).equals(third)) {
            	StartRestartTimer();
            }
            if (!countdown) {
                task.cancel();
            }
        }
    }
	
}
