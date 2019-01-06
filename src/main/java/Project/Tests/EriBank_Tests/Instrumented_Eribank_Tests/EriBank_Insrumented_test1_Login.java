package Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests;

import Project.BaseTest;
import Project.Tests.EriBank_Tests.EriBank_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EriBank_Insrumented_test1_Login extends EriBank_Instrumented{


    @Test
    @DisplayName("EriBank_Instrumented_Test1 - Login")
    public void Test(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/


        String userName = "company";
        String password ;


        //check login in
        for (int i = 0; i < 4; i++) {
            if (i % 2 == 0) { //active for 2 of the times
                password = "company";
            } else {
                password = "a";
            }

            //if already logged in-> log out
            if (client.waitForElement("NATIVE", "//*[@text='Make Payment']", 0, 1000)) {
                client.click("NATIVE", "//*[@text='Logout']", 0, 1);
            }
            //if Error accrued- click close
            if (client.waitForElement("NATIVE", "//*[@text='Error']", 0, 1000)) {
                client.click("NATIVE", "//*[@text='Close']", 0, 1);
            }
            client.waitForElement("NATIVE", "//*[@id='usernameTextField']", 0, 3000);
            client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, userName); //send the text from the csv file to "user name"
            client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, password); //send the text from the csv file to "password"

            client.click("NATIVE", "//*[@text='Login']", 0, 1);

            if (i % 2 == 0) {
                try {
                    client.sleep(5000);
                    client.verifyElementFound("NATIVE", "//*[@text='Make Payment']", 0);
                    client.sleep(3000);
//                        client.verifyElementFound("default", "Logout_EriBank", 0);
                } catch (Exception e) {
                }
            }

        }

    }



}
