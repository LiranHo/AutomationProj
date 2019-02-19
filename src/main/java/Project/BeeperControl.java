package Project;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.*;
//INFO: https://docs.oracle.com/javase/6/docs/api/java/util/concurrent/ScheduledExecutorService.html
//INFO: https://stackoverflow.com/questions/426758/running-a-java-thread-in-intervals

public class BeeperControl {

    public static void main(String[] args) {
        BeeperControl b = new BeeperControl();
        b.WakeEveryHour();

      //  b.cancel(true);
    //    scheduler.shutdown();
    }

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    ScheduledFuture<?> beeperHandle=null ;


    public void WakeEveryHour() {

        final Runnable beeper = () -> {
            Main.CollectSupportDataVar.set(true);
            Main.sout("Info!","Beep is on!");
//                String errorOutput= "Beep is on!";
//                System.out.println(errorOutput);
//                Main.ErrorFile.addRowToReport("Beep",errorOutput);

       };
        scheduler.scheduleAtFixedRate(beeper, 10, Main.CollectEveryX_inMin, MINUTES);
//        beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, Main.CollectEveryX_inMin, MINUTES);
                //scheduler.scheduleAtFixedRate(beeper, 10, 10, SECONDS);
        //initial delay: the time the first beep is happen
        //period: the time between beeps


//        scheduler.schedule(() -> { beeperHandle.cancel(true); }, Main.TimeToRun, SECONDS);
        //}, 60 * 60, SECONDS);

        //delay:how much time the beeps happend

        System.out.println("BEEP START");
    }

    public void Terminate(){
        scheduler.shutdownNow();
//        if(beeperHandle !=null)
//            beeperHandle.cancel(true);
    }


}
