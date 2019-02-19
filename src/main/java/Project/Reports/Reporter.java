package Project.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import Project.*;


//singleton
public class Reporter extends ReportBasics {
    public static void main(String[] args) {
//        Project.Reports.Reporter r = Project.Reports.Reporter.Project.Reports.Reporter ("reportname1","E:\\Project.Reports.Files - Liran - 2");
//        Project.Reports.Reporter r2 = Project.Reports.Reporter.Project.Reports.Reporter ("reportname2","E:\\Project.Reports.Files - Liran - 2");

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
//        this.file.println("### Report: "+", "+FileName+" , "+Project.Main.startTime+" ###");
//        this.file.flush();
    }


    //Create report only once
    public static Reporter Reporter(String FileName , String projDir){
        if(report == null)
            report=new Reporter(FileName , projDir);
        return report;
    }



    //T: add row to main report
    public void addRowToReport(String type, String testName, String deviceSN, String status,String testDuring,String SessionID, String reportURL , String exception){
        Date currentTime = new Date();
        String line;
        currentTime.getTime();
        line = currentTime+","+
                type+","+
                testName+","+
                deviceSN+","+
                status+","+
                testDuring+","+
                SessionID+","+
                reportURL+","+
                exception;
        System.out.println(line);
        file.println(line);
        file.flush();
    }




}
