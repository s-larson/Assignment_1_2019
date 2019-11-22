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
	public static AndrewsSemaphore semMan = new AndrewsSemaphore(0);
	public static AndrewsSemaphore semWoman = new AndrewsSemaphore(0); 
	public static AndrewsSemaphore semMutex = new AndrewsSemaphore(1);

	public volatile static int totalNumberOfWomen = 2;
	public volatile static int totalNumberOfMen = 2;
	public volatile static int numberOfWomenInCS = 0;
	public volatile static int numberOfMenInCS = 0;
	public volatile static int numberOfDelayedMen = 0;
	public volatile static int numberOfDelayedWomen = 0;


	public static void main(String argv[]) {
		List<Runnable> lor = new ArrayList<>();
		lor.addAll(IntStream.range(0,totalNumberOfMen).mapToObj(i -> new Man()).collect(Collectors.toList()));
		lor.addAll(IntStream.range(0,totalNumberOfWomen).mapToObj(i -> new Woman()).collect(Collectors.toList()));
		AndrewsProcess[] processes = (AndrewsProcess[]) lor.stream().map(r -> new AndrewsProcess(r)).toArray(AndrewsProcess[]::new);
		AndrewsProcess.startAndrewsProcesses(processes);
	}
}
