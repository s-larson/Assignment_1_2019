package limitedBathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			// Lock shared mutex before comparing values
			GlobalState.semMutex.P();
			// If any men exists in the bathroom, or there are more than 3 women,
            // put the woman as waiting and lock access for women
			// If no men, occupy bathroom and unlock shared mutex
			if(GlobalState.numberOfMenInCS > 0 || GlobalState.numberOfWomenInCS > 3) {
				GlobalState.numberOfDelayedWomen++;
				GlobalState.semMutex.V();
				GlobalState.semWoman.P();
			}
			GlobalState.numberOfWomenInCS++;
			GlobalState.signal();
			
			// Delay
			doThings();
			printState();
			
			// Lock shared mutex, tell others you left the bathroom 
			// Unlock a semaphore depending on bathroom's current state
			GlobalState.semMutex.P();
			GlobalState.numberOfWomenInCS--;
			GlobalState.signal();		
		}
	}

	// printout on the global state
	@Override
	public  void printState() {
		System.out.println(this);
	}

	// represents that processes are staying in a state for a while
	@Override
	public void doThings() {
		AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
				.nextInt(100, 500));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "W " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;	
	}
}
