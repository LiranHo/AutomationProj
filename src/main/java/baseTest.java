
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
    protected static Client client = null;

    @BeforeAll
    public static void setup(){
        client = new Client(host, port, true);
        System.out.println("Client SessionID: "+client.getSessionID());
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "Untitled");
    }

    @BeforeEach
    public void before() {
        client.waitForDevice("@os='android'",10000);
        System.err.println("***********************************BaseTest Before Each");
    }


    @Test
    public void newTest(){

    }


    @AfterAll
    public static void after(){

    }


}
