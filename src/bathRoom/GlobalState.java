package bathRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.his.iit.it325g.common.AndrewsProcess;
import se.his.iit.it325g.common.AndrewsSemaphore;

/*
 * author: <group_ID, group members>
 */

public class GlobalState {
	/* insert necessary semaphores here! */

	// adjusts the number of total processes
	public volatile static int totalNumberOfWomen = 2;
	public volatile static int totalNumberOfMen = 2;
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
		AndrewsProcess[] processes = (AndrewsProcess[]) lor.stream().map(r -> new AndrewsProcess(r)).toArray();
		
		// start the processes
		AndrewsProcess.startAndrewsProcesses(processes);
	}
}
