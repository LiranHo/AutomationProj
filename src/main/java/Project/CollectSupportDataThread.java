package Project;

import Project.Properties.CollectSupportDatat_apiV2;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;

import static Project.Properties.CollectSupportDatat_apiV2.CreateCollectSupportData_api;

public class CollectSupportDataThread extends Thread {


    private int numOfDevices;
    private boolean running;
    private Client client;
    private GridClient gridClient;
    private int CountFails;


    public void setNumDevices(int numOfDevices) {
        this.numOfDevices = numOfDevices;
    }

    public CollectSupportDataThread() {
        CountFails=0;
        this.running=true;
        //this(Main.cloudUser.grid_domain + "/api/v2/configuration/collect-support-data/false/false/", Main.userName, Main.Password);
    }

    public void terminate() {
        running = false;
    }

    public void doCollect()throws Exception{
        String startTime = new SimpleDateFormat("dd.MM.yyyy - HH.mm.ss").format(new java.util.Date());
        //First sleep, then check if the tests still running
//        this.sleep(60000 * 10); //sleep for 10 min

        if(running) {
            try {
                CreateClient();
                client.collectSupportData(Main.innerDirectoryPath + "\\SupportData" + startTime+".zip", "", "", "", "", "", true, false);
            } catch (Exception e) {
                String errorOutput = "CollectSupportData Thread - doCollect fucntion failed #"+CountFails;
                System.out.println(errorOutput);
                Main.ErrorFile.addRowToReport("Exception", errorOutput+Main.delimiter+e.getMessage());

                CountFails++;
                throw new Exception("collect support data failed");
            }
            Thread.sleep(60000 * 60 * 2); //sleep for 2 hours
            System.out.println("collect support data thread - sleep for 60*3 min");
        }

    }

    public void run() {
        while(running!=false) {
            System.err.println("collectSupportDataThread");
            try {
                doCollect();
            } catch (Exception e) {
                String errorOutput = "CollectSupportData Thread - run fucntion failed #"+CountFails;
                System.out.println(errorOutput);
                Main.ErrorFile.addRowToReport("Exception", errorOutput +Main.delimiter+e.getMessage());
                e.printStackTrace();

                if(CountFails>5) {
                    errorOutput = "CollectSupportData Thread is Terminating";
                    System.out.println(errorOutput);
                    Main.ErrorFile.addRowToReport("Exception", errorOutput);
                    terminate();
                }
            }
        }
    }

    public void CreateClient() {
        if (Main.Grid == false) { //use STA
            this.client = new Client(Main.local_host, Main.local_port, true);

        } else if (Main.Grid == true) { //use Grid)
            try {
                CreateCollectSupportData_api(Main.innerDirectoryPath);
            } catch (Exception e) {
                e.printStackTrace();
                String errorOutput = "CollectSupportData Thread - data wasn't collected";
                System.out.println(errorOutput);
                Main.ErrorFile.addRowToReport("Exception", errorOutput);
            }

            //System.setProperty("manager.url", "localhost:8181");
            //this.gridClient = new GridClient(Main.cloudUser.userName, Main.cloudUser.Password, Main.cloudUser.projectName, Main.cloudUser.grid_domain, Main.cloudUser.grid_port, Main.cloudUser.isSecured);
        }
    }

}

