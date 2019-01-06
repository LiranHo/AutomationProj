package Project.Reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportBasics {

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
