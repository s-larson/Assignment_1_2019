package fairBathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

    @Override
    public void run() {
        while (true) {
        	// This order mutex will lock access for accessing the meta-variables
        	// and semaphores. This causes a queue to form behind the first one to lock order mutex
        	// This shifts queue management responsibility onto the JVM which handles queues in FIFO
        	GlobalState.semOrder.P();
			// Lock shared mutex before comparing values
            GlobalState.semMutex.P();
			// If any women exists in the bathroom, or there are more than 3 men,
            // put the man as waiting and lock access for men
			// If no women, occupy bathroom and unlock shared mutex
            if(GlobalState.numberOfMenInCS > 0 || GlobalState.numberOfWomenInCS>3) {
                GlobalState.numberOfDelayedWomen++;
                GlobalState.semMutex.V();
                GlobalState.semWoman.P();
            }
            GlobalState.numberOfWomenInCS++;
        	GlobalState.semOrder.V();
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


    @Override
    public  void printState() {
        System.out.println(this);
    }


    @Override
    public void doThings() {
        AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
                .nextInt(1, 5));
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