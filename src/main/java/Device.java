

public class Device {
    protected String serialnumber;
    protected String name;
    protected String os;
    protected String version;
    protected String model;
    protected String category;
    protected String manufacture;
    protected String remote;
    protected String reservedtoyou;



    public Device(String serialnumber, String name, String os, String version , String model, String category,  String manufacture, String remote, String reservedtoyou){
        this.serialnumber =serialnumber;
        this.name =name;
        this.os =os;
        this.version =version;
        this.model =model;
        this.category =category;
        this.manufacture =manufacture;
        this.remote =remote;
        this.reservedtoyou = reservedtoyou;
    }

    public Device(String serialnumber){
        this(serialnumber,"","","","","","","","");
    }


    public String toString(){
        String delimiter = "\r\n";
        String deviceParameter=
                "Device info: "+delimiter+
                        "serialnumber: "+this.serialnumber+delimiter+
                        "name: "+this.name+delimiter+
                        "os: "+this.os+delimiter+
                        "version: "+this.version+delimiter+
                        "model: "+this.model+delimiter+
                        "category: "+this.category+delimiter+
                        "manufacture: "+this.manufacture+delimiter+
                        "remote: "+this.remote;
        if(this.remote.equals("true")){deviceParameter+=delimiter+"reservedtoyou: "+this.reservedtoyou;}

    return deviceParameter;

    }

}