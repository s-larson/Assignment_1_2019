package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			doThings();
			GlobalState.semMutex.P();
			if(GlobalState.numberOfMenInCS == 0) {
				GlobalState.numberOfWomenInCS++;
				GlobalState.semMutex.V();
				doThings();
				printState();
				GlobalState.semMutex.P();
				GlobalState.numberOfWomenInCS--;
				GlobalState.semMutex.V();
			}else {
				GlobalState.semMutex.V();
			}
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
