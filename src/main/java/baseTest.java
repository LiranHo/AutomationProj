
import com.experitest.client.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class baseTest {
//    protected Device devices = Devices.A0235;  //Enum
    protected static String host = "localhost";
    protected static int port = 8889;
    protected static String projectBaseDirectory = "C:\\Users\\liran.hochman\\workspace\\project2";
    protected Client client = null;

    @BeforeAll
    public static void setup(){

    }

    @BeforeEach
    public void before() {
        client = new Client(host, port, true);
        String serial=Thread.currentThread().getName();
        client.waitForDevice("@serialnumber='"+serial+"'",10000);
        System.out.println("Client SessionID: "+client.getSessionID());
        System.err.println("***********************************BaseTest Before Each");
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "Untitled");
    }


    @Test
    public void newTest(){

    }


    @AfterAll
    public static void after(){

    }


}
