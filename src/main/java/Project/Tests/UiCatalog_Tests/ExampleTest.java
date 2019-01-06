package Project.Tests.UiCatalog_Tests;

import Project.BaseTest;
import Project.Main;

public class ExampleTest extends BaseTest{


/*
    public class UiCatalog extends BaseTest {

        public UiCatalog(String serialNumber, String OS, int deviceNum, String testName) throws Exception {
            super(serialNumber, OS, deviceNum, testName);
        }


        public void test() throws Exception {
            testName= "UiCatalog - SendTextCheck";
            SendTextCheck();
        }

        public boolean test1() throws Exception {
            testName= "UiCatalog - WebView";
            WebView();
            return true;
        }

//    public boolean test2() throws Exception {
//        testName= "";
//
//        return true;
//    }


        //***********************************************\\
        public void SendTextCheck() {
            client.install(Main.UiCatalogInstallOnComputerPath, true, false);
            client.launch(Main.UiCatalogLaunchName , true, true);
            client.click("NATIVE", "xpath=//*[@text='Text Fields']", 0, 1);
            client.click("NATIVE", "xpath=//*[@hint='Normal Text field']", 0, 1);
            client.sendText("123");
            client.closeKeyboard();
            client.click("NATIVE", "xpath=//*[@text='Name' or @hint='Name']", 0, 1);
            client.sendText("456");
            client.closeKeyboard();
            client.swipe("down",150,500);
            client.click("NATIVE", "xpath=//*[@hint='Postal Address']", 0, 1);
            client.sendText("789");
            client.closeKeyboard();
            client.click("NATIVE", "xpath=//*[@hint='MultiLine Text']", 0, 1);
            client.closeKeyboard();
            client.sendText("101112");
        }


        public void WebView(){
            client.install(Main.UiCatalogInstallOnComputerPath, true, false);
            client.launch(Main.UiCatalogLaunchName , true, true);
            client.elementSwipeWhileNotFound("NATIVE", "xpath=//*[@class='android.widget.RelativeLayout']", "Down", 100, 2000, "NATIVE", "xpath=//*[@text='Css WebView']", 0, 1000, 5, true);
            client.click("WEB", "xpath=//*[@id='usr']", 0, 1);
            client.sendText("a");
            client.closeKeyboard();
            client.click("WEB", "xpath=//*[@id='pswd']", 0, 1);
            client.sendText("a");
            client.closeKeyboard();
            client.click("WEB", "xpath=//*[@text='Login']", 0, 1);
            if(client.isElementFound("NATIVE", "xpath=//*[@text='login success']", 0)){
            }else{
                client.click("WEB", "this test failed because login didn't success", 0, 1);
            }

//        client.launch("com.experitest.uicatalog/.MainActivity", false, true);
//        client.click("NATIVE", "xpath=//*[@text='WebElements']", 0, 1);
//        client.waitForElement("NATIVE", "xpath=//*[@id='UrlText']", 0, 10000);
//            String str1 = client.getCounter("cpu:com.experitest.uicatalog");
//            String str2 = client.getCounter("memory:com.experitest.uicatalog");
        }



    }

    */
}
