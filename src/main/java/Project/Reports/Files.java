package Project.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import Project.*;

//this is NOT singelton
public class Files extends ReportBasics {
    private static Files report= null;

    PrintWriter file;

    //constructor (this is NOT singleton)
    public Files(String FileName , String projDir) {
        //Crete new report
        this.file=CreateReportFile(projDir, FileName+" ; "+ Main.startTime, "txt");
    }


    //T: add row to main report
    public void addRowToReport(boolean addTime, String text){
        String line="";
        if(addTime){
        Date currentTime = new Date();
        currentTime.getTime();
        line = "** "+currentTime+"; ";}
        line+= text;
//        System.out.println(line);
        file.println(line);
        file.flush();
    }

    public String addRowToReport(String LogType, String text){
        String line="";
            Date currentTime = new Date();
            currentTime.getTime();
            line = "** "+currentTime+"; #"+LogType+"# ; "+text;
//        System.out.println(line);
        file.println(line);
        file.flush();

        return line;
    }

    public void addRowToReport(boolean addTime, String text, boolean addFailureStamp){
        String line="";
        if(addTime){
            Date currentTime = new Date();
            currentTime.getTime();
            line = currentTime+"; "+ Main.delimiter;}
            if (addFailureStamp){
            line += "***FAILURE***" + Main.delimiter;
            }
        line+= text;
//        System.out.println(line);
        file.println(line);
        file.flush();
    }



}
