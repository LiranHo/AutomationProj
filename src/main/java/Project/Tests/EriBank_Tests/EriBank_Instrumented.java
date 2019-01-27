package Project.Tests.EriBank_Tests;

import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class EriBank_Instrumented extends BaseTest{
    final private boolean instrumentedProperties=true;



    @BeforeAll
    public void init_EriBank_Instrumented() {
        functionPrintInfo(getClass().toString(),"init_EriBank_Instrumented","BeforeAll");

        System.out.println("Start init_EriBank_Instrumented");
      //  super.setup();


        System.out.println("*** EriBank_Instrumented_BaseTest - Install " + Main.EriBankPackageName);
         }


    @BeforeEach
    public void BeforeEach_EriBank_Instrumented() {
        functionPrintInfo(getClass().toString(),"BeforeEach_EriBank_Instrumented","BeforeEach");

        try{
            client.applicationClearData(Main.EriBankPackageName);
        }catch (Exception e){
            String errorOutput= "applicationClearData for: "+Main.EriBankPackageName+ " failed | for device "+device.getSerialnumber()+Main.delimiter+"  "+e.getMessage();
            System.out.println(errorOutput);
            Main.ErrorFile.addRowToReport("Exception",errorOutput);
        }

        //check if need to install
        client.applicationClose("com.android.chrome");
        if (checkIfAppInstall(Main.EriBankPackageName, instrumentedProperties) == false) { //not installed
            client.install(Main.EriBankInstallOnComputerPath, instrumentedProperties, false);
        }
        //try to launch
        try {
            client.launch(Main.EriBankLaunchName, instrumentedProperties, true);
        } catch (Exception e) {

            String errorOutput= "device " + device.getSerialnumber() + " is failed to launch, will try again";
            System.out.println(errorOutput);
            Main.ErrorFile.addRowToReport("Exception",errorOutput);

            client.install(Main.EriBankInstallOnComputerPath, instrumentedProperties, false);
            client.launch(Main.EriBankLaunchName, instrumentedProperties, true);
        }

        //AfterLaunch CheckForPopUp
        if(client.isElementFound("native","xpath=//*[@text='OK']")){
            Main.sout("Info","click on ok in eriBank instrumented class");
            client.click("native","xpath=//*[@text='OK']",0,1);
        }




        //check if need battery monitoring
        if (Main.BatteryMonitoring && Integer.valueOf(device.getVersion()) > 5) {
            System.out.println("# START battery monitoring");
            System.out.println("device " + device.getSerialnumber() + " battery monitoring is on , OS version is: " + device.getVersion());
            try {
                client.startMonitor(Main.EriBankPackageName + ":battery");
            } catch (Exception e) {
                Main.report.addRowToReport("Other", "EriBank_Instrumented_BaseTest " + " start Monitor failed: ", device.getSerialnumber(), "Fail", "", "", "", e.getMessage());
//                    stopRunIf(e);
                Main.BatteryMonitoring = false;
            }
        } else {

            String errorOutput="device " + device.getSerialnumber() + " battery monitoring is off , OS version is: " + device.getVersion() ;
            System.out.println(errorOutput);
            Main.ErrorFile.addRowToReport("NOTE",errorOutput);
        }
    }



    @AfterEach
    public void afterEach_EriBank_Instrumented() {
        functionPrintInfo(getClass().toString(),"afterEach_EriBank_Instrumented","AfterEach");

        if (Main.BatteryMonitoring && Integer.valueOf(device.getVersion()) > 5) {
            System.out.println("# STOP battery monitoring");
            try {
                printBatteryStats();
                String str5 = client.getMonitorsData();
            } catch (Exception e) {

                String errorOutput= "device " + device.getSerialnumber() + " battery monitoring getMonitorsData failed";
                System.out.println(errorOutput);
                Main.ErrorFile.addRowToReport("Exception",errorOutput);

            }
        }

        try {
            String str6 = client.getDeviceLog();
        } catch (Exception e) {

            String errorOutput= "device " + device.getSerialnumber() + "getDeviceLog failed";
            System.out.println(errorOutput);
            Main.ErrorFile.addRowToReport("Exception",errorOutput);


        }
        //super.tearDown();

    }



    @AfterAll
    public void afterAll_EriBank_Instrumented() throws IOException {
        functionPrintInfo(getClass().toString(),"afterAll_EriBank_Instrumented","AfterAll");

    }

}
