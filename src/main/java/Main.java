import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        //T: ***Init The Test***
        //T: 1.Choose the platform to run (if Grid is true choose the cloud user)
        Grid= true;
        cloudUser = CloudUsers.LiranCloud;
        //T: 2. Choose on what devices to run:
        // Can add devices SN or Name (without ADB:)
        //TODO: make sure devices can't be added twice
        chooseSpesificDevices=false;
        /**/Choosedevices.add("668bbfe5");
        /**/Choosedevices.add("adb:HUAWEI BKL-L09");
        //TODO: run with all devices without choose them in the beginning
        //T: 3. Choose the run length
        //Run by time or choose number of Rounds
        //Or choose the length time you want
        Runby_NumberOfRounds =true;
        /**/NumberOfRoundsToRun=1;
        TimeToRun= 60 * 60 * 2; //Seconds * minutes * hours
        //T: 4. choose classes or packages to run with
        testsSuites = TestsSuites.LONGRUN;



        //T: ***Start To Prepare The Test***
        //T: TODO: 1:Create preparation for Report ;  Create directory, sample Start time, create test report

        //T: 2: get devices list and create Hashmap
        try {
            initDevicesList();
        } catch (Exception e) {
            System.err.println("Failed to initDevicesList()  ~  Location: class main");
            e.printStackTrace();
        }


        //T: TODO: 3: Print this-run properties
        //T: 4: Create threads for each device and start to Run
        ExecutorService executorService = Executors.newFixedThreadPool(Main.devices.size());
        for(Device device : devices) {
            Runner r = new Runner(device);
            executorService.submit(r);
        }

        System.err.println("End of Threads");
//        if(executorService.isShutdown())
//        executorService.awaitTermination(1, TimeUnit.DAYS);
    }


    //***Init Test Vars***
    //**Devices**
    protected static List<Device> devices = new ArrayList<>(); // the devices list which we run on
    protected static List<String> Choosedevices = new ArrayList<>(); // the devices SN the user want to run on
    protected static boolean chooseSpesificDevices; //Choose specific devices or run on all connected devices
    protected static boolean Runby_NumberOfRounds; //Choose nubmer of rounds or decided the time length you want to run
    protected static int NumberOfRoundsToRun; //Choose nubmer of rounds
    protected static int TimeToRun; //decided the time length you want to run: Hours * Min



    public static boolean Devices;
    public static boolean Grid=false;
    //Tests
    protected static TestsSuites testsSuites;


    //**Local**
    protected static Client client = null;
    protected static String local_host = "localhost";
    protected static int local_port = 8889;

    //**Grid**
    protected static CloudUsers cloudUser;

    //***Methods***

    public static void initDevicesList() throws Exception {
        /* create client and get the connected devices list using "getdevicesinformation
         * afterwards, parse the xml and create object-device to each device
         */
        if (!Grid) {
            client = new Client(Main.local_host, Main.local_port, true);
            devices = getDevices(client.getDevicesInformation()); //create devices from getDevicesInformation
            client.releaseClient();

        } else if (Grid){
            GridClient gridClient = new GridClient(cloudUser.userName, cloudUser.Password, cloudUser.projectName, cloudUser.grid_domain, cloudUser.grid_port, cloudUser.isSecured);
            devices = getDevices(gridClient.getDevicesInformation());
//            client.releaseClient();

//           gridClient = new GridClient(userName, Password, projectName, grid_domain, grid_port, isSecured);
//             devices = getDevices(gridClient.getDevicesInformation());

        }

        //print the devices list
        System.out.println("Devices List info: "+delimiter);
        System.out.println("Number of Devices in this run: "+devices.size());
        String PrintDevicesInfo="";
        String PrintDeviceSN="";
        for (int i = 0; i < devices.size(); i++) {
            PrintDevicesInfo+="#"+(i+1)+" " +devices.get(i).toString()+delimiter;
            PrintDeviceSN+=devices.get(i).getSerialnumber()+delimiter;
        }
        //TODO: add this tow lines to the main report
        System.out.println(PrintDeviceSN);
        System.out.println(PrintDevicesInfo);

   }


    //get devices and enter them to hash map
    public static List<Device> getDevices(String xml) throws Exception {
        //The user can chose if he want to run on specific devices or on all connected devices:
        //1. If the user want to run on all connected devices:
        //2. if the user wants to run on specific devices:
        List<Device> devicesMap = new ArrayList<>();
        NodeList nodeList = parseDevices(xml); //parse the xml and get back nodeList with all the devices

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nNode = nodeList.item(i);
                Element eElement = (Element) nNode;
                Device device = new Device(
                        eElement.getAttribute("serialnumber"),
                        eElement.getAttribute("name"),
                        eElement.getAttribute("os"),
                        eElement.getAttribute("version"),
                        eElement.getAttribute("model"),
                        eElement.getAttribute("category"),
                        eElement.getAttribute("manufacture"),
                        eElement.getAttribute("remote"),
                        eElement.getAttribute("reservedtoyou"));
//            System.out.println(eElement.getAttribute("serialnumber"));

                //if we don't want to use all devices, check if the device is in the list of device
                if(chooseSpesificDevices) {

                    for(String DeviceSN: Choosedevices){
                        if(eElement.getAttribute("serialnumber").equalsIgnoreCase(DeviceSN)
                                ||eElement.getAttribute("name").toLowerCase().contains(DeviceSN.toLowerCase())) {
                            devicesMap.add(device);
                        }
                    }
                }else { //if use all devices is true
                    devicesMap.add(device);
                }
            }


                return devicesMap;

    }

    //Parse the devices XML from getDevicesInformation and returns A nodeList Element
    private static NodeList parseDevices(String xml) throws Exception{
        //Using JDOM to parse the devices xml
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append(xml);
        ByteArrayInputStream input = new ByteArrayInputStream(
                xmlStringBuilder.toString().getBytes("UTF-8"));
        Document doc = builder.parse(input);
        doc.getDocumentElement().normalize();
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/devices/device";
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        System.out.println("Number of Devices connected: " + nodeList.getLength());

        return nodeList;

    }

    //Finals - aid variables
    public static final String delimiter = "\r\n";
}
