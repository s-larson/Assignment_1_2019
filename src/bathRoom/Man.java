package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Man implements Runnable,IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			
			GlobalState.semMutex.P();
			
			if(GlobalState.numberOfMenInCS > 0) {
				GlobalState.semMutex.V();
				GlobalState.semMan.P();
			}
			
			printState();
			
			GlobalState.numberOfMenInCS++;
			GlobalState.signal();
			
			doThings();
			
			GlobalState.semMutex.P();
			GlobalState.numberOfMenInCS--;
			GlobalState.signal();
			
			/*
			GlobalState.semMutex.P();
			if(GlobalState.numberOfWomenInCS == 0) {
				if(GlobalState.numberOfMenInCS == 0) {
					GlobalState.semMan.P();
				}
				GlobalState.numberOfMenInCS++;
				printState();
				// Critical section
				this.doThings();
				// Exit protocol
				GlobalState.numberOfMenInCS--;
				if(GlobalState.numberOfMenInCS == 0) {
					GlobalState.semMan.V();
				}
			}
			*/
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
		return "M " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;
	}
}