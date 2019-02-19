package Project;

import Project.Properties.CloudUsers;
import Project.Reports.Files;
import Project.Reports.Reporter;
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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main {
    final protected static String NEWDEVICE_ANDROID = "899X07061";

    public static void initTheMain() {

        EnterInput = true;
        //TODO: Create GUI to enter properties
        //TODO: create a template for println with: deviceSN, time, and that is also written to file.

        //T: ***Init The Test***
        //T: 1. Choose the platform to run (if Grid is true choose the cloud user)
        Grid = true;
        cloudUser = CloudUsers.LiranCloud;
//        cloudUser = CloudUsers.LiranWindowsCloud;
        //T: 2. Choose on what devices to run: (Can add devices SN or Name (without ADB:))
        //TODO: make sure devices can't be added twice
        //TODO: fix that adb: or ios: also work
        //TODO: add option to run on random X devices
        chooseSpesificDevices = false; //**/Choosedevices.add("cvh7n15b04005855");/*Choosedevices.add("ce051715b20f972a02"); Choosedevices.add("668bbfe5");Choosedevices.add("adb:HUAWEI BKL-L09")*/
        Choosedevices.add("8d141db5");
        Choosedevices.add("ce051715b20f972a02");
        Choosedevices.add("899X07061");


        //T: 3. Choose the run length (Run by time or choose number of Rounds - Or choose the length time you want)
        Runby_NumberOfRounds = false; /**/
        NumberOfRoundsToRun = 1;
        TimeToRun = 60 * 60 * 7; //Seconds * minutes * hours
        //T: 4. choose classes or packages to run with
        testsSuites = TestsSuites.AllTest;
//        testsSuites = TestsSuites.NothingTest;
//        testsSuites = TestsSuites.OnecTimeTest;
//        testsSuites = TestsSuites.LongRunTest_NoRelease;


        //T: 5. choose some properties
        BatteryMonitoring = false;

        //T: ***Start To Prepare The Test***
        startTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        innerDirectoryPath = createNewDir(projectBaseDirectory, startTime); //create new directory for this test run
        //T: Init reports
        // INIT report and add first raw titles
        report = Reporter.Reporter("MainReport", innerDirectoryPath);
        report.addRowToReport("Type", "Test Name", "Device SN", "Status", "Test During", "Session ID", "Report URL", "Exception");
        // INIT info file
        infoFile = new Files("Init Info", innerDirectoryPath);
        ErrorFile = new Files("Error File", innerDirectoryPath);

    }
    public static void main(String[] args) throws Exception {
        initTheMain();

        //NOTE: EnterInput
        if (EnterInput)
            getInputFromUser();

        System.err.println("###STARTING...###");

        //T: get devices list and create Hashmap
        String devicesInitInfo = "";
        try {
            devicesInitInfo = initDevicesList();
            //Add devices info to info file
            infoFile.addRowToReport(false, devicesInitInfo);
        } catch (Exception e) {
            System.err.println("Failed to initDevicesList");
            infoFile.addRowToReport(true, "*** Failed to initDevicesList *** " + delimiter + e.getMessage(), true);
            ErrorFile.addRowToReport(true, "*** Failed to initDevicesList *** " + delimiter + e.getMessage(), true);
            report.addRowToReport("FAILURE", "initDevicesList", "", "Fail", "0", "", "", e.getMessage());

            e.printStackTrace();
        }


        //T: TODO: 3: Print this-run properties
        //Create Run Info File:
        //add devices info
        infoFile.addRowToReport(false, cloudUser.toString());

        //T: 4: Create threads for each device and start to Run
        if (Main.devices.size() <= 0)
            throw new Exception("Devices list is 0");
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ArrayList<Future> futures = new ArrayList<>();

        for (Device device : devices) {
            System.out.println("starting device - " + device.getSerialnumber());
            Runner r = new Runner(device);
            System.out.println("Runner is up for device - " + device.getSerialnumber());
//            futures.add(executorService.submit(r));
            executorService.execute(r);
        }

        //T: Create CollectSupportData Thread
        //Run collect support data only if the test is long enough
         collectSupportData();


        System.err.println("Started All Threads");
//        int i = 0;
//        //Future is every thread that run, we wait for all the threads to ends their work with the command "future.get" and then terminate the program
//        for (Future f : futures) {
//            try {
//                f.get();
//                System.out.println(f + " Future # " + (i + 1) + " is ended | from " + futures.size() + " of futures");
//                i++;
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        executorService.shutdown();
        if(executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            System.out.println("Finished all threads");
        } else {
            System.out.println("Timeout reached before all threads finished");
        }

//        executorService.shutdownNow();

//        Main.sout("Info!","executorService.shutdownNow()");
//        System.err.println("executorService.shutdownNow()");

        if (collectSupportDataThread != null) {
            collectSupportDataThread.terminate();
//        collectSupportDataThread.interrupt();

            Main.sout("Info!","collectSupportDataThread.terminate()");
        }

        //stop the collectSupportData- beep
        if(beep!=null) {
            beep.Terminate();
//            while(!beep.beeperHandle.isCancelled()) {
//                Main.sout("Info!", "beep.Terminate() and is isCancelled()= " + beep.beeperHandle.isCancelled());
//                beep.beeperHandle.cancel(true);
//            }
            Main.sout("Info!", "beep.Terminate()" );
        }

        //Add tests info to the info file
        String countTestsInfo = "=========================" + delimiter +
                "Count Test Info" + delimiter +
                "countTests: " + countTests + delimiter +
                "countTests_fail: " + countTests_fail + delimiter +
                "countTests_pass: " + countTests_pass + delimiter;
        infoFile.addRowToReport(true, countTestsInfo);
//        System.exit(0);
    }

    private static void getInputFromUser() {
        //TODO: this function

        Scanner scan = new Scanner(System.in);

        printCurrentTunProperties();
        printRunOptions();

        System.err.println("If You Want To Change Press On The Relevant Number");

        String input = scan.nextLine();

        //T: Change the properties for this run
        while(!input.equals("")) { //If entering just "Enter" the program will start
            switch (input) {
                case "1": //Grid
                    System.out.println("Enter Value: true/false");
                    String getInput = scan.nextLine();
                    Grid=Boolean.valueOf(getInput);

                    System.out.println("Grid value is changed to: "+Grid);
                    break;
                case "2": //Cloud User
                    System.out.println("Enter Value: LiranCloud / LiranQaCloud");
                    getInput = scan.nextLine();
                    cloudUser=CloudUsers.valueOf(getInput);
                    System.out.println("cloudUser value is changed to: "+cloudUser.toString(true));
                    break;
                case "3": //chooseSpesificDevices
                    System.out.println("Enter Value: true/false");
                    getInput = scan.nextLine();
                    chooseSpesificDevices=Boolean.valueOf(getInput);
                    System.out.println("chooseSpesificDevices value is changed to: "+chooseSpesificDevices);
                    String Device="";
                    if(chooseSpesificDevices) {
                        while (!Device.equals("-1")) {
                            System.out.println("Enter Devices Serial Number, To quit enter -1");
                            Device = scan.nextLine();
                            if(!Device.equals("-1")) {
                                Choosedevices.add(Device);
                                System.out.println("Device " + Device + " Enter To the Running devices");
                            }else System.out.println("Devices Add Is Ended");
                        }
                    }
                    break;
                case "4": //Runby_NumberOfRounds
                    System.out.println("Enter Value: true/false");
                    getInput = scan.nextLine();
                    Runby_NumberOfRounds=Boolean.valueOf(getInput);
                    System.out.println("Runby_NumberOfRounds value is changed to: "+Runby_NumberOfRounds);
                    break;

                case "5": //NumberOfRoundsToRun OR TimeToRun
                    if(Runby_NumberOfRounds){
                        System.out.println("Enter Value for NumberOfRoundsToRun: Integer");

                        getInput = scan.nextLine();
                        NumberOfRoundsToRun=Integer.valueOf(getInput);
                        System.out.println("NumberOfRoundsToRun value is changed to: "+NumberOfRoundsToRun);
                    }else{
                        System.out.println("Enter Value for TimeToRun: time in milliseconds");
                        getInput = scan.nextLine();
                        TimeToRun=Integer.valueOf(getInput);
                        System.out.println("TimeToRun value is changed to: "+TimeToRun);
                    }

                    break;
                case "6": //testsSuites
                    System.out.println("Enter Value: AllTest / OneTimeTest");
                    getInput = scan.nextLine();
                    testsSuites=TestsSuites.valueOf(getInput);
                    System.out.println("testsSuites value is changed to: "+testsSuites.toString());
                    break;

                case "0": //testsSuites
                    printCurrentTunProperties();
                    break;

                case "-10": //-10. AllTest, All Devices, Grid, 10 Hours
                    Grid=true;
                    chooseSpesificDevices=false;
                    Runby_NumberOfRounds=false;
                    TimeToRun=60 * 60 * 10;
                    testsSuites=TestsSuites.valueOf("AllTest");
                    break;

                case "-9": //-9. OneTimeTest, All Device, Grid, 1 Time
                    Grid=true;
                    chooseSpesificDevices=false;
                    Runby_NumberOfRounds=true;
                    NumberOfRoundsToRun=1;
                    testsSuites=TestsSuites.valueOf("OneTimeTest");
                    break;
            }
            System.err.println("If You Want To Change Press On The Relevant Number");
            input = scan.nextLine();
        }

        printCurrentTunProperties();

    }

    public static void printCurrentTunProperties(){
        System.err.println("********************");
        System.out.println("********************");
        System.out.println("**Run Properties**");
        System.out.println("0. Print the Setting");
        System.out.println("1. Grid: "+Grid);
        System.out.println("2. Cloud User: "+cloudUser.toString(true));
        System.out.println("3. chooseSpesificDevices: "+chooseSpesificDevices);
        if(chooseSpesificDevices)

        {
            for (String DeviceSN : Choosedevices) {
                System.out.println("    " + DeviceSN);
            }

        }
        System.out.println("4. Runby_NumberOfRounds: "+Runby_NumberOfRounds);
        if(Runby_NumberOfRounds)

        {
            System.out.println("5. NumberOfRoundsToRun: " + NumberOfRoundsToRun);
        }
        else

        {
            System.out.println("5. TimeToRun: " + TimeToRun);
        }
        System.out.println("6. testsSuites: "+testsSuites.toString());
        System.out.println("********************");
        System.out.println("********************");
    }

    public static void printRunOptions(){
        System.out.println("-10. AllTest, All Devices, Grid, 10 Hours");
        System.out.println("-9. OneTimeTest, All Device, Grid, 1 Time");

    }



        //***Init Test Vars***
    //**Devices**
    protected static List<Device> devices = new ArrayList<>(); // the devices list which we run on
    protected static List<String> Choosedevices = new ArrayList<>(); // the devices SN the user want to run on
    protected static boolean chooseSpesificDevices; //Choose specific devices or run on all connected devices
    public static boolean Runby_NumberOfRounds; //Choose nubmer of rounds or decided the time length you want to run
    public static int NumberOfRoundsToRun; //Choose nubmer of rounds
    public static int TimeToRun; //decided the time length you want to run: Hours * Min


    public static boolean Devices;
    public static boolean Grid = false;
    public static Boolean EnterInput;
    //Tests
    protected static TestsSuites testsSuites;



    //**Local**
    protected static Client client = null;
    protected static String local_host = "localhost";
    protected static int local_port = 8889;

    //**Grid**
    public static CloudUsers cloudUser;

    //**Report**
    public static String projectBaseDirectory = "E:\\Reports\\Main Project Test Report";
    public static String Repository_project = "C:\\Users\\liran.hochman\\workspace\\project2";
    public static String innerDirectoryPath = "";
    public static String startTime;
    public static Reporter report;
    public static Files infoFile;
    public static Files ErrorFile;
    public static int countTests = 0;
    public static int countTests_fail = 0;
    public static int countTests_pass = 0;
    private static CollectSupportDataThread collectSupportDataThread;
    private static  BeeperControl beep;
    final public static int CollectEveryX_inMin=20; //Optimal is 30 MIN
    public static AtomicBoolean CollectSupportDataVar = new AtomicBoolean(false);
    public static String PrintDevicesInfo;
    public static String PrintDeviceSN;


    //**Applications install paths**
    public static String EriBankInstallOnComputerPath = "E:\\Files - Liran - 2\\Applications_apk\\EriBank\\eribank.apk";
    public static String EriBankLaunchName = "com.experitest.ExperiBank/.LoginActivity";
    public static String EriBankPackageName = "com.experitest.ExperiBank";

    public static String UiCatalogInstallOnComputerPath = "E:\\Files - Liran - 2\\Applications_apk\\UiCatalog\\UICatalog.apk";
    public static String UiCatalogLaunchName = "com.experitest.uicatalog/.MainActivity";
    public static String UiCatalogPackageName = "com.experitest.uicatalog";

    public static String EriBankLaunchName_old = "com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
    public static String EriBankPackageName_old = "com.experitest.eribank";

    //**Run Other Properties**
    public static Boolean BatteryMonitoring;


    //***Methods***

    public static String initDevicesList() throws Exception {
        String devicesInitInfo = "";
        /* create client and get the connected devices list using "getdevicesinformation
         * afterwards, parse the xml and create object-device to each device
         */
        if (!Grid) {
            client = new Client(Main.local_host, Main.local_port, true);
            devices = getDevices(client.getDevicesInformation()); //create devices from getDevicesInformation
            client.releaseClient();

        } else if (Grid) {
            GridClient gridClient = new GridClient(cloudUser.userName, cloudUser.Password, cloudUser.projectName, cloudUser.grid_domain, cloudUser.grid_port, cloudUser.isSecured);
            devices = getDevices(gridClient.getDevicesInformation());


//           gridClient = new GridClient(userName, Password, projectName, grid_domain, grid_port, isSecured);
//             devices = getDevices(gridClient.getDevicesInformation());

        }

        //print the devices list
        System.out.println("Devices List info: " + delimiter);
        System.out.println("Number of Devices in this run: " + devices.size());
        PrintDevicesInfo = "";
        PrintDeviceSN = "";
        for (int i = 0; i < devices.size(); i++) {
            PrintDevicesInfo += "#" + (i + 1) + " " + devices.get(i).toString() + delimiter + delimiter;
            PrintDeviceSN += devices.get(i).getSerialnumber() + delimiter;
        }

        System.out.println(PrintDeviceSN);
        System.out.println(PrintDevicesInfo);

        devicesInitInfo += "Devices List info: " + delimiter + "Number of Devices in this run: " + devices.size() + delimiter + PrintDeviceSN + delimiter + PrintDevicesInfo;
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
            if (chooseSpesificDevices) {

                for (String DeviceSN : Choosedevices) {
                    if (eElement.getAttribute("serialnumber").equalsIgnoreCase(DeviceSN)
                            || eElement.getAttribute("name").toLowerCase().contains(DeviceSN.toLowerCase())) {
                        //Create new folder for this device
                        device.setDeviceFolderPath(Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
                        devicesMap.add(device);


                    }
                }
            } else { //if use all devices is true
                //Create new folder for this device
                device.setDeviceFolderPath(Main.createNewDir(Main.innerDirectoryPath, eElement.getAttribute("serialnumber")));
                devicesMap.add(device);

            }
        }


        return devicesMap;

    }

    //Parse the devices XML from getDevicesInformation and returns A nodeList Element
    private static NodeList parseDevices(String xml) throws Exception {
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


    public static Device searchDeviceBySN(String SN) {
        for (Device device : devices) {
            if (device.getSerialnumber().equals(SN)) {
                return device;
            }
        }
        return null;
    }

    //run collect support data only if the test is long enough
    private static void collectSupportData() { //with beep
        if ((Runby_NumberOfRounds && NumberOfRoundsToRun >= 30) || (!Runby_NumberOfRounds && TimeToRun >= (60 * 60 * 2))) {

//            CollectSupportDataVar=true;
            CollectSupportDataVar.set(true);
            //Check beeper control
            beep = new BeeperControl();
            beep.WakeEveryHour();

         // ############
            //disable collect supprt data for now
            System.err.println("(!) collectSupportDataThread is disabled");
            //collectSupportDataThread = new CollectSupportDataThread();
            //collectSupportDataThread.start();
        }

    }

    public static void sout(String type, String output)throws NullPointerException{ //print and add to file "error file"
        //if using ! in the type - it will be printes in red in the console
        if(type.toLowerCase().contains("!")){
            System.err.println(Main.ErrorFile.addRowToReport(type,output));
        }else
        System.out.println(Main.ErrorFile.addRowToReport(type,output));
    }

    //Finals - aid variables
    public static final String delimiter = "\r\n";


}
