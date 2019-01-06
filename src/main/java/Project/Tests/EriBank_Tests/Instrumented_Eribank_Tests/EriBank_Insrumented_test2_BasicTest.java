package Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests;

import Project.BaseTest;
import Project.Tests.EriBank_Tests.EriBank_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EriBank_Insrumented_test2_BasicTest extends EriBank_Instrumented {


    @Test
    @DisplayName("EriBank_Insrumented_test2_BasicTest")
    public void Test(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        String userName = "company";
        String password = "company";

        String appName = client.getCurrentApplicationName();

        //if already logged in-> log out
        if (client.waitForElement("NATIVE", "//*[@text='Make Payment']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Logout']", 0, 1);
        }
        //if Error - send close
        if (client.waitForElement("NATIVE", "//*[@text='Error']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Close']", 0, 1);
        }
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, userName); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, password); //send the text from the csv file to "password"

        client.click("NATIVE", "//*[@text='Login']", 0, 1);

        client.sleep(3000);
        client.isElementFound("NATIVE", "//*[@text='Make Payment']", 0);

        client.click("NATIVE", "xpath=//*[@text='Make Payment']", 0, 1);
        client.elementSendText("NATIVE", "xpath=//*[@hint='Phone']", 0, "phone");
        client.elementSendText("NATIVE", "xpath=//*[@hint='Name']", 0, "name");
        client.elementSendText("NATIVE", "xpath=//*[@hint='Amount']", 0, "0");
        client.click("NATIVE", "xpath=//*[@text='Select']", 0, 1);
        client.elementListSelect("xpath=//*[@id='countryList']", "text=Spain", 0, false);
//        client.elementListSelect("", "text=Spain", 0, false);
        client.click("NATIVE", "text=Spain", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Send Payment']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Yes']", 0, 1);

        String remoteFile = client.capture("Capture");

    }



}
