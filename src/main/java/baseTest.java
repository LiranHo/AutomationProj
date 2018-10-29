
import com.experitest.client.Client;
import com.sun.org.glassfish.gmbal.Description;
import org.boon.validation.annotations.StopOnRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


public class baseTest {
//    protected Device devices = Devices.A0235;  //Enum

    protected static String reportURL;
    protected Thread device;
    protected String sessionID;
    protected String testName;
    protected String testStartTime;
    protected String testEndTime;
    protected String path;
    public String FolderinnerDeviceDirPath;



    //STA settings
    protected static String host = "localhost";
    protected static int port = 8889;
    protected Client client = null;





    @BeforeAll
    public static void setup(){

        reportURL = "GetReportURL";
//        protected String sessionID;
//        protected String testName;
//        this.FolderinnerDeviceDirPath = Main.createNewDir(Main.innerDirectoryPath, serialNumber+";"+os);


    }

    @BeforeEach
    public void before() {
        client = new Client(host, port, true);
        device=Thread.currentThread();

        String serial=device.getName();
        client.waitForDevice("@serialnumber='"+serial+"'",10000);
        System.out.println("Client SessionID: "+client.getSessionID());
        System.err.println("***********************************BaseTest Before Each");
        client.setProjectBaseDirectory(Main.Repository_project);

        path = client.setReporter("xml",this.FolderinnerDeviceDirPath, getTestName()); //set Project.report, called at the beginning of each test and define the Project.report parameters


        client.setReporter("xml", "reports", "Untitled");
    }


    @Test
    public void newTest(){

    }


    @AfterAll
    public static void after(){

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

}
