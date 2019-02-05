package Project.Tests.WebTests;

import Project.BaseTest;
import Project.Tests.EriBank_Tests.EriBank_Instrumented;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Webtest_test1_basic extends WebTests_BaseTest {
    @Test
    @DisplayName("Webtest_test1 - basic")
    public void Webtest_test1_basic() {
        functionPrintInfo(getClass().toString(), "Webtest_test1_basic", "Test");
        System.out.println("The application up is: " + client.getCurrentApplicationName());
        /*~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

        final String searchBox = "//*[(@id='kw' and @name='_nkw') or @id='gh-ac-box2']";
        final String searchButton = "//*[@id='searchTxtBtn' or @id='gh-btn' or @id='ghs-submit']";
//        final String searchButton = "id=ghs-submit";

        final String tabElement = "//*[@class='srp-item__title' or @class='grVwBg' or @class='s-item' or @class='s-item__image']";

        launchChromeMechanizem("chrome:m.ebay.com", true, true);
        client.hybridWaitForPageLoad(30000);
        client.waitForElement("WEB", searchBox, 0, 30000);
        client.elementSendText("WEB", searchBox, 0, "Hello");

        client.click("WEB", searchButton, 0, 1);

        //client.click("WEB", searchButton, 0, 1);
        if(client.isElementFound("WEB",tabElement,0)){
            client.getAllValues("WEB", tabElement, "text");
        }
        else{
            client.report("element "+tabElement +"not found", false);
        }
        client.elementSendText("WEB", searchBox, 0, "Hello Again");
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        if(!client.isElementFound("WEB",tabElement,0)){
            client.report("element "+tabElement +"not found", false);
        }
        client.elementSendText("WEB", searchBox, 0, "3rd Time");
        String searchText = client.elementGetProperty("WEB", searchBox, 0, "value");
        System.out.println(searchText);
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        if(!client.isElementFound("WEB",tabElement,0)){
            client.report("element "+tabElement +"not found", false);
        }


        launchChromeMechanizem("chrome:ebay.com", true, false);
        client.elementSendText("WEB", "xpath=//*[@name='_nkw']", 0, "yellow");

        try {
            client.click("WEB", searchButton, 0, 1);
//            client.click("WEB", "id=ghs-submit", 0, 1);

        }catch (Exception e){
        }
        System.out.println(client.getVisualDump("WEB"));


        //client.click("WEB", "xpath=//*[@nodeName='IMG'][1]", 0, 1);

    }


}
