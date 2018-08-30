
//this class runs each device in separate Thread
public class Runner extends  Thread{
    protected Device device;
    //Parameters for run by rounds
    protected int CountRounds=0;
    //Parameters for run by time
    protected long StartTime;
    protected long TimePassedSinceStart;
    protected long CurrentTime;

    public void run() {
        if(Main.Runby_NumberOfRounds){
            if(Main.NumberOfRoundsToRun>=CountRounds){
                runTest();
                CountRounds++;
            }
        }else { //run by time



        }

    }

    private void runTest(){

    }


    }
