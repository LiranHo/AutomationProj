package Project.Tests.WebTests;

import Project.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Webtest_test2_WebLaunches extends WebTests_BaseTest {

    @Test
    @DisplayName("ManyLaunchesForDataSaver")
    public void ManyLaunchesForDataSaver() throws Exception {
        for (int i = 0; i < 100; i++) {
            if(i%11==0){
                client.applicationClose("com.android.chrome");
            }
            launchChromeMechanizem("chrome:ebay.com",true,false);
           // client.launch("chrome:ebay.com",true,false);
            if(client.isElementFound("native","xpath=//*[@text='Save data and browse faster']",0)){
                Main.sout("Info","popUpIsUp");
                throw new Exception("popUpIsUp - ManyLaunchesForDataSaver");
            }
        }
//        xpath=//*[@text='NO THANKS']
//        xpath=//*[@text='TURN ON DATA SAVER']
//        xpath=//*[@text='Save data and browse faster']
    }

  //  @Test
    @DisplayName("Multi Launches with tabs")
    public void Multi_Launches_with_tabs(){
        if(!device.getCategory().toLowerCase().equals("tablet")){ //The tabs in chrome for Tablets not closing as expected
            launchChromeMechanizem("chrome:www.ebay.com",true,false);
            client.applicationClose("com.android.chrome");
            launchChromeMechanizem("chrome:www.amazon.com",true,false);
            client.run("adb shell am start -n com.android.chrome/org.chromium.chrome.browser.ChromeTabbedActivity -d \"www.mako.co.il\" --activity-clear-task ");
            client.applicationClose("com.android.chrome");
            launchChromeMechanizem("chrome:www.ynet.co.il",true,false);

            //close the tabs
            client.click("native","xpath=//*[@id='tab_switcher_button']",0,1);
            client.click("native","xpath=//*[@id='menu_button']",0,1);
            client.click("native","xpath=//*[@text='Close all tabs']",0,1);

            client.applicationClose("com.android.chrome");
            launchChromeMechanizem("chrome:www.bookdepository.com",true,false);
            client.applicationClose("com.android.chrome");
        }

    }

   // @Test
    @DisplayName("Webtest_test2 - WebLaunches")
    public void Test() {
        functionPrintInfo(getClass().toString(), "Test", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        try {
            launchChromeMechanizem("chrome:www.google.com", true, false);
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
        launchChromeMechanizem("chrome:www.imdb.com", true, false);
        if(client.isElementFound("native","xpath=//*[@text='NO THANKS']",0))
            client.click("NATIVE", "xpath=//*[@text='NO THANKS']", 0, 1);
     //   client.click("WEB", "xpath=(//*/*[@nodeName='IMG'])[2]", 0, 1);
        client.capture();
        /*=====*/
        try {
            launchChromeMechanizem("chrome:www.bookdepository.com", true, false);
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
                launchChromeMechanizem("chrome:wikipedia.org/wiki/jerusalem", true, false);
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

