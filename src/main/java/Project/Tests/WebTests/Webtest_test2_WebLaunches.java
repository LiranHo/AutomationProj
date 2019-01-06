package Project.Tests.WebTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Webtest_test2_WebLaunches extends WebTests_BaseTest {
    @Test
    @DisplayName("Webtest_test2 - WebLaunches")
    public void Test() {
        functionPrintInfo(getClass().toString(), "Test", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        try {
            client.launch("chrome:www.google.com", true, false);
            if(client.isElementFound("native","xpath=//*[@text='NO THANKS']",0))
                client.click("NATIVE", "xpath=//*[@text='NO THANKS']", 0, 1);
            client.elementSendText("WEB", "xpath=//*[@name='q']", 0, "lmgtfy");
            client.click("WEB", "xpath=//*[@nodeName='BUTTON'][1]", 0, 1);
            client.capture();
        } catch (Exception e1) {
            client.report("failed to launch",false);
            System.out.println("launch chrome:www.google.com failed");
        }
        /*=====*/
        client.launch("chrome:www.imdb.com", true, false);
        if(client.isElementFound("native","xpath=//*[@text='NO THANKS']",0))
            client.click("NATIVE", "xpath=//*[@text='NO THANKS']", 0, 1);
     //   client.click("WEB", "xpath=(//*/*[@nodeName='IMG'])[2]", 0, 1);
        client.capture();
        /*=====*/
        try {
            client.launch("chrome:www.bookdepository.com", true, false);
            if(client.isElementFound("native","xpath=//*[@text='NO THANKS']",0))
                client.click("NATIVE", "xpath=//*[@text='NO THANKS']", 0, 1);
            client.click("WEB", "xpath=(//*/*/*/*/*[@nodeName='IMG' and @width>0 and ./parent::*[@text and @nodeName='A' and ./parent::*[(./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@nodeName='DIV']]]]]] and (./preceding-sibling::* | ./following-sibling::*)[@text]])[1]", 0, 1);
            client.capture();
        } catch (Exception e2) {
            client.report("failed to launch",false);
            System.out.println("launch chrome:www.bookdepository.com failed");
        }
            /*=====*/
            try {
                client.launch("chrome:wikipedia.org/wiki/jerusalem", true, false);
                if(client.isElementFound("native","xpath=//*[@text='NO THANKS']",0))
                    client.click("native","xpath=//*[@text='NO THANKS']",0,1);
                client.swipe("down", 0, 3000);
                client.capture();
            } catch (Exception e3) {
                client.report("failed to launch",false);
                System.out.println("launch chrome:wikipedia.org/wiki/jerusalem failed");
                /*=====*/
            }

        }


    }

