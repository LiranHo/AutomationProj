import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//singleton
public class Reporter {
    public static void main(String[] args) {
        Reporter r = Reporter.Reporter ("reportname1","E:\\Files - Liran - 2");
        Reporter r2 = Reporter.Reporter ("reportname2","E:\\Files - Liran - 2");

    }
    private static Reporter report= null;

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    PrintWriter file;
    Device device;
    String FileContent;

    //singleton constructor (defined as private)
    private Reporter(String FileName , String projDir) {
//        String projDir = System.getProperty("user.dir");
        //Crete new report
        this.file=CreateReportFile(projDir, FileName+" ; "+ Main.startTime, "csv");
        //print first line to this report
//        this.file.println("### Report: "+", "+FileName+" , "+Main.startTime+" ###");
//        this.file.flush();
    }


    //Create report only once
    public static Reporter Reporter(String FileName , String projDir){
        if(report == null)
            report=new Reporter(FileName , projDir);
        return report;
    }



    //T: add row to main report
    public void addRowToReport(String type, String SessionID, String testName, String deviceSN, String status, String reportURL){
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = currentTime+","+
                type+","+
                SessionID+","+
                testName+","+
                deviceSN+","+
                status+","+
                reportURL ;
        System.out.println(line);
        file.println(line);
        file.flush();
    }


    public static PrintWriter CreateReportFile (String Path, String FileName , String FileFormat){
        //create New File
        File report = new File(Path + "/" + FileName+"."+FileFormat);
        FileWriter fw = null;
        try {
            fw = new FileWriter(report);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        return pw;
    }

}
