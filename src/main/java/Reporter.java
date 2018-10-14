import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

//singleton
public class Reporter {
    public static void main(String[] args) {
        Reporter r = Reporter.Reporter ("reportname1");
        Reporter r2 = Reporter.Reporter ("reportname2");



    }

    private static Reporter report= null;

    static SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy-HH:mm:ss,SS");

    PrintWriter file;
    Device device;

    //singleton constructor (defined as private)
    private Reporter(String FileName) {
        String projDir = System.getProperty("user.dir");
        //Crete new report
        this.file=CreateReportFile(projDir, FileName);
        //print first line to this report
        this.file.println("### Report: "+","+FileName+","+"###");
        this.file.flush();
    }


    //Create report only once
    public static Reporter Reporter(String FileName){
        if(report == null)
            report=new Reporter(FileName);

        return report;
    }





    public static PrintWriter CreateReportFile (String Path, String FileName){
        //create New File
        File report = new File(Path + "/" + FileName+".csv");
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
