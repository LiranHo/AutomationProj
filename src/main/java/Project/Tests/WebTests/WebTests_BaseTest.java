package Project.Tests.WebTests;

import Project.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class WebTests_BaseTest extends BaseTest {


//    @BeforeAll
//    public void init_WebTests_BaseTest() {
//        functionPrintInfo(getClass().toString(),"init_WebTests_BaseTest","BeforeAll");
//    }
    @BeforeEach
    public void start_WebTests_BaseTest() {
        functionPrintInfo(getClass().toString(),"start_WebTests_BaseTest","BeforeEach");
//        handleMobileLisener("web","xpath=//*[@text='I agree']","web","xpath=//*[@text='I agree']");
//        handleMobileLisener("native","xpath=//*[@text='NO THANKS']","antive","xpath=//*[@text='NO THANKS']");
        client.setNetworkConnection("wifi", true);
    }


    @AfterEach
    public void afterEach_WebTests_BaseTest() {
        functionPrintInfo(getClass().toString(), "afterEach_WebTests_BaseTest", "AfterEach");
        client.applicationClose("com.android.chrome");

    }


}
