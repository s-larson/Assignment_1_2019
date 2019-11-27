package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			// Lock shared mutex before comparing values
			GlobalState.semMutex.P();		
			// If any men exists in the bathroom, put the woman as waiting and lock access for women
			// If no men, occupy bathroom and unlock shared mutex
			if(GlobalState.numberOfMenInCS > 0) {
				GlobalState.numberOfDelayedWomen++;
				GlobalState.semMutex.V();
				GlobalState.semWoman.P();
			}
			GlobalState.numberOfWomenInCS++;
			GlobalState.signal();
			
			// Delay
			doThings();
			printState();
			
			// Lock shared mutex, tell others you left the bathroom, 
			// and unlock a semaphore depending on bathroom's current state
			GlobalState.semMutex.P();
			GlobalState.numberOfWomenInCS--;
			GlobalState.signal();
		}
	}


	@Override
	public  void printState() {
		System.out.println(this);
	}


	@Override
	public void doThings() {
		AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
				.nextInt(100, 500));
	}
	
	@Override
	public String toString() {
		return  "W " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;
	}
}
