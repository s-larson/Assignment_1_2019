package bathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Man implements Runnable, IUnisexBathroomActorProcess {

	@Override
	public void run() {
		while (true) {
			doThings();
			GlobalState.semMutex.P();
			if(GlobalState.numberOfWomenInCS == 0) {
				GlobalState.numberOfMenInCS++;
				GlobalState.semMutex.V();
				doThings();
				printState();
				GlobalState.semMutex.P();
				GlobalState.numberOfMenInCS--;
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
		return "M " + AndrewsProcess.currentAndrewsProcessId()
		+ "  in CS, state: nm:" + GlobalState.numberOfMenInCS + " nw:"
		+ GlobalState.numberOfWomenInCS + " dm:"
		+ GlobalState.numberOfDelayedMen + " dw:"
		+ GlobalState.numberOfDelayedWomen;
	}
}