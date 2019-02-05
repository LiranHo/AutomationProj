package Project.Tests.WebTests;

import Project.Main;
import org.boon.primitive.Int;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Webtest_test4_GboardKeyboard extends WebTests_BaseTest {
    @Test
    @DisplayName("Webtest_test4_GboardKeyboard")
    public void Test() {
        functionPrintInfo(getClass().toString(), "Test", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        //Gboard Keyboard
        initKeyboard();

        for (int i = 0; i <2 ; i++) {

            client.launch("http://www.amazon.com", false, true);
            client.sleep(1000);
            client.click("native", "xpath=//*[@id='url_bar']", 0, 1);
            if(client.isElementFound("native","//*[@text='NO, THANKS']",0)){
                client.click("native","//*[@text='NO, THANKS']",0,1);
            }
            client.sleep(1000);
            client.elementSendText("native","//*[@id='url_bar']",0,"");
            clickKeyboard('e');
            clickKeyboard('b');
            clickKeyboard('a');
            clickKeyboard('y');
            clickKeyboard('.');
            clickKeyboard('c');
            clickKeyboard('o');
            clickKeyboard('m');
            client.click("native", "nixpath=//*[@contentDescription='Go']", 0, 1);
            client.verifyElementFound("native", "//*[@text='eBay Logo' or @text='eBay']", 0);
//            client.verifyElementFound("native", "nixpath=//*[@id='gh-mlogo']", 0);
        }

    }

    protected void clickKeyboard(char Letter){
        client.click("native","nixpath=//*[@contentDescription='"+Letter+"']",0,1);

    }

    private void initKeyboard() {
        client.closeAllApplications();
        client.launch("com.android.vending/com.google.android.finsky.activities.MainActivity", false, false);
        client.click("NATIVE", "xpath=//*[@id='search_box_idle_text']", 0, 1);
        client.sleep(500);
        client.sendText("Gboard{ENTER}");
        if(client.isElementFound("NATIVE", "xpath=//*[@text='INSTALL']", 0)){
            client.click("NATIVE", "xpath=//*[@text='INSTALL']", 0, 1);
            client.waitForElement("NATIVE", "xpath=//*[@text='UNINSTALL']", 0, 100000);
        }
        client.run("adb shell ime enable com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME");
        client.run("adb shell ime set com.google.android.inputmethod.latin/com.android.inputmethod.latin.LatinIME");
        client.deviceAction("Home");
    }

}



