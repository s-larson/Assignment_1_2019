package limitedBathRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import limitedBathRoom.Man;
import limitedBathRoom.Woman;
import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsSemaphore;

/*
 * author: <group_ID, group members>
 */

public class GlobalState {
	/* insert necessary semaphores here! */
	public static AndrewsSemaphore semMan = new AndrewsSemaphore(0);
	public static AndrewsSemaphore semWoman = new AndrewsSemaphore(0); 
	public static AndrewsSemaphore semMutex = new AndrewsSemaphore(1);
	
	// adjusts the number of total processes
	public volatile static int totalNumberOfWomen = 8;
	public volatile static int totalNumberOfMen = 8;
	// the number of people in the bathroom
	public volatile static int numberOfWomenInCS = 0;
	public volatile static int numberOfMenInCS = 0;
	// the number of people staying in the line
	public volatile static int numberOfDelayedMen = 0;
	public volatile static int numberOfDelayedWomen = 0;

	// The main is only present to start the processes
	public static void main(String argv[]) {
		// list of runnable
		List<Runnable> lor = new ArrayList<>();
		
		// initialize with number of men Man and number of women Woman runnables
		
		lor.addAll(IntStream.range(0,totalNumberOfMen).mapToObj(i -> new Man()).collect(Collectors.toList()));
		lor.addAll(IntStream.range(0,totalNumberOfWomen).mapToObj(i -> new Woman()).collect(Collectors.toList()));

		// create an array of AndrewsProcess of the runnables
		AndrewsProcess[] processes = (AndrewsProcess[]) lor.stream().map(r -> new AndrewsProcess(r)).toArray(AndrewsProcess[]::new);
		
		// start the processes
		AndrewsProcess.startAndrewsProcesses(processes);
	}
	public static void signal() {
		if(GlobalState.numberOfMenInCS == 0 && GlobalState.numberOfDelayedWomen > 0 && GlobalState.numberOfWomenInCS < 4) {
			--GlobalState.numberOfDelayedWomen;
			GlobalState.semWoman.V();
		}
		else if(GlobalState.numberOfWomenInCS == 0 && GlobalState.numberOfDelayedMen > 0 && GlobalState.numberOfMenInCS < 4) {
			--GlobalState.numberOfDelayedMen;
			GlobalState.semMan.V();
		}
		else {
			GlobalState.semMutex.V();
		}
	}
}