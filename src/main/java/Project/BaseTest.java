package Project;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.experitest.client.MobileListener;
import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import sun.security.x509.CRLReasonCodeExtension;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;


@ExtendWith(RunnerExtension.class)
@DisplayName("Base Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
//    protected Project.Device devices = Devices.A0235;  //Enum

    protected String reportURL;
    protected Device device;
    protected String sessionID;
    protected String testName;
    protected long startTimePerDevice;
    protected String testStartTime;
    protected String testEndTime;
    protected long testStartTime_calculate;
    protected long testDuring;
    public String path;
    public static String FolderinnerDeviceDirPath;
    GridClient gridClient;
    //STA settings
    protected static String host = "localhost";
    protected static int port = 8889;
    protected Client client = null;
//    protected GridClient gridClient = null;


    @BeforeAll
    public void setup() {
        Main.sout("### MyInfo: ***","Class: 'BaseTest', | Method: 'setup' | which is: 'BeforeAll' *** ###");
        reportURL = "GetReportURL";
        startTimePerDevice = System.currentTimeMillis();
        String DeviceSN = Thread.currentThread().getName();
        device = Main.searchDeviceBySN(DeviceSN);
        testName = device.getSerialnumber();
        System.out.println(new Date() + "\t" + device.getSerialnumber() + "\tBaseTest BeforeAll - device " + Thread.currentThread().getName());
        FolderinnerDeviceDirPath = device.getDeviceFolderPath();
        gridClient = new GridClient(Main.cloudUser.userName, Main.cloudUser.Password, Main.cloudUser.projectName, Main.cloudUser.grid_domain, Main.cloudUser.grid_port, Main.cloudUser.isSecured);



    }


    @BeforeEach
    public void before() {
        Main.sout("### MyInfo: ***","Class: 'BaseTest', | Method: 'before' | which is: 'BeforeEach' *** ###");

        Main.sout("Info",new Date() + "\t" + device.getSerialnumber() + "\tBaseTest BeforeEach - device " + device.getSerialnumber());

        testStartTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        testStartTime_calculate = System.currentTimeMillis();

        createClient();

      //  client.setProjectBaseDirectory(Main.Repository_project);
        path = client.setReporter("xml", FolderinnerDeviceDirPath, getTestName()); //set Project.report, called at the beginning of each test and define the Project.report parameters

        try{
            handleMobileLisener();
        }catch (Exception e){
            String errorOutput= new Date()+ "\t"+"handleMobileLisener fail "+ "\t"+ e.getMessage();
            System.out.println(errorOutput);
            Main.ErrorFile.addRowToReport("Exception",errorOutput);
        }
    }


    //  @Test
    @DisplayName("BaseTest Test")
    public void Test() {


        System.err.println(new Date() + "\t" + device.getSerialnumber() + "\tBaseTest Test 1 ");

//        String displayName= TestInfo.getDisplayName();

    }

    //@Test
    @DisplayName("BaseTest Test2")
    public void newTest2() throws Exception {
        System.err.println(new Date() + "\t" + device.getSerialnumber() + "\tBaseTest Test 2 - this class throw exception on purpose");
        throw new Exception("Sta'm exception");

    }


    @AfterEach
    public void tearDown() {
        Main.sout("### MyInfo: *** ","Class: 'BaseTest', | Method: 'tearDown' | which is: 'AfterEach' *** ###");
        Main.sout("Info", device.getSerialnumber() + "\tBaseTest AfterEach (tear down)");
        try {
            if (client == null) {
                Main.sout("Warning","Tear Down - client is null do nothing "+device.getSerialnumber());
                return;
            }
            client.generateReport(false);
            //create collect support data every X time
            CollectSupportDataFromBeep(this.getClass().getName());
            client.releaseClient();
        }catch (Exception e){
            Main.sout("Exception","tearDown Failed \t"+e.getMessage());

            /** try it out **/
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            Main.sout("Exception","BaseTest TearDown printStackTrace: "+"\t"+exceptionAsString);
            /*****/

        }
        testEndTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());

    }

    protected void CollectSupportDataFromBeep(String theClassThatActivateMe) {
        System.out.println("** BeeperControl check in class "+theClassThatActivateMe+" \t Main.CollectSupportDataVar = "+Main.CollectSupportDataVar);
        if(Main.CollectSupportDataVar){
            Main.CollectSupportDataVar=false;
            String collectSupportDataPATH=Main.innerDirectoryPath + "\\SupportData" + new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date())+".zip";
            System.out.println("** Start to collectSupportData from beeperControl activation,"+Main.delimiter+"collectSupportData Path is: "+collectSupportDataPATH);
            client.collectSupportData(collectSupportDataPATH , "", "", "", "", "", true, false);
        }
    }

    @AfterAll
    public static void after() {
        System.out.println("### MyInfo: *** Class: 'BaseTest', | Method: 'after' | which is: 'AfterAll' *** ###");

//        System.err.println(new Date() + "\t" + device.getSerialnumber() + "\tBaseTest AfterAll - device " + device.getSerialnumber());
    }

    public String getTestName() {
        return this.testName;
    }

    public void handleMobileLisener(){
        handleMobileLisener("native", "xpath=//*[@nodeName='DIV' and @width>0 and ./*[@text='การตั้งค่าภาษา'] and ./*[@nodeName='IMG']]", "native", "xpath=//*[@id='infobar_close_button']");
        handleMobileLisener("native", "xpath=//*[@text='No Thanks']", "native", "xpath=//*[@text='No Thanks']");
        handleMobileLisener("native", "xpath=//*[@id='infobar_close_button']", "native", "xpath=//*[@id='infobar_close_button']");
        handleMobileLisener("native", "xpath=//*[@text='No Thanks']", "native", "xpath=//*[@text='No Thanks']");
        handleMobileLisener("native", "xpath=//*[@text='NO THANKS']", "native", "xpath=//*[@text='NO THANKS']");
        handleMobileLisener("native", "xpath=//*[@id='button_secondary']", "native", "xpath=//*[@id='button_secondary']");
        handleMobileLisener("native", "xpath=//*[@text='ALLOW']", "native", "xpath=//*[@text='ALLOW']");
        handleMobileLisener("web","xpath=//*[@text='I agree']","web","xpath=//*[@text='I agree']");
        handleMobileLisener("native","xpath=//*[@text='NO THANKS']","Native","xpath=//*[@text='NO THANKS']");
        handleMobileLisener("native","xpath=//*[@text='OK']","Native","xpath=//*[@text='OK']");

    }

    public void createClient() {
        System.out.println("### MyInfo: *** Class: 'BaseTest', | Method: 'createClient' | which is: 'function' *** ###");

        //Check if running on grid or local
        //Wait for device

        //Use STA
        if (!Main.Grid) {
            client = new Client(host, port, true);
            System.out.println("Waiting for device: "+device.getSerialnumber());
            client.waitForDevice("@serialnumber='" + device.getSerialnumber() + "'", 500000);
        }
        //Use Grid
        else {
//                gridClient = new GridClient(Main.cloudUser.userName, Main.cloudUser.Password, Main.cloudUser.projectName, Main.cloudUser.grid_domain, Main.cloudUser.grid_port, Main.cloudUser.isSecured);
            System.out.println("Trying to get - " + "@serialnumber='" + device.getSerialnumber());

            client = gridClient.lockDeviceForExecution(testName, "@serialnumber='" + device.getSerialnumber() + "'", 480, 50000);
            System.out.println("Got - @serialnumber='" + device.getSerialnumber());

        }


        //Add relevant info
        System.out.println("Client SessionID: " + client.getSessionID());
        sessionID = client.getSessionID();

    }

    public void handleMobileLisener(String Findtype, String findXpath , String Presstype , String PressXpath) {
        client.addMobileListener(Findtype, findXpath
                , new MobileListener() {
                    @Override
                    public boolean recover(String type, String xpath) {
                        client.click(Presstype, PressXpath, 0, 1);
                        return true;
                    }
                });
    }



    protected boolean checkIfAppInstall(String appPackage, boolean instrumented) {
        System.out.println("### MyInfo: *** Class: 'BaseTest', | Method: 'checkIfAppInstall' | which is: 'function' *** ###");

        String apps = client.run("adb shell pm list packages");
            if (apps.contains(appPackage)) {
                if (instrumented) {
                    if (apps.contains(appPackage + ".test")) {
                        int app_userId = 0;
                        int app_userId_test = -2;
                        try {
                            app_userId = Integer.valueOf(client.run("adb shell dumpsys package " + appPackage + " | grep userId").replaceAll("[^\\d.]", ""));
                            app_userId_test = Integer.valueOf(client.run("adb shell dumpsys package " + appPackage + ".test | grep userId").replaceAll("[^\\d.]", ""));
                        } catch (Exception e) {
                            app_userId = -10;
                            app_userId_test = -20;
                        }

                        if ((app_userId + 1 == app_userId_test) || (app_userId - 1 == app_userId_test)) {
                            return true;
                        } else
                            return false;
                    } else
                        return false;
                } else
                    return true;
            } else
                return false;
        }


    //get battery stats file into file in the test folder
    public void printBatteryStats() throws IOException {
        String myPath = path + "\\battery.txt";
//        Process p = Runtime.getRuntime().exec("adb shell dumpsys batterystats > " + myPath);



        String devices = "";

        String [] command = {"adb","-s",device.getSerialnumber(), "shell","dumpsys","batterystats"};
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream();
        Process process = null;


        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(myPath, true)));
        try {
            process = builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                try{
                    writer.write(line + "\n");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            reader.close();
            is.close();
//                Thread.sleep(10000);
        }catch(Exception e) {
            process.destroy();
        }
        writer.close();

        System.out.println(devices);

    }


    public void functionPrintInfo(String my_Class, String my_method, String my_type){
        System.out.println("### MyInfo: *** Class: '"+my_Class+"', | Method: '"+my_method+"' | which is: '"+my_type+"' *** ###");

    }

   }





/*
T:https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-our-first-test-class/

* The method that is annotated with the @BeforeAll annotation must be static, and it is run once before any test method is run.
The method that is annotated with the @BeforeEach is invoked before each test method.
The method that is annotated with the @AfterEach annotation is invoked after each test method.
The method that is annotated with the @AfterAll annotation must be static, and it is run once after all test methods have been run.
* */