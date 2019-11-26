package fairBathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Woman implements Runnable, IUnisexBathroomActorProcess {

    @Override
    public void run() {
        while (true) {
        	GlobalState.semOrder.P();
            GlobalState.semMutex.P();
            if(GlobalState.numberOfMenInCS > 0 || GlobalState.numberOfWomenInCS>3) {
                GlobalState.numberOfDelayedWomen++;
                GlobalState.semMutex.V();
                GlobalState.semWoman.P();
            }
            GlobalState.numberOfWomenInCS++;
        	GlobalState.semOrder.V();
            GlobalState.signal();

            doThings();
            printState();

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

    public void doThings2() {
        AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
                .nextInt(10, 50));
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