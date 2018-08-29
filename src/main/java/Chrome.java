import com.experitest.client.Client;
import org.junit.BeforeClass;
import org.junit.Test;

public class Chrome extends baseTest {


    @BeforeClass
    public void setupChrome(){
        client.launch("chrome:fvsdgvd",true,true);
    }

    @Test
    public void newTest3(){

    }

}
