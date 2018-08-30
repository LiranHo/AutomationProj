import com.experitest.client.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Chrome extends baseTest {


    @BeforeAll
    public void setupChrome(){
        client.launch("chrome:fvsdgvd",true,true);
    }

    @Test
    public void newTest3(){

    }

}
