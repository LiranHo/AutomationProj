package Project.Properties;

import Project.Main;

public enum CloudUsers {
    //TODO: add properties file to save all the passwords / access key
    //Todo: https://www.mkyong.com/java/java-properties-file-examples/
    LiranCloud("liranh","Experitest2012", "default", false,"lirans-mac-mini.experitest.local", 80, "Basic bGlyYW5oOkV4cGVyaXRlc3QyMDEy"),
    LiranQaCloud("liranh","Experitest2012", "default", true,"qacloud.experitest.com", 443),
    LiranMasterCloud("liranh","Experitest2012", "default", true,"mastercloud.experitest.com", 443),
    LiranReleaseCloud("liranh","Experitest2012", "default", true,"releasecloud.experitest.com", 443),
    LiranWindowsCloud("liranh","Experitest2012", "default", false,"192.168.2.225", 80),
    AdminDeepCloud("admin","Experitest2012", "default", true,"qa-win2016.experitest.com", 443),
    KBC_Cloud("Experitest_rnd2","Aa123456", "default", true,"kbc.experitest.com", 443),
    Ortals_Cloud("admin","Aa123456", "default", false,"192.168.2.191", 80),

    ;

    public String userName;
    public String Password;
    public String projectName;
    public boolean isSecured;
    public String grid_domain; //without https
    public int grid_port;
    public String Authorization;

    CloudUsers(String userName, String Password , String projectName , Boolean isSecured ,
               String grid_domain , int grid_port){
        this.userName=userName;
        this.Password=Password;
        this.projectName=projectName;
        this.isSecured=isSecured;
        this.grid_domain=grid_domain;
        this.grid_port=grid_port;
        this.Authorization="";
    }

    CloudUsers(String userName, String Password , String projectName , Boolean isSecured ,
               String grid_domain , int grid_port, String Authorization){
        this.userName=userName;
        this.Password=Password;
        this.projectName=projectName;
        this.isSecured=isSecured;
        this.grid_domain=grid_domain;
        this.grid_port=grid_port;
        this.Authorization=Authorization;
    }



    public String toString(boolean PrintInOneLine){

        if(PrintInOneLine){
            String starts="http";
            if(isSecured) starts+="s";
            return starts+"://"+grid_domain+":"+grid_port+"   |    "+"UserName: "+userName;
        }else
        return "## Cloud User: ##"+ Main.delimiter+
                "userName: "+userName+ Main.delimiter+
                "projectName: "+projectName+ Main.delimiter+
                "isSecured: "+isSecured+ Main.delimiter +
                "grid_domain: "+grid_domain+ Main.delimiter +
                "grid_port: "+grid_port + Main.delimiter
                ;
    }

    public String toString(){
        return "## Cloud User: ##"+ Main.delimiter+
                "userName: "+userName+ Main.delimiter+
                "projectName: "+projectName+ Main.delimiter+
                "isSecured: "+isSecured+ Main.delimiter +
                "grid_domain: "+grid_domain+ Main.delimiter +
                "grid_port: "+grid_port + Main.delimiter
                ;
    }

    }
