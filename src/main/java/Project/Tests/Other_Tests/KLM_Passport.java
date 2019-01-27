package Project.Tests.Other_Tests;

import Project.BaseTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//SA-25098
public class KLM_Passport extends BaseTest{

    @Ignore
    @DisplayName("KLM_Passport_test")
    @Test
    public void testKLMpassport(){
        functionPrintInfo(getClass().toString(),"Test","Test");
        System.out.println("The application up is: "+client.getCurrentApplicationName());


        client.setProperty("android.install.grant.permissions", "true");
        client.setProperty("android.instrumentation.camera", "true");
        client.install("E:\\Files - Liran - 2\\Applications_apk\\KLM\\KLM Royal Dutch Airlines_v9.10.0_apkpure.com.apk", true, false);
        client.launch("com.afklm.mobile.android.gomobile.klm/com.klm.mobile.android.core.activity.SplashScreenActivity", true, true);
        client.sleep(2000);
        if(client.isElementFound("native","xpath=//*[@text='OK']",0)){
        client.click("NATIVE", "xpath=//*[@text='OK']", 0, 1);}
        client.click("NATIVE", "xpath=//*[@id='profile']", 0, 1);
        if(client.isElementFound("native","xpath=//*[@text='Log in']",0)) {
            client.click("NATIVE", "xpath=//*[@text='Log in']", 0, 1);
            client.elementSendText("NATIVE", "xpath=//*[@id='styled_car_edit_text' and ./parent::*[./parent::*[./parent::*[./parent::*[@id='user_name_field']]]]]", 0, "eyal.kopelevich_p@experitest.com");
            client.elementSendText("NATIVE", "xpath=//*[@id='styled_car_edit_text' and (./preceding-sibling::* | ./following-sibling::*)[@id='text_input_password_toggle']]", 0, "Aa123456");
            client.closeKeyboard();
            client.click("NATIVE", "xpath=//*[@id='action_button_text_view']", 0, 1);
        }
        client.sleep(2000);
        client.waitForElement("native","xpath=//*[@text='Travel documents']",0,50000);
        client.click("NATIVE", "xpath=//*[@text='Travel documents']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Add document']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Add your passport']", 0, 1);
        client.waitForElement("native","xpath=//*[@text='Scan your travel document']",0,50000);
        client.click("NATIVE", "xpath=//*[@text='Scan your travel document']", 0, 1);
        client.waitForElement("NATIVE", "xpath=//*[@class='android.widget.LinearLayout' and ./*[@id='item_scan_option_button_icon']]", 0, 10000);
        client.click("NATIVE", "xpath=//*[@class='android.widget.LinearLayout' and ./*[@id='item_scan_option_button_icon']]", 0, 1);
        if(!client.getCurrentApplicationName().contains("afklm")){
            System.err.println("Crashed before enter to camera"+device.getSerialnumber());
        }
        client.sleep(5000);

        client.simulateCapture("E:\\Files - Liran - 2\\Applications_apk\\KLM\\passportKLM.png");
        client.simulateCapture("E:\\Files - Liran - 2\\Applications_apk\\KLM\\try_different_scales_photos\\00e2e5fb3fdc464b\\1.jpg");
    }


}
