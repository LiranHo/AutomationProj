package Project.LongRun;
import Project.BaseTest;
import Project.Device;
import Project.Main;
import Project.RunnerExtension;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.sun.org.glassfish.gmbal.Description;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.launcher.TestPlan;

import javax.xml.transform.Result;
import java.text.SimpleDateFormat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RunnerExtension_LongRun.class)
@DisplayName("Base Test _ Long Run")
public class BaseTest_LongRun {
//    protected Project.Device devices = Devices.A0235;  //Enum

    protected static String reportURL;
    protected static Device device;
    protected String sessionID;
    protected static String testName;
    protected String testStartTime;
    protected String testEndTime;
    protected long testStartTime_calculate;
    protected long testDuring;
    public String path;
    public static String FolderinnerDeviceDirPath;



    //STA settings
    protected static String host = "localhost";
    protected static int port = 8889;
    protected Client client = null;
    protected GridClient gridClient = null;


    @BeforeAll
    public void setup() {
        reportURL = "GetReportURL";
        testName="Base Test _ Long Run";

        String DeviceSN  = Thread.currentThread().getName();
        device= Main.searchDeviceBySN(DeviceSN);
        FolderinnerDeviceDirPath=device.getDeviceFolderPath();


        System.err.println("1. Before All method - setup longRun test");

        testStartTime=new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        testStartTime_calculate=System.currentTimeMillis();

        //Check if running on grid or local
        //Wait for device
        if(!Main.Grid) { //Use STA
            client = new Client(host, port, true);
            client.waitForDevice("@serialnumber='" + device.getSerialnumber() + "'", 500000);
        }
        else { //use Grid

            try {
                gridClient = new GridClient(Main.cloudUser.userName, Main.cloudUser.Password, Main.cloudUser.projectName, Main.cloudUser.grid_domain, Main.cloudUser.grid_port, Main.cloudUser.isSecured);
                client = gridClient.lockDeviceForExecution(testName, "@serialnumber='" + device.getSerialnumber() + "'", 480, 300000); //5 minutes
            }catch (Exception e){
                throw e;
            }
            //managerPublisher = gridClient.createPublisher(testName, client);
        }

        System.out.println("Client SessionID: " + client.getSessionID());
        sessionID = client.getSessionID();

       // client.setProjectBaseDirectory(Main.Repository_project);

    }

    @BeforeEach
    public void before(){
        path = client.setReporter("xml",FolderinnerDeviceDirPath, getTestName()); //set Project.report, called at the beginning of each test and define the Project.report parameters

    }


    @Test
    @DisplayName("BaseTest Test")
    public void Test(){
        System.err.println("BaseTest Test 1 ");

//        String displayName= TestInfo.getDisplayName();

    }

    //@Test
    @DisplayName("BaseTest Test2")
    public void newTest2() throws Exception {
        System.err.println("BaseTest Test 2 - this class throw exception on purpose");
        throw new Exception("Sta'm exception");

    }


    @AfterEach
    public void generateReport(){
    //    String result = testExecutionResult.getStatus().toString();
        client.generateReport(false);
        //testInfo.getTestMethod().toString();
       // Main.report.addRowToReport("BaseTest_LongRun_ParcialReport",testName, device.getSerialnumber(),"?", "",sessionID,path,"");
    }


    @AfterAll
    public void tearDown(){
        System.err.println("1. After each method - tearDown");
        if(client == null) {
            System.out.println("*Tear Down - client is null will exit");
            return;
        }
        client.releaseClient();
        testEndTime=new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());

    }

    protected void failed(Throwable e, Description description){
        //if the device is null
        if (device.equals(null)){

        }
        if(false);


    }

    public String getTestName(){
        return this.testName;
    }
    public String getPath(){
        return this.path;
    }




}







/*
T:https://www.petrikainulainen.net/programming/testing/junit-5-tutorial-writing-our-first-test-class/

* The method that is annotated with the @BeforeAll annotation must be static, and it is run once before any test method is run.
The method that is annotated with the @BeforeEach is invoked before each test method.
The method that is annotated with the @AfterEach annotation is invoked after each test method.
The method that is annotated with the @AfterAll annotation must be static, and it is run once after all test methods have been run.
* */