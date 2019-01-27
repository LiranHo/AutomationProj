package Project.Tests;

import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NothingTest extends BaseTest {


    @DisplayName("NothingTest - check the code withoutTest")
    @Test
    public void NothingTest(){
        Main.sout("Info!","Just Do the Nothing Test (check the code withoutTest)");
    }
}
