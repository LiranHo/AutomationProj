package Project.Tests.EriBank_Tests.NON_Instrumented_Eribank_Tests;

import Project.Main;
import Project.Tests.EriBank_Tests.EriBank_Non_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EriBank_NON_Insrumented_test1_XpathAfterChangeOrientation extends EriBank_Non_Instrumented{


    @Test
    @DisplayName("EriBank_NON_Insrumented_test1_XpathAfterChangeOrientation")
    public void Test() {
        functionPrintInfo(getClass().toString(), "Test", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, "company"); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, "company"); //send the text from the csv file to "password"
        client.click("NATIVE", "//*[@text='Login']", 0, 1);
        client.isElementFound("NATIVE", "//*[@text='Make Payment']", 0);

        String getCounter_cpu = client.getCounter("cpu");
        String getCounter_memory = client.getCounter("memory");
        String getCounter_battery = client.getCounter("battery");

        System.out.println("getCounter_cpu "+getCounter_cpu +"/t"
        +"getCounter_memory "+getCounter_memory +"/t"
        +"getCounter_battery "+getCounter_battery +"/t");


        for (int i = 0; i < 10; i++) {
            client.deviceAction("Landscape");
            client.click("NATIVE", "xpath=//*[@id='makePaymentButton']", 0, 1);
            client.deviceAction("Portrait");
            client.click("NATIVE", "xpath=//*[@text='Cancel']", 0, 1);
//            client.click("NATIVE", "xpath=//*[@text='Send Payment']", 0, 1);
//            client.deviceAction("back");
        }
    }
}
