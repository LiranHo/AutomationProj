
import com.experitest.client.Client;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class baseTest {
//    protected Device devices = Devices.A0235;  //Enum
    protected String host = "localhost";
    protected int port = 8889;
    protected String projectBaseDirectory = "C:\\Users\\liran.hochman\\workspace\\project2";
    protected Client client = null;

    @BeforeAll
    public void setup(){
        client = new Client(host, port, true);
        client.setProjectBaseDirectory(projectBaseDirectory);
        client.setReporter("xml", "reports", "Untitled");
    }

    @BeforeEach
    public void before(){
        client.setDevice("");
    }


    @Test
    public void newTest(){

    }


    @AfterAll
    public void after(){

    }


}
