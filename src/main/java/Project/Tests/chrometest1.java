package Project.Tests;

import Project.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class chrometest1 extends BaseTest{


  //  @Test
    @DisplayName("chrometest1 Test1")
    public void newTest(){
        System.err.println("***********************************test chrome1");
        System.out.println("Thread Name\t" + Thread.currentThread().getName());
        client.sleep(10000);
        client.swipe("down",0,10000);
//        Main.report.addRowToReport("i am chrome test 1","","","","","","");
    }
}
