package Project.LongRun;

import Project.Main;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class RunnerExtension_LongRun implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        BaseTest_LongRun baseTest = ((BaseTest_LongRun)context.getTestInstance().get());
        Boolean testResult = !context.getExecutionException().isPresent(); //true - SUCCESS, false- FAILED

        Optional<Throwable> executionException=context.getExecutionException();
        String sessionID = baseTest.sessionID;
        String testName = context.getDisplayName();
        String deviceSN= baseTest.device.getSerialnumber();
        String reportPath= baseTest.path;
        long testDuring = System.currentTimeMillis() - baseTest.testStartTime_calculate;

        //System.out.println("The test result is: @@ "+testResult+" @@ | false - SUCCESS, true - FAILED"); //false - SUCCESS, true - FAILED


        Main.countTests++;

        if(testResult.equals(false)){
            String error=executionException.toString().replaceAll("\n"," | ");
            Main.report.addRowToReport("Report",testName, deviceSN,String.valueOf(testResult), calculateTestDuring(testDuring),sessionID,reportPath,error);
//           System.out.println("the test failed");
            Main.countTests_fail++;
        }

        if(testResult.equals(true)){
            Main.report.addRowToReport("Report LongRun",testName, deviceSN,String.valueOf(testResult), calculateTestDuring(testDuring),sessionID,reportPath,"");
//            System.out.println("the test passed");
            Main.countTests_pass++;

        }
    }


    protected String calculateTestDuring(long TestDuring){
        System.out.println("Calculating test during");
        TestDuring=TestDuring/1000;
        String Units=" Sec";
        if (TestDuring>60 && TestDuring<60*60){
            TestDuring=TestDuring/60 ;
            Units=" Min";
        }
        else
        if (TestDuring>60*60){
            TestDuring=TestDuring/(60*60);
            Units=" Hour";
        }

        System.out.println(String.valueOf(TestDuring)+Units);
        return String.valueOf(TestDuring)+Units;
    }
}
