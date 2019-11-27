package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Man implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			// Lock shared mutex before comparing values
			GlobalState.semMutex.P();
			// If any women exists in the bathroom, put the man as waiting and lock access for men
			// If no women, occupy bathroom and unlock shared mutex
			if(GlobalState.numberOfWomenInCS > 0) {
				GlobalState.numberOfDelayedMen++;
				GlobalState.semMutex.V();
				GlobalState.semMan.P();
			}
			GlobalState.numberOfMenInCS++;
			GlobalState.signal();
	
			//Delay
			doThings();
			printState();
			
			// Lock shared mutex, tell others you left the bathroom, 
			// and unlock a semaphore depending on bathroom's current state
			GlobalState.semMutex.P();
			GlobalState.numberOfMenInCS--;
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
		return "M " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;
	}
}