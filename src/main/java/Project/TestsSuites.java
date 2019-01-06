package Project;

import Project.LongRun.LongRunTestWithoutReleaseOriginal;
import Project.Tests.Chrome;
import Project.LongRun.LongRunTest;
import Project.Tests.Other_Tests.Capture;
import Project.Tests.chrometest1;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public enum TestsSuites {
    EriBankInstrumented("Project.Tests.EriBank_Tests.Instrumented_Eribank_Tests"),
    WebTests("Project.Tests.WebTests"),
    AllTest("Project.Tests"),
    OneTimeTest(Project.Tests.Other_Tests.KLM_Passport.class),
    LongRunTest_NoRelease(LongRunTestWithoutReleaseOriginal.class),
    BaseTest_LongRun(Project.LongRun.BaseTest_LongRun.class),
    NothingTest(Project.Tests.NothingTest.class), /*Check the code environment without actual test*/


    //Examples
    Packgess("Package1", "Package1"),
    Packgess_Class(Arrays.asList("Package1","Package1"), Chrome.class, Chrome.class),
    ;


    List<DiscoverySelector> selectors;

    //For classes list only
    TestsSuites(Class... classes) {

        selectors = new ArrayList<DiscoverySelector>();
        for (Class c: classes) {
            selectors.add(selectClass(c));
        }
    }


    //For packages only
    TestsSuites(String... Packages) {
        selectors = new ArrayList<DiscoverySelector>();
        for (String  p: Packages) {
            selectors.add(DiscoverySelectors.selectPackage(p));
        }
    }

    //For packages And classes
    TestsSuites(List<String> Packages, Class... classes) {
        selectors = new ArrayList<DiscoverySelector>();
        for (String  p: Packages) {
            selectors.add(DiscoverySelectors.selectPackage(p));
        }
        for (Class c: classes) {
            selectors.add(selectClass(c));
        }
    }
}


