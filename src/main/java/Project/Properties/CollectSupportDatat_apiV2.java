package Project.Properties;

import Project.Main;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CollectSupportDatat_apiV2 {

    public static void CreateCollectSupportData_api(String FileDestination) throws IOException {
        SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

        String http;
        if(Main.cloudUser.isSecured){
            http="https://";}
        else{
            http="http://";}


        DefaultHttpClient httpClient = new DefaultHttpClient();


        HttpGet getRequest = new HttpGet(
        //NOTE:"https://qa-win2016.experitest.com:443" + "/api/v2/configuration/collect-support-data/false/false/955983,955991,955999/-1/-1"); //{onlyLatestLogs}/{onlyServer}/{dhmIds}/{deviceId}/{seleniumAgentIds}
                http+Main.cloudUser.grid_domain+":"+Main.cloudUser.grid_port+"/api/v2/configuration/collect-support-data/false/false"
        );
        getRequest.addHeader("accept", "application/json");
        getRequest.addHeader("Authorization", Main.cloudUser.Authorization);

        HttpResponse response = httpClient.execute(getRequest);
        Date now = new Date();
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        File targetFile = new File(FileDestination +"\\SupportData"+ sdfFormat.format(now) + ".zip");

//            File targetFile = new File("C:\\Program Files (x86)\\Experitest\\Reporter\\supportDataSpecified\\" + sdfFormat.format(now) + ".zip");
        FileUtils.copyInputStreamToFile(response.getEntity().getContent(), targetFile);
        httpClient.getConnectionManager().shutdown();
        System.out.println("data collected");


    }
}
