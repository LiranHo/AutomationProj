import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        //1: get devices list and create Hashmap
        try {
            initDevicesList();
        } catch (Exception e) {
            System.err.println("Failed to initDevicesList()  ~  Location: class main");
            e.printStackTrace();
        }
        //2: Create threads for each device
    }
    public static String delimiter = "\r\n";


    //init all var

//    private static Map<String,String> devices= new HashMap<>();
      private static List<Device> devices = new ArrayList<Device>();

    public static boolean Devices;
    public static boolean Grid=false;


    //**Local**
    protected static Client client = null;
    protected static String local_host = "localhost";
    protected static int local_port = 8889;

    //***Methods***

    public static void initDevicesList() throws Exception {
        if (!Grid) {
            client = new Client(Main.local_host, Main.local_port, true);
            devices = getDevices(client.getDevicesInformation()); //create devices from getDevicesInformation
            client.releaseClient();

        } else if (Grid){
//           gridClient = new GridClient(userName, Password, projectName, grid_domain, grid_port, isSecured);
//             devices = getDevices(gridClient.getDevicesInformation());

        }

        //print devices list
        System.out.println("Devices List info: "+delimiter);
        for (int i = 0; i < devices.size()-1; i++) {
            System.out.println("#"+(i+1)+" " +devices.get(i).toString()+delimiter);

        }
   }


    //get devices and enter them to hash map
    public static List<Device> getDevices(String xml) throws Exception {
//      Map<String, String> devicesMap = new HashMap<>();
       List<Device> devicesMap = new ArrayList<Device>();
        NodeList nodeList = parseDevices(xml); //parse the xml and get back nodeList with all the devices

        for (int i = 0; i <nodeList.getLength() ; i++) {
            Node nNode = nodeList.item(i);
            Element eElement = (Element) nNode;
            Device device=new Device(
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
            devicesMap.add(device);
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
}
