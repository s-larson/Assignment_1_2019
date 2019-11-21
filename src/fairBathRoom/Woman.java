package fairBathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			// fill functionality here
			
			
			
			
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
