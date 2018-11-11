
import com.experitest.client.Client;
import com.sun.org.glassfish.gmbal.Description;
import io.github.glytching.junit.extension.watcher.WatcherExtension;
import org.boon.validation.annotations.StopOnRule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;


@ExtendWith(RunnerExtension.class)
@DisplayName("Base Test")
public class BaseTest {
//    protected Device devices = Devices.A0235;  //Enum

    protected static String reportURL;
    protected static Device device;
    protected String sessionID;
    protected static String testName;
    protected String testStartTime;
    protected String testEndTime;
    public String path;
    public static String FolderinnerDeviceDirPath;



    //STA settings
    protected static String host = "localhost";
    protected static int port = 8889;
    protected Client client = null;





//    @ExtendWith(WatcherExtension.class)
//    public class StoryExtension{
//
//
//    };
//
//    /*************************************************/
//    @Rule
//    public TestRule testWatcher = new TestWatcher() {
//        @Override
//        public Statement apply(Statement base, Description description) {
//            return super.apply(base, description);
//        }
//
//        @Override
//        protected void succeeded(Description description) {
//            current_thread.device.addSuccess(current_thread.getRun_number());
//            current_thread.updateReport();
//            log = adjustDateTime() + "\ttest "+ getTestName() + " on " + device_name + ": passed\r\n" ;
//            Writer.getInstance().writeToFile(log, Writer.file_type.LOG,true);
//            client.releaseClient();
//        }
//
//        @Override
//        protected void failed(Throwable e, Description description) {
//            current_thread.device.addFail(current_thread.getRun_number(), "    * "+description.toString() + ": " + e.toString() + "\r\n");
//            current_thread.updateReport();
//            log = adjustDateTime() + "\ttest "+ getTestName() + " on " + device_name + ": failed\r\n" ;
//            Writer.getInstance().writeToFile(log, Writer.file_type.LOG,true);
//            client.collectSupportData(report_path + File.separator + "SupportData.zip","",device_name,"","",description.toString());
//            client.releaseClient();
//        }
//    };


    /*************************************************/


    @BeforeAll
    public static void setup(){
        System.err.println("1. Before all method - setup");

        reportURL = "GetReportURL";
        testName="Base Test";
//        protected String sessionID;
//        protected String testName;
//        this.FolderinnerDeviceDirPath = Main.createNewDir(Main.innerDirectoryPath, serialNumber+";"+os);

        String DeviceSN  = Thread.currentThread().getName();
        device=Main.searchDeviceBySN(DeviceSN);
        FolderinnerDeviceDirPath=device.getDeviceFolderPath();
    }


    @BeforeEach
    public void before() {
        System.err.println("1. Before each method - before");

        //Create client
        client = new Client(host, port, true);

        //Get the current thread info and the current device info
//        String DeviceSN  = Thread.currentThread().getName();
//        device=Main.searchDeviceBySN(DeviceSN);
//        FolderinnerDeviceDirPath=device.getDeviceFolderPath();


        client.setProjectBaseDirectory(Main.Repository_project);
        path = client.setReporter("xml",FolderinnerDeviceDirPath, getTestName()); //set Project.report, called at the beginning of each test and define the Project.report parameters

        //Wait for device
        client.waitForDevice("@serialnumber='"+device.getSerialnumber()+"'",10000);
        System.out.println("Client SessionID: "+client.getSessionID());


    }


    @Test
    @DisplayName("BaseTest Test")
    public void newTest(){
        System.err.println("BaseTest Test 1 ");

//        String displayName= TestInfo.getDisplayName();

    }

    @Test
    @DisplayName("BaseTest Test2")
    public void newTest2() throws Exception {
        System.err.println("BaseTest Test 2 - this class throw exception on purpose");
        throw new Exception("Sta'm exception");


    }


    @AfterEach
    public void tearDown(){
        System.err.println("1. After each method - tearDown");
        if(client == null) {
            System.out.println("*Tear Down - client is null will exit");
            return;
        }
        client.generateReport(false);
        client.releaseClient();

    }

    @AfterAll
    public static void after(){
        System.err.println("1. After all method - after");

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