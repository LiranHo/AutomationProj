package Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests;

import Project.Main;
import Project.Tests.EriBank_Tests.EriBank_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EriBank_Insrumented_test4_NativeAPICall extends EriBank_Instrumented {


    @Test
    @DisplayName("EriBank_Insrumented_test4_NativeAPICall")
    public void Test(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());

        //TODO: add more tests
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        client.sleep(2000); //test for issue SA-25922
//        client.applicationClose("com.android.chrome");
        String str0 = client.runNativeAPICall("NATIVE", "xpath=//*[@id='usernameTextField']", 0, "view.setHint(\"Native_Call_Api_Test\");");
        client.verifyElementFound("NATIVE", "xpath=//*[@hint='Native_Call_Api_Test']", 0);

    }



}
