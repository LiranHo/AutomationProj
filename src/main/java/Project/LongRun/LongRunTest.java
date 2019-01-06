package Project.LongRun;
import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LongRunTest extends BaseTest {
    public static String UiCatalogInstallOnComputerPath = "C:\\Users\\liran.hochman\\Downloads\\apk\\New experitest apps\\UICatalog.apk";
    public static String EriBankInstallOnComputerPath = "C:\\Users\\liran.hochman\\Downloads\\apk\\eribank.apk";
    public static String EriBankLaunchName = "com.experitest.ExperiBank/.LoginActivity";
    public static String EriBankPackageName = "com.experitest.ExperiBank";
    public static String UiCatalogLaunchName = "com.experitest.uicatalog/.MainActivity";
    public static String EriBankLaunchName_old = "com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
    public static String EriBankPackageName_old = "com.experitest.eribank";


    //@Test
    @DisplayName("Long Run Test - no release")
    public void Test(){
        System.out.println("Long Run Test - no release");
        long startTime = System.currentTimeMillis();
        long endTime;
        long totalTime=0;
        //while(totalTime<Main.TimeLoop){
        while(totalTime<Main.TimeToRun){
            endTime   = System.currentTimeMillis();
            totalTime = (endTime - startTime)/1000;

            client.deviceAction("unlock");

            //TESTS adb commands parallel
//                adb_commands_parallel();
            //TESTS long run
                AllTest();
            }

        }

        @Test
    @DisplayName("adb_commands_parallel")
    public void adb_commands_parallel() {
        String name = "adb_commands_parallel";
        path = client.setReporter("xml", this.FolderinnerDeviceDirPath, name); //set Project.report, called at the beginning of each test and define the Project.report parameters


        int numberOfCommandsInTheSwitch = 10;
        Random rand = new Random(System.currentTimeMillis());
        int StartNumber = rand.nextInt(numberOfCommandsInTheSwitch) + 1; //11 is the maximum and the 1 is our minimum

        for (int i = 0; i < 50; i++) {
            int num = ((StartNumber + i) % numberOfCommandsInTheSwitch) + 1;
            System.out.println(num);
            try {
                switch (num) {
                    case 1:
                        client.install(EriBankInstallOnComputerPath, true, false);
                        client.launch(EriBankLaunchName, true, false);
                        break;
                    case 2:
                        client.deviceAction("unlock");
                        break;
                    case 3:
                        client.closeKeyboard();
                        break;
                    case 4:
                        client.closeAllApplications();
                        break;
                    case 5:
                        client.applicationClearData(EriBankPackageName);
                        break;
                    case 6:
                        client.install(EriBankInstallOnComputerPath, true, false);
                        client.uninstall(EriBankPackageName);
                        break;
                    case 7:
                        client.getDeviceLog();
                        break;
                    case 8:
                        client.deviceAction("Recent Apps");
                        client.deviceAction("home");
                        break;
                    case 9:
                        client.setLocation("0", "0");
                        break;
                    case 10:
                        client.launch("chrome:m.ebay.com", true, false);
                        break;
                    //      case 11: client.reboot(200000);  client.deviceAction("unlock"); break;
                }
            } catch (Exception e) {
                throw e;
                //InsideException("adb_commands_parallel - " + num + " | ", e);
            }
        }


       }

        protected void InsideException(String name, Exception e){
        String error=e.getMessage().replaceAll("\n"," | ");
        Main.report.addRowToReport("LongRun",name,Thread.currentThread().getName(),"Fail","-",client.getSessionID(),path,error);
        }


        @Test
       protected void AllTest(){

        List<String> functions = new ArrayList<>();
        functions.add("AllEriBankTests");
        functions.add("AllWebTests");
        functions.add("AllNonInstrumentedEriBankTests");


            long startTime = System.currentTimeMillis();
            long endTime;
            long totalTime=0;
            int i= 0;
            while(totalTime<Main.TimeToRun) {
                i++;
                endTime = System.currentTimeMillis();
                totalTime = (endTime - startTime) / 1000;

                for (String name : functions) {
                    try {
                        System.out.println(name);
                        path = client.setReporter("xml", this.FolderinnerDeviceDirPath, name); //set Project.report, called at the beginning of each test and define the Project.report parameters
                        switch (name) {
                            case "AllEriBankTests":
                                AllEriBankTests();
                                break;
                            case "AllWebTests":
                                AllWebTests();
                                break;
                            case "AllNonInstrumentedEriBankTests":
                                AllNonInstrumentedEriBankTests();
                                break;
                        }

                        Main.report.addRowToReport("LongRun", name, Thread.currentThread().getName(), "Pass", "-", client.getSessionID(), path, "");
                    } catch (Exception e) {
                        InsideException("AllTest - " + name, e);
                    }
                    generateReport();
                }
                Main.report.addRowToReport("end of loop","Long Run AllTest","","no status",String.valueOf(totalTime),"loop #: "+i,"","");
            }

    }



//    @Test
    @DisplayName("Long Run - All Non Instrumented EriBank Tests")
    protected void AllNonInstrumentedEriBankTests() {
        testName= "NonInstrumentedEriBank - EriBankNONInstrumentedTest1";
        client.install(EriBankInstallOnComputerPath, false, false);


        String userName = "company";
        String password = "company";
        client.deviceAction("Portrait");
        client.launch(EriBankLaunchName, false, true);
        System.out.println("launch 1 ");
        client.deviceAction("Home");
        client.launch(EriBankLaunchName, false, true);
        System.out.println("launch 2");
        client.deviceAction("Landscape");
        //if already logged in-> log out
        if (client.waitForElement("NATIVE", "//*[@text='Make Payment']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Logout']", 0, 1);
        }
        client.deviceAction("Portrait");
        //if Error - send close
        if (client.waitForElement("NATIVE", "//*[@text='Error']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Close']", 0, 1);
        }
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, "company"); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, "company"); //send the text from the csv file to "password"
        client.click("NATIVE", "//*[@text='Login']", 0, 1);
//        client.sleep(500);
        client.isElementFound("NATIVE", "//*[@text='Make Payment']", 0);
        { // check if the user is log in
//            Report.addToReport("EriBank login check succeed");
        }

        client.deviceAction("portrait");
        String remoteFile = client.capture("Capture");
        client.deviceAction("Landscape");
        //get the balance from the webView
        String balanceString="xpath=//*[@class='android.webkit.WebView' and ./*[@class='android.view.View']]";
//                System.out.println("balanceFirst ="+ Double.parseDouble(client.elementGetText("WEB", balanceString, 0).split(": ")[1].substring(0, client.elementGetText("WEB", balanceString, 0).split(": ")[1].length() - 1)));

        client.click("NATIVE", "xpath=//*[@id='makePaymentButton']", 0, 1);
//                client.click("NATIVE", "xpath=//*[@id='makePaymentButton']", 0, 1);
        client.elementSendText("NATIVE", "xpath=//*[@id='phoneTextField']", 0, "phone");
        client.closeKeyboard();
        client.click("NATIVE", "xpath=//*[@id='nameTextField']",0,1);
        client.sendText("Name");
        client.closeKeyboard();
//        client.elementSendText("NATIVE", "xpath=//*[@id='nameTextField']", 0, "Name");
        client.click("NATIVE", "xpath=//*[@id='amountTextField']",0,1);
        client.sendText("5");
        client.closeKeyboard();
//        client.elementSendText("NATIVE", "xpath=//*[@id='amountTextField']", 0, "5");
        client.click("NATIVE", "xpath=//*[@text='Select']", 0, 1); //choose country
        client.elementListSelect("xpath=//*[@id='countryList']", "text=Spain", 0, false);
        client.click("NATIVE", "text=Spain", 0, 1);
        client.deviceAction("Portrait");
//        if(client.isElementFound("NATIVE", "xpath=//*[@text='Send Payment']", 0)) { // check if user name or password are not correct.
        client.click("NATIVE", "xpath=//*[@text='Send Payment']", 0, 1);
        if(client.isElementFound("NATIVE", "xpath=//*[@text='Are you sure you want to send payment?']", 0)) { // check if user name or password are not correct.
            client.click("NATIVE", "xpath=//*[@text='Yes']", 0, 1);}
        if(client.isElementFound("NATIVE", "xpath=//*[@text='Make Payment']", 0)) { // check if user name or password are not correct.
//                    System.out.println("balanceAfter =" + Double.parseDouble(client.elementGetText("WEB", balanceString, 0).split(": ")[1].substring(0, client.elementGetText("WEB", balanceString, 0).split(": ")[1].length() - 1)));
        }
        client.deviceAction("Landscape");
        client.deviceAction("Portrait");

        //        client.startMonitor(Project.Main.EriBankPackageName+":battery");

        String str5 = client.getMonitorsData();
        String str6 = client.getDeviceLog();

        client.launch(EriBankLaunchName, false, true);
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, "company"); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, "company"); //send the text from the csv file to "password"
        client.click("NATIVE", "//*[@text='Login']", 0, 1);
        client.isElementFound("NATIVE", "//*[@text='Make Payment']", 0);

        String getCounter_cpu = client.getCounter("cpu");
        String getCounter_memory = client.getCounter("memory");
        String getCounter_battery = client.getCounter("battery");


        for (int i = 0; i < 10; i++) {
            client.deviceAction("Landscape");
            client.click("NATIVE", "xpath=//*[@id='makePaymentButton']", 0, 1);
            client.deviceAction("Portrait");
            client.click("NATIVE", "xpath=//*[@text='Cancel']", 0, 1);
//            client.click("NATIVE", "xpath=//*[@text='Send Payment']", 0, 1);
//            client.deviceAction("back");
        }


    }

//    @Test
    @DisplayName("Long Run - All Web Tests")
    protected void AllWebTests() {
        client.setNetworkConnection("wifi",true);

        final String searchBox = "//*[(@id='kw' and @name='_nkw') or @id='gh-ac-box2']";
        final String searchButton = "//*[@id='searchTxtBtn' or @id='gh-btn' or @id='ghs-submit']";
//        final String searchButton = "id=ghs-submit";

        final String tabElement = "//*[@class='srp-item__title' or @class='grVwBg' or @class='s-item']";

        client.launch("chrome:m.ebay.com", true, true);
        client.hybridWaitForPageLoad(30000);
        client.waitForElement("WEB", searchBox, 0, 30000);
        client.elementSendText("WEB", searchBox, 0, "Hello");

        client.click("WEB", searchButton, 0, 1);

        //client.click("WEB", searchButton, 0, 1);
        client.sleep(5000);
        client.isElementFound("WEB", tabElement, 0);
        try {
            //   client.getAllValues("WEB", tabElement, "text");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
            //      client.getAllValues("WEB", tabElement, "text");
        }
        client.elementSendText("WEB", searchBox, 0, "Hello Again");
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        client.isElementFound("WEB", tabElement, 0);
        client.elementSendText("WEB", searchBox, 0, "3rd Time");
        String searchText = client.elementGetProperty("WEB", searchBox, 0, "value");
        System.out.println(searchText);
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        client.isElementFound("WEB", tabElement, 0);



        client.launch("chrome:ebay.com", true, false);
        client.elementSendText("WEB", "xpath=//*[@name='_nkw']", 0, "yellow");

        try {
            client.click("WEB", searchButton, 0, 1);
//            client.click("WEB", "id=ghs-submit", 0, 1);

        }catch (Exception e){
        }
        System.out.println(client.getVisualDump("WEB"));


        client.click("WEB", "xpath=//li[@id=\"srp-river-results-listing1\" and @class=\"s-item\"]//a[@class=\"s-item__link\"]//div[@class=\"s-item__wrapper clearfix\"]//div[@class=\"s-item__image-section\"]//div[@class=\"s-item__image\"]//div[@class=\"s-item__image-wrapper\"]//img[@class=\"s-item__image-img\"]", 0, 1);



        //hybrid run java script at ebay website
        client.launch("http://www.ebay.com", true, true);
        String serach_input_id = client.elementGetProperty("WEB", "xpath=//*[@nodeName='INPUT']", 0, "id");
        String result = client.hybridRunJavascript("", 0, "var result = window.document.getElementById(\"" + serach_input_id + "\").value = \"hello\";");

        //Hybrid get html
        client.launch("http://www.ebay.com", true, false);
        client.isElementFound("WEB","xpath=//*[@text='eBay']",0);
//        if(client.isElementFound("NATIVE","xpath=//*[@id='button_secondary']",0)){
//            client.click("NATIVE","xpath=//*[@id='button_secondary']",0,1);
//        }
//        try { client.click("NATIVE", "xpath=//*[@id='button_secondary']", 0, 1); }catch (Exception e){}

        String getHtml = client.hybridGetHtml("\"id=gh-mlogo\"", 0);
        System.out.println();


        //clear cash
        client.hybridClearCache(true, true);


        //Hybrid select
        client.launch("https://www.google.co.il/preferences?hl=en-IL&sa=X&ved=0ahUKEwiQ0q-syNHWAhWTF8AKHW4SAzAQn5cCCAg", true, false);
        client.swipe("DOWN", 0, 2000);
        client.hybridSelect("adb:Nexus 7", 0, "id", "hl", "English");
        client.hybridSelect("", 0, "id", "hl", "euskara");

        //Hybrid wait for page to load
        client.launch("chrome:http://www.amazon.com", true, false);
        client.hybridWaitForPageLoad(30000);

        //****** need to make test only that that need to fail
//        client.launch("chrome:http://www.cnn.com", true, false);
//        try { //need to fail
//            client.hybridWaitForPageLoad(30000);
//        }catch(Exception e){}


        //imdb element names
//            String imdbGameOfThrone=" //*[@id='autocomplete' and @nodeName='DIV']/*[@nodeName='A' and ./*[./*[@nodeName='IMG']]])[1] | //*[@nodeName='A' and @width>0 and ./*[@nodeName='IMG'] and ./*[@text=' ' and ./*[@text='(2011)'] and ./*[@text='Game of Thrones']]]";
        String imdbGameOfThrone="xpath=//*[@text='Game of Thrones' and @nodeName='SPAN'][1]";
        client.launch("http://imdb.com/", true, false);
        client.capture("Capture");
        client.elementSendText("WEB", "//*[@name='q']", 0, "Game of Thrones");
        if(client.waitForElement("web","xpath=//*[@id='autocomplete' and @nodeName='DIV']",0,10000)) {
//            client.click("WEB", "//*[@nodeName='A' and @width>0 and ./*[@nodeName='IMG'] and ./*[@text=' ' and ./*[@text='(2011)'] and ./*[@text='Game of Thrones']]]", 0, 1);
            client.click("WEB", imdbGameOfThrone, 0, 1);
        }else {
            client.click("web", "xpath=//*[@id='suggestion-search-button' and @text]", 0, 1);
            client.click("web", "xpath=//*[@text='Game of Thrones' and (./preceding-sibling::* | ./following-sibling::*)[@text='(2011)(TV Series)']]", 0, 1);
        }
        client.capture("Capture");


        client.install("com.example.app/.MainActivity", true, false);
        client.launch("com.example.app/.MainActivity", true, true);
        client.sleep(1000);
        if(        client.isElementFound("native", "xpath=//*[@nodeName='I' and ./parent::*[@nodeName='A' and ./parent::*[@text='        ']]]", 0)){
            client.click("web","xpath=//*[@nodeName='I' and ./parent::*[@nodeName='A' and ./parent::*[@text='        ']]]",0,1);}
        //client.setProperty("element.visibility.level", "center");
        client.isElementFound("WEB", "xpath=(//*/*/*/*/*[@nodeName='IMG' and @width>0 and ./parent::*[@text and @nodeName='A' and ./parent::*[@nodeName='DIV' and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@nodeName='DIV']]] and ./*[./*[@nodeName='P']]]]]])[1]", 0);
        client.elementSwipeWhileNotFound("WEB", " ", "Down", 100, 800, "WEB", "xpath=(//*/*/*/*/*[@nodeName='IMG' and @width>0 and ./parent::*[@text and @nodeName='A' and ./parent::*[@nodeName='DIV' and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@nodeName='DIV']]] and ./*[./*[@nodeName='P']]]]]])[1]", 0, 1000, 5, true);

        client.sleep(1000);
        client.isElementFound("web","xpath=//*[@nodeName='I' and ./parent::*[@text='                                                            ']]",0);

    }


//    @Test
    @DisplayName("Long Run - All Instrumented EriBank Tests")
    protected void AllEriBankTests() {

        client.deviceAction("unlock");
        // client.uninstall(Main.EriBankPackageName_old);
        client.install(EriBankInstallOnComputerPath, true, false);
        client.launch(EriBankLaunchName, true, true);

        System.out.println("*** EriBank_Instrumented_test1 - check login");

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
                client.sleep(2000);
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
                    System.err.println("Exception: "+e.getMessage());
                }
            }

        }

        System.out.println("*** EriBank_Instrumented_test2 - Basic Test");

        String userName1 = "company";
        String password1 = "company";

        String appName = client.getCurrentApplicationName();

        //if already logged in-> log out
        if (client.waitForElement("NATIVE", "//*[@text='Make Payment']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Logout']", 0, 1);
        }
        //if Error - send close
        if (client.waitForElement("NATIVE", "//*[@text='Error']", 0, 1000)) {
            client.click("NATIVE", "//*[@text='Close']", 0, 1);
        }
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, userName1); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, password1); //send the text from the csv file to "password"

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

        System.out.println("*** EriBank_Instrumented_test3 - Clear Data");

        client.launch(EriBankLaunchName, true, true);
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
        client.applicationClose(EriBankPackageName);
        client.applicationClearData(EriBankPackageName);
        client.launch(EriBankLaunchName, true, true);
//        client.launch("", true, true);
        client.elementSendText("NATIVE", "id=usernameTextField", 0, "company");
        client.elementSendText("NATIVE", "id=passwordTextField", 0, "company");
        client.click("NATIVE", "text=Login", 0, 1);
        client.waitForElement("native","id=balanceWebView",0,10000);
        client.verifyIn("NATIVE", "id=balanceWebView", 0, "Inside", "WEB", "text=Your balance is: 100.00$", 0, 0);
        String str0 = client.getDeviceLog();

        System.out.println("*** EriBank_Instrumented_Test4 - Native Call Api");
        client.click("NATIVE", "//*[@text='Logout']", 0, 1);
        String str11 = client.runNativeAPICall("NATIVE", "xpath=//*[@id='usernameTextField']", 0, "view.setHint(\"Native_Call_Api_Test\");");
        client.verifyElementFound("NATIVE", "xpath=//*[@hint='Native_Call_Api_Test']", 0);


    }


    protected void generateReport(){
        try{
            client.generateReport(false);
        }catch (Exception e1){
            System.out.println("Cant generate Report");

        }
    }


    }


