package Project.Tests.Other_Tests;

import Project.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Capture extends BaseTest{

    @Test
    @DisplayName("Capture_SA_26421")
    public void Capture_SA_26421(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());


        if(device.IsDeviceRemote()) {
            System.out.println("Device is Remote and therefor we install from the cloud");
            client.install("cloud:com.experitest.ExperiBank/.LoginActivity", true, false);
            client.launch("cloud:com.experitest.ExperiBank/.LoginActivity", true, true);
        }
        else{
            System.out.println("Device is Local and therefor we install from STA");
            client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
            client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        }

        client.elementSendText("NATIVE", "xpath=//*[@hint='Username']", 0, "company");
        client.elementSendText("NATIVE", "xpath=//*[@hint='Password']", 0, "company");
        client.click("NATIVE", "xpath=//*[@id='loginButton']", 0, 1);
      //  client.sleep(5000);
        String p1 = client.capture();
        try {
            client.getRemoteFile(p1, 2000, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.click("NATIVE", "xpath=//*[@id='makePaymentButton']", 0, 1);
        client.click("NATIVE", "xpath=//*[@id='cancelButton']", 0, 1);
        String p2 = client.capture();
        try {
            client.getRemoteFile(p2, 2000, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //            client.sleep(3000);
    }
}
