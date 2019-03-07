package Project.Tests.Other_Tests;

import Project.BaseTest;
import Project.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class InstallAndLaunch_Tests extends BaseTest{


    @DisplayName("ManyInstallTests")
    @Test
    public void ManyInstallTests(){
        for (int i = 0; i < 10; i++) {
            Main.sout("Info","ManyInstallTests #"+(i+1));

            client.install("E:\\Files - Liran - 2\\Applications_apk\\BankID - security app\\com.bankid.bus.activities.InitActivity.2.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\EriBank\\eribank.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\Fidelity\\fidelity.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\JP_Morgan\\SA-27877\\com.jpmorgan.android.mcl.fingerprint.sample_.LoginActivity__ver_1.0.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\KLM\\KLM Royal Dutch Airlines_v9.10.0_apkpure.com.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\navistar ; SA-27026\\OCCDriverLog-2.16.26.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\philips SA-26597\\philips.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\SA_26575\\app-releasev1.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\SA-27343 - BlackBerry-Chromium\\BBAccess_ENT_SEETEST-2.11.1.2000_no_obfuscation.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\Simple app for web browser\\com.example.app.MainActivity.2.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\United\\united.apk",true,false);
            client.install("E:\\Files - Liran - 2\\Applications_apk\\zirrat_26279\\ziraat.apk",true,false);
            client.install("C:\\Users\\liran.hochman\\Downloads\\apk\\Hungry Shark Evolution_v5.2.0_apkpure.com.apk",true,false);
            client.install("C:\\Users\\liran.hochman\\Downloads\\apk\\Digimon Heroes_v1.0.52_apkpure.com.apk",true,false);
            client.install("C:\\Users\\liran.hochman\\Downloads\\apk\\Facebook.apk",true,false);
        }

    }


    }
