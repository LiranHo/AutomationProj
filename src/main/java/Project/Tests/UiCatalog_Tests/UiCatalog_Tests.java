package Project.Tests.UiCatalog_Tests;

import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class UiCatalog_Tests extends UiCatalog_Instrumented{


    //SA-20237
    @Test
    @DisplayName("getLocation")
    public void getLocation() {
        try {
            // Set location provider to network (to be able get location in cloud room :/ )
            client.run("adb shell settings put secure location_providers_allowed -gps");
            client.run("adb shell settings put secure location_providers_allowed +network");

            if (client.isElementFound("Native", "//*[@text='Agree'] | //*[@text='AGREE']", 0)) {
                client.click("Native", "//*[@text='Agree'] | //*[@text='AGREE']", 0, 1);
            }

            // Go to googleMaps activity and allow permissions
            client.swipe("Down", client.p2cy(50), 1000);
            client.click("Native", "//*[@text='GoogleMaps']", 0, 1);

            if (client.isElementFound("Native", "//*[@text='ALLOW'] | //*[@text='Allow']", 0)) {
                client.click("Native", "//*[@text='ALLOW'] | //*[@text='Allow']", 0, 1);
            }


            client.setProperty("location.service.gps", "true");
            client.setProperty("location.service.network", "false");

            String x1 = "20.593684";
            String y1 = "78.962880";
            String x2 = "64.670920";
            String y2 = "-17.929688";

            client.setLocation(x1, y1);  // India
            String[] location1 = client.getLocation().split(",");

            Assert.state(x1.startsWith(location1[0]),"getLocation return wrong latitude . latitude should be "+x1+ " but was "+ location1[0]);
            Assert.state(y1.startsWith(location1[1]),"getLocation return wrong longitude . longitude should be "+y1+ " but was "+ location1[1]);


            client.setLocation(x2, y2);  // Iceland
            String[] location2 = client.getLocation().split(",");

            Assert.state(x2.startsWith(location2[0]),"getLocation return wrong latitude . latitude should be "+x2+ " but was "+ location2[0]);
            Assert.state(y2.startsWith(location2[1]),"getLocation return wrong longitude . longitude should be "+y2+ " but was "+ location2[1]);


        }
        finally {

            client.run("adb shell settings put secure location_providers_allowed -gps");
            client.run("adb shell settings put secure location_providers_allowed -network");
        }

    }


}
