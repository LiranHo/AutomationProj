import com.experitest.client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Chrome extends baseTest {


    @BeforeAll
    public static void setupChrome(){
        System.err.println("***********************************test chrome");
        client.launch("chrome:ebay.com",true,true);
    }

    @Test
    public void newTest(){
        System.err.println("***********************************test chrome");
        client.sleep(1000);

    }

}
