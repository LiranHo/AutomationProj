package Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests;

import Project.Main;
import Project.Tests.EriBank_Tests.EriBank_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EriBank_Insrumented_test3_ClearData extends EriBank_Instrumented {


    @Test
    @DisplayName("EriBank_Insrumented_test3_ClearData")
    public void Test(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());

        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        client.elementSendText("NATIVE", "id=usernameTextField", 0, "company");
        client.elementSendText("NATIVE", "id=passwordTextField", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.click("NATIVE", "id=makePaymentButton", 0, 1);
        client.elementSendText("NATIVE", "id=phoneTextField", 0, "046655984");
        client.elementSendText("NATIVE", "id=nameTextField", 0, "name1");
        client.click("NATIVE", "id=countryButton", 0, 1);
        client.elementListSelect("xpath=//*[@id='countryList']", "text=Greenland", 0, true);
        client.elementSendText("NATIVE", "id=amountTextField", 0, "50");
        client.click("NATIVE", "id=sendPaymentButton", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.waitForElement("native","id=balanceWebView",0,10000);
        client.verifyIn("NATIVE", "id=balanceWebView", 0, "Inside", "WEB", "text=Your balance is: 50.00$", 0, 0);
        client.click("NATIVE", "id=logoutButton", 0, 1);
        client.applicationClose(Main.EriBankPackageName);
        client.applicationClearData(Main.EriBankPackageName);
        client.launch(Main.EriBankLaunchName, true, true);
        client.elementSendText("NATIVE", "id=usernameTextField", 0, "company");
        client.elementSendText("NATIVE", "id=passwordTextField", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.waitForElement("native","id=balanceWebView",0,10000);
        client.verifyIn("NATIVE", "id=balanceWebView", 0, "Inside", "WEB", "text=Your balance is: 100.00$", 0, 0);
        String str0 = client.getDeviceLog();
    }



}
