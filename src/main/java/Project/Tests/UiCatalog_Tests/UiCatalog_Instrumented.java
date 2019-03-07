package Project.Tests.UiCatalog_Tests;

import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class UiCatalog_Instrumented extends BaseTest{
    final private boolean instrumentedProperties=true;

    @BeforeAll
    public void init_UiCatalog_Instrumented() {
        functionPrintInfo(getClass().toString(),"init_UiCatalog_Instrumented","BeforeAll");
        System.out.println("Start init_UiCatalog_Instrumented");
      //  super.setup();
        System.out.println("*** UiCatalog_Instrumented_BaseTest - Install " + Main.UiCatalogPackageName);
         }


    @BeforeEach
    public void BeforeEach_UiCatalog_Instrumented() {
        functionPrintInfo(getClass().toString(),"BeforeEach_UiCatalog_Instrumented","BeforeEach");

        try{
            client.applicationClearData(Main.UiCatalogPackageName);
        }catch (Exception e){
            Main.sout("Exception","applicationClearData for: "+Main.UiCatalogPackageName+ " failed | for device "+device.getSerialnumber()+Main.delimiter+"  "+e.getMessage());
        }

        //check if need to install
        client.applicationClose("com.android.chrome"); //close chrome before installing because it sometimes caus problems
        if (checkIfAppInstall(Main.UiCatalogPackageName, instrumentedProperties) == false) { //not installed
            client.install(Main.UiCatalogInstallOnComputerPath, instrumentedProperties, false);
        }
        //try to launch
        try {
            client.launch(Main.UiCatalogLaunchName, instrumentedProperties, true);
        } catch (Exception e) {
            Main.sout("Exception","device " + device.getSerialnumber() + " is failed to launch, will try again");
            client.install(Main.UiCatalogInstallOnComputerPath, instrumentedProperties, false);
            client.launch(Main.UiCatalogLaunchName, instrumentedProperties, true);
        }

        //AfterLaunch CheckForPopUp
        if(client.isElementFound("native","xpath=//*[@text='OK']")){
            Main.sout("Info","click on ok in UiCatalog instrumented class");
            client.click("native","xpath=//*[@text='OK']",0,1);
        }


        //check if need battery monitoring
        if (Main.BatteryMonitoring && Integer.valueOf(device.getVersion()) > 5) {
            System.out.println("# START battery monitoring");
            System.out.println("device " + device.getSerialnumber() + " battery monitoring is on , OS version is: " + device.getVersion());
            try {
                client.startMonitor(Main.UiCatalogPackageName + ":battery");
            } catch (Exception e) {
                Main.report.addRowToReport("Other", "UiCatalog_Instrumented_BaseTest " + " start Monitor failed: ", device.getSerialnumber(), "Fail", "", "", "", e.getMessage());
//                    stopRunIf(e);
                Main.BatteryMonitoring = false;
            }
        } else {

            Main.sout("Info","device " + device.getSerialnumber() + " battery monitoring is off , OS version is: " + device.getVersion() );
        }
    }


    @AfterEach
    public void afterEach_UiCatalog_Instrumented() {
        functionPrintInfo(getClass().toString(),"afterEach_UiCatalog_Instrumented","AfterEach");

        if (Main.BatteryMonitoring && Integer.valueOf(device.getVersion()) > 5) {
            System.out.println("# STOP battery monitoring");
            try {
                printBatteryStats();
                String str5 = client.getMonitorsData();
            } catch (Exception e) {

                Main.sout("Exception","device " + device.getSerialnumber() + " battery monitoring getMonitorsData failed");

            }
        }

        try {
            String str6 = client.getDeviceLog();
        } catch (Exception e) {

            Main.sout("Exception","device " + device.getSerialnumber() + "getDeviceLog failed");

        }
    }


    @Test
    @DisplayName("test")
    public void test(){
        System.out.println("Test for UiCatalog Instrumented BaseTest");
    }

    @AfterAll
    public void afterAll_UiCatalog_Instrumented() throws IOException {
        functionPrintInfo(getClass().toString(),"afterAll_UiCatalog_Instrumented","AfterAll");

    }

}
