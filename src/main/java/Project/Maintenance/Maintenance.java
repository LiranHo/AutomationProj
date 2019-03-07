package Project.Maintenance;

import Project.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Maintenance extends BaseTest {

    @Test
    @DisplayName("cleanDeviceLog")
    public void cleanDeviceLog(){
        client.run("adb logcat -c");
    }

}
