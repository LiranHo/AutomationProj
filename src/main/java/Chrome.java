import com.experitest.client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Chrome extends baseTest {


    @BeforeAll
    public static void setupChrome(){
        System.err.println("***********************************test chrome");
//        client.launch("chrome:ebay.com",true,true);
    }

    @Test
    public void newTest(){
        System.err.println("***********************************test chrome");
        client.sleep(1000);
        EriBankNONInstrumentedTest1();


    }


    public void EriBankNONInstrumentedTest1() {
        for(int i=0;i<50;i++){
            System.out.printf("%s: %s: %s%n", i, Thread.currentThread().getName(), client.getSessionID());
            client.swipe("left",1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void EriBankNONInstrumentedTest2() {

        String userName = "company";
        String password = "company";
        client.deviceAction("Portrait");
        client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
        System.out.println("launch 1 ");
        client.deviceAction("Home");
        client.launch("com.experitest.ExperiBank/.LoginActivity", false, true);
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
        client.elementSendText("NATIVE", "//*[@id='usernameTextField']", 0, userName); //send the text from the csv file to "user name"
        client.elementSendText("NATIVE", "//*[@id='passwordTextField']", 0, password); //send the text from the csv file to "password"


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
        }




}
