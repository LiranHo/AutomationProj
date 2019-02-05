package Project.Tests.WebTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Webtest_test3_HybridElement extends WebTests_BaseTest {
    @Test
    @DisplayName("Webtest_test3_HybridElement")
    public void Test() {
        functionPrintInfo(getClass().toString(), "Test", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        client.install("E:\\Files - Liran - 2\\Applications_apk\\Simple app for web browser\\com.example.app.MainActivity.2.apk", true, false);
        client.launch("com.example.app/.MainActivity", true, true);
        client.sleep(1000);
        if(        client.isElementFound("native", "xpath=//*[@nodeName='I' and ./parent::*[@nodeName='A' and ./parent::*[@text='        ']]]", 0)){
            client.click("web","xpath=//*[@nodeName='I' and ./parent::*[@nodeName='A' and ./parent::*[@text='        ']]]",0,1);}
        //client.setProperty("element.visibility.level", "center");
        client.isElementFound("WEB", "xpath=(//*/*/*/*/*[@nodeName='IMG' and @width>0 and ./parent::*[@text and @nodeName='A' and ./parent::*[@nodeName='DIV' and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@nodeName='DIV']]] and ./*[./*[@nodeName='P']]]]]])[1]", 0);
        client.elementSwipeWhileNotFound("WEB", " ", "Down", 100, 800, "WEB", "xpath=(//*/*/*/*/*[@nodeName='IMG' and @width>0 and ./parent::*[@text and @nodeName='A' and ./parent::*[@nodeName='DIV' and (./preceding-sibling::* | ./following-sibling::*)[./*[./*[./*[@nodeName='DIV']]] and ./*[./*[@nodeName='P']]]]]])[1]", 0, 1000, 5, true);

        client.sleep(1000);
        client.verifyElementFound("web","xpath=//*[@nodeName='I' and ./parent::*[@text='                                                            ']]",0);
    }

}



