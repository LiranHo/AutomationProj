package Project.Tests.Other_Tests;

import Project.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class GeneralTests_ManyApps extends BaseTest {

    @Test
    @DisplayName("minicapWorkWithSecurityApps")
    public void minicapWorkWithSecurityApps(){
        final String APP_NAME = "com.bankid.bus/.activities.InitActivity";
        final String APP_PACKAGE = "com.bankid.bus";
        final String APPLICATION_URL = "E:\\Files - Liran - 2\\Applications_apk\\BankID - security app\\com.bankid.bus.activities.InitActivity.2.apk";

        try {
            client.install(APPLICATION_URL, false, false);
            client.launch(APP_NAME, false, false);
            if (client.isElementFound("native", "xpath=//*[@text='GOT IT' or @text='Got it']", 0)) {
                client.click("native", "xpath=//*[@text='GOT IT' or @text='Got it']", 0, 1);
            }
            if (client.isElementFound("native", "xpath=//*[@text='Allow']", 0)) {
                client.click("native", "xpath=//*[@text='Allow']", 0, 1);
            }
            client.capture(); //to make sure manually (if needed) that the image is shown.
            Assert.state((client.isElementFound("text", "BankID", 0)) && (client.isElementFound("text", "QR", 0)),"Image isn't Shown - There is a problem with Minicap");
        }finally {
            client.applicationClose(APP_PACKAGE);
        }
    }
}
