package Project.Tests.Other_Tests;

import Project.BaseTest;
import Project.Main;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DeviceActions_Test extends BaseTest{


    @DisplayName("DeviceActions_Test_FlipDevice")
    @Test
    public void DeviceActions_Test_FlipDevice(){
        if(device.getSerialnumber().equals("3230d293cf7611a3")){

        }else {
            functionPrintInfo(getClass().toString(), "DeviceActions_Test_FlipDevice", "Test");
            System.out.println("The application up is: " + client.getCurrentApplicationName());

            for (int i = 0; i < 10; i++) {
                System.out.println("function flip count: i=" + i);
                //  client.launch("com.android.chrome/com.google.android.apps.chrome.Main", false, false);
                launchChromeMechanizem("chrome:http://www.ebay.com", true, false);
                client.deviceAction("Landscape");
                client.deviceAction("Unlock");
                client.launch("com.android.chrome/com.google.android.apps.chrome.Main", false, false);
                client.deviceAction("Portrait");
                client.deviceAction("Unlock");
                client.launch("com.android.chrome/com.google.android.apps.chrome.Main", false, false);
                client.deviceAction("Landscape");
                client.deviceAction("Unlock");
                client.launch("com.android.chrome/com.google.android.apps.chrome.Main", false, false);
                client.deviceAction("Portrait");
                client.deviceAction("Unlock");
            }
        }
    }

    @DisplayName("DeviceActions_Test_FlipDeviceONLY")
    @Test
    public void DeviceActions_Test_FlipDeviceONLY() {
        if (device.getSerialnumber().equals("3230d293cf7611a3")) {

        } else {
            functionPrintInfo(getClass().toString(), "DeviceActions_Test_FlipDeviceONLY", "Test");
            System.out.println("The application up is: " + client.getCurrentApplicationName());


            client.deviceAction("Portrait");
            client.launch("com.android.chrome/com.google.android.apps.chrome.Main", false, false);
            for (int i = 0; i < 20; i++) {
                System.out.println("function chrome flip count: i=" + i);
                client.deviceAction("Landscape");
                client.deviceAction("Portrait");
            }


            client.deviceAction("Portrait");

            Random randomno = new Random();
            boolean instrumentedProperties = randomno.nextBoolean();

            if (checkIfAppInstall(Main.EriBankPackageName, instrumentedProperties) == false) { //not installed
                client.install(Main.EriBankInstallOnComputerPath, instrumentedProperties, false);
            }
            client.launch(Main.EriBankLaunchName, instrumentedProperties, false);

            for (int i = 0; i < 20; i++) {
                System.out.println("function eribank flip count: i=" + i);
                client.deviceAction("Landscape");
                client.deviceAction("Portrait");
            }

        }
    }
    }
