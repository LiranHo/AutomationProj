import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;
//T: https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/engine/descriptor/JupiterEngineExtensionContext.html
public class RunnerExtension implements AfterTestExecutionCallback {

       @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
           //get the baseTest instance info
           BaseTest baseTest = ((BaseTest)context.getTestInstance().get());
           Boolean testResult = !context.getExecutionException().isPresent(); //true - SUCCESS, false- FAILED

           String sessionID = baseTest.sessionID;
           String testName = context.getDisplayName();
           String deviceSN= Thread.currentThread().getName();
           String reportPath= baseTest.path;

           System.out.println("The test result is: @@ "+testResult+" @@ | false - SUCCESS, true - FAILED"); //false - SUCCESS, true - FAILED

            // more not relevant info
//        Optional Exception_Content= context.getExecutionException();
//        Optional Class_test_Name= context.getTestClass(); //return "class" + classname
//        String TestName= context.getDisplayName();
//        String TestStartTime = baseTest.testStartTime;



           Main.report.addRowToReport("note",sessionID,testName, deviceSN,String.valueOf(testResult),reportPath);


           if(testResult.equals(false)){
            System.out.println("the test failed");
        }

        if(testResult.equals(true)){
            //add to report
            System.out.println("the test passed");
        }


    }




}
