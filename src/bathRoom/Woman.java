package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;


public class Woman implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			GlobalState.semMutex.P();
			
			if(GlobalState.numberOfWomenInCS > 0) {
				GlobalState.semMutex.V();
				GlobalState.semWoman.P();
			}
			
			printState();
			
			GlobalState.numberOfWomenInCS++;
			GlobalState.signal();
			
			doThings();
			
			GlobalState.semMutex.P();
			GlobalState.numberOfWomenInCS--;
			GlobalState.signal();
			
			/*
			GlobalState.semMutex.P();
			if(GlobalState.numberOfMenInCS == 0) {
				if(GlobalState.numberOfWomenInCS == 0) { 
					GlobalState.semWoman.P();
				}
				GlobalState.semMutex.V();
				GlobalState.numberOfWomenInCS++;
				printState();
				// Critical section
				this.doThings();
				// Exit protocol
				GlobalState.numberOfWomenInCS--;
				if(GlobalState.numberOfWomenInCS == 0) {
					GlobalState.semWoman.V();
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
				.nextInt(100, 200));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  "W " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;
	}
}
