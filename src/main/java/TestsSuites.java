import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.discovery.DiscoverySelectors;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public enum TestsSuites {
    OneTest_forTest(Chrome.class),
//    OneTest_forTest(baseTest.class, Chrome.class),
    LONGRUN(Chrome.class, chrometest1.class),
    SHORTRUN(Chrome.class, chrometest1.class),
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


