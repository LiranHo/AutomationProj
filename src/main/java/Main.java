import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.nio.ch.ThreadPool;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        //T: ***Init The Test***
        //T: 1.Choose the platform to run (if Grid is true choose the cloud user)
        Grid= false;
        cloudUser = CloudUsers.LiranQaCloud;
        //T: 2. Choose on what devices to run:
        // Can add devices SN or Name (without ADB:)
        //TODO: make sure devices can't be added twice
        //TOdO: add option to run on random X devices
        chooseSpesificDevices=false;
        /**/Choosedevices.add("668bbfe5");
//        /**/Choosedevices.add("2bc6d8279805");
//        /**/Choosedevices.add("668bbfe5");
//        /**/Choosedevices.add("adb:HUAWEI BKL-L09");
        //TODO: run with all devices without choose them in the beginning
        //T: 3. Choose the run length
        //Run by time or choose number of Rounds
        //Or choose the length time you want
        Runby_NumberOfRounds =true;
        /**/NumberOfRoundsToRun=1;
        TimeToRun= 60 * 60 * 2; //Seconds * minutes * hours
        //T: 4. choose classes or packages to run with
        testsSuites = TestsSuites.OneTest_forTest;


        //T: ***Start To Prepare The Test***
        //T: TODO: 1:Create preparation for Report ;  Create directory, sample Start time, create test report
        startTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        innerDirectoryPath = createNewDir(projectBaseDirectory, startTime); //create new directory for this test run
        report = Reporter.Reporter ("MainReport",innerDirectoryPath);
        report.addRowToReport("Type","Session ID","Test Name","Device SN","Status","Report URL");


        //T: 2: get devices list and create Hashmap
        String devicesInitInfo="";
        try {
            devicesInitInfo=initDevicesList();
        } catch (Exception e) {
            System.err.println("Failed to initDevicesList()  ~  Location: class main");
            e.printStackTrace();
        }


        //T: TODO: 3: Print this-run properties
        //Create Run Info File:
        RunInfoFile("devices Init Info",devicesInitInfo);
        //T: 4: Create threads for each device and start to Run
        ExecutorService executorService = Executors.newFixedThreadPool(Main.devices.size());
        ArrayList<Future> futures = new ArrayList<>();
        for(Device device : devices) {
            Runner r = new Runner(device);
            futures.add(executorService.submit(r));
        }
//            System.out.println(a);
//            try {
//                a.get();
//                System.out.println("a.isDone() "+ a.isDone());
//            } catch (Exception e) { e.printStackTrace(); }

//        while(true){
//            for(Future a : futures){
//                if(!a.isDone());
//            }
//        }
        System.err.println("End of Threads");
        int i = 0;
        for(Future f : futures) {
            try {
                f.get();
                System.out.println(futures.get(i) + " Future # "+i+" is ended | from "+futures.size()+" of futures");
                i++;
            } catch (ExecutionException e) {e.printStackTrace();}
        }

        executorService.shutdownNow();
//        System.exit(0);

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

    //**Report**
    public static String projectBaseDirectory = "E:\\Reports\\Main Project Test Report";
    public static String Repository_project="C:\\Users\\liran.hochman\\workspace\\project2";
    public static String innerDirectoryPath = "";
    public static String startTime;
    public static Reporter report;



    //***Methods***

    public static String initDevicesList() throws Exception {
        String devicesInitInfo="";
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
            PrintDevicesInfo+="#"+(i+1)+" " +devices.get(i).toString()+delimiter+delimiter;
            PrintDeviceSN+=devices.get(i).getSerialnumber()+delimiter;
        }

        System.out.println(PrintDeviceSN);
        System.out.println(PrintDevicesInfo);

        devicesInitInfo+="Devices List info: "+delimiter+"Number of Devices in this run: "+devices.size()+delimiter+PrintDeviceSN+delimiter+PrintDevicesInfo;
        return devicesInitInfo;
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
                            //Create new folder for this device
                            device.setDeviceFolderPath( Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
                            devicesMap.add(device);



                        }
                    }
                }else { //if use all devices is true
                    //Create new folder for this device
                    device.setDeviceFolderPath( Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
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

    //create new directory, get the directory path and the new folder name
    public static String createNewDir(String path, String folderName) {
        File newDir = new File(path + "\\" + folderName);
        String createdPath = path + "\\" + folderName;
        //create
        if (!newDir.exists()) {
            System.out.println("creating directory: " + newDir.getName());
            try {
                newDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return createdPath;
    }

    //Create Info File
    public static void RunInfoFile(String InfoFileName, String content){
        PrintWriter infoFile = Reporter.CreateReportFile(innerDirectoryPath,InfoFileName,"txt");
        infoFile.println(content);
        infoFile.flush();

    }


    public static Device searchDeviceBySN(String SN) {
        for (Device device : devices) {
            if (device.getSerialnumber().equals(SN)) {
                return device;
            }
        }
        return null;
    }



    //Finals - aid variables
    public static final String delimiter = "\r\n";
}
