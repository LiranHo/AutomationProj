import javax.xml.transform.Result;
//import org.junit.runner.JUnitCore;
//import org.junit.jupiter.*;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.util.List;


//this class runs each device in separate Thread
public class Runner extends  Thread{
    protected Device device;
    //Parameters for run by rounds
    protected int CountRounds=0;
    //Parameters for run by time
    protected long StartTime;
    protected long TimePassedSinceStart;
    protected long CurrentTime;

    Runner(Device d){
        this.device=d;
        this.run();
    }

    public void run() {


        //TODO: add report for each device
        //Run by selected ROUNDS
        if(Main.Runby_NumberOfRounds){
            while(Main.NumberOfRoundsToRun>=CountRounds){
                runTest(Main.testsSuites);
                CountRounds++;
            }
        }else {
            //Run by selected TIME
            StartTime = System.currentTimeMillis();
            do {
                CurrentTime = System.currentTimeMillis();
                TimePassedSinceStart =  Math.round(CurrentTime - StartTime)/1000;
                runTest(Main.testsSuites);
                CountRounds++; //need to know who many runs there was in this test
            }
            while (TimePassedSinceStart<=Main.TimeToRun);

        }

    }

    private void runTest(TestsSuites testsClasses){
        // https://junit.org/junit5/docs/current/user-guide/#launcher-api
        // https://stackoverflow.com/questions/39111501/whats-the-equivalent-of-org-junit-runner-junitcore-runclasses-in-junit-5

        Result result;

//        result = JUnitCore.runClasses(JunitTestSuite_android.class);

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(testsClasses.selectors)
                .build();

        Launcher launcher = LauncherFactory.create();

        TestPlan testPlan = launcher.discover(request);

        /////////////////////////////////////////////////////////////


        // Register a listener of your choice
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        long testFoundCount = summary.getTestsFoundCount();
        List<TestExecutionSummary.Failure> failures = summary.getFailures();

        System.out.println("getTestsSucceededCount() - " + summary.getTestsSucceededCount());
        failures.forEach(failure -> System.out.println("failure - " + failure.getException()));


    }


    }
