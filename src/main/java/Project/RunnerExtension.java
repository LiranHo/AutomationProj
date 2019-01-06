package Project;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

//T: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/engine/descriptor/JupiterEngineExtensionContext.html
public class RunnerExtension implements AfterEachCallback {
//public class RunnerExtension implements AfterTestExecutionCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {



//       @Override
//    public void afterTestExecution(ExtensionContext context) {
           System.out.println("afterTestExecution for device: "+ Thread.currentThread().getName());
           //get the baseTest instance info
           BaseTest baseTest = ((BaseTest)context.getTestInstance().get());
           Boolean testResult = !context.getExecutionException().isPresent(); //true - SUCCESS, false- FAILED

           Optional<Throwable> executionException=context.getExecutionException();
           String sessionID = baseTest.sessionID;
           String testName = context.getDisplayName();
           String deviceSN= baseTest.device.getSerialnumber();
           String reportPath= baseTest.path;
           long testDuring = System.currentTimeMillis() - baseTest.testStartTime_calculate;

           //System.out.println("The test result is: @@ "+testResult+" @@ | false - SUCCESS, true - FAILED"); //false - SUCCESS, true - FAILED


            // more not relevant info
//        String deviceSN= Thread.currentThread().getName();
//        Optional Exception_Content= context.getExecutionException();
//        Optional Class_test_Name= context.getTestClass(); //return "class" + classname
//        String TestName= context.getDisplayName();
//        String TestStartTime = baseTest.testStartTime;



         //  Main.report.addRowToReport("Report",testName, deviceSN,String.valueOf(testResult), calculateTestDuring(testDuring),sessionID,reportPath,executionException.toString());


           //add to the tests count
           Main.countTests++;

           if(testResult.equals(false)){
               System.err.println("afterTestExecution - FAIL \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t devicesn: "+deviceSN);
               String error=executionException.toString().replaceAll("\n"," | ");
               Main.report.addRowToReport("Report",testName, deviceSN,String.valueOf(testResult), calculateTestDuring(testDuring),sessionID,reportPath,error);
//           System.out.println("the test failed");
               Main.countTests_fail++;
        }

        if(testResult.equals(true)){
            System.err.println("afterTestExecution - PASS \t"+"+Thread.currentThread().getName() "+Thread.currentThread().getName()+"\t devicesn: "+deviceSN);
            Main.report.addRowToReport("Report",testName, deviceSN,String.valueOf(testResult), calculateTestDuring(testDuring),sessionID,reportPath,"");
//            System.out.println("the test passed");
            Main.countTests_pass++;

        }
    }




    protected String calculateTestDuring(long TestDuring){
        System.out.println("Calculating test during");
        String TestDuringForPrint="";
        //Move from milliseconds to seconds
        TestDuring=TestDuring/1000;
        if(TestDuring<=60){
            TestDuringForPrint=String.valueOf(TestDuring)+ " Sec";
        }
        else
            if(TestDuring>60 && TestDuring<60*60){
            long Sec=TestDuring%60;
            TestDuring=TestDuring/60 ;
            TestDuringForPrint=String.valueOf(TestDuring)+ " Min "+String.valueOf(Sec)+" Sec";
            }
            else
                if(TestDuring>60*60){
                    long Min=TestDuring%60;
                    TestDuring=TestDuring/60;
                    long Sec=TestDuring%60;
                    TestDuring=TestDuring/60;
                    TestDuringForPrint=String.valueOf(TestDuring)+ " Hour "+String.valueOf(Min)+ " Min "+String.valueOf(Sec)+" Sec";
        }


        System.out.println(TestDuringForPrint);
        return TestDuringForPrint;

    }

    protected String calculateTestDuring_old(long TestDuring){
        System.out.println("Calculating test during");
           TestDuring=TestDuring/1000;
           String Units=" Sec";
           if (TestDuring>60 && TestDuring<60*60){
               long second = TestDuring%60;
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
