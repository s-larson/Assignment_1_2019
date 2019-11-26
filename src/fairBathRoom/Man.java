package fairBathRoom;
import java.util.concurrent.ThreadLocalRandom;

import common.IUnisexBathroomActorProcess;
import se.his.iit.it325g.common.AndrewsProcess;

public class Man implements Runnable, IUnisexBathroomActorProcess {

    @Override
    public void run() {
        while (true) {
        	GlobalState.semOrder.P();
            GlobalState.semMutex.P();
            if(GlobalState.numberOfWomenInCS > 0 || GlobalState.numberOfMenInCS>3) {
                GlobalState.numberOfDelayedMen++;
                GlobalState.semMutex.V();
                GlobalState.semMan.P();
            }

            GlobalState.numberOfMenInCS++;
        	GlobalState.semOrder.V();
            GlobalState.signal();
            
            doThings();
            printState();

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
                .nextInt(1, 5));
    }


    public void doThings2() {
        AndrewsProcess.uninterruptibleMinimumDelay(ThreadLocalRandom.current()
                .nextInt(10, 50));
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