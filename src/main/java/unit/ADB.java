package unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ADB {
	
	Process p;
	
	public String runCommand(String command) throws InterruptedException, IOException {
		 
		p = Runtime.getRuntime().exec(command);
		BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		String line = "";
		String allLine = "";
				
		while ((line = r.readLine()) != null) {
			/* if (line.isEmpty()) {
				break;
			}*/
			allLine = allLine + "" + line + "\n";
            if (line.contains("Console LogLevel: debug") && line.contains("Complete"))
				break;	
		}
		return allLine;
	}
	
	public void ADB_SetCommand(String Device) throws Exception {
		ADB_stopADB();
		ADB_startADB();
		ADB_devices();
		ADB_WakeUpDevice(Device);
		ADB_GPS_On(Device);
		
	}
	
	public void ADB_stopADB() throws Exception {
		
		runCommand("adb kill-server");
		System.out.println("adb kill-server");
		
	}
	
	public void ADB_startADB() throws Exception {
		
		runCommand("adb start-server");
		System.out.println("adb start-server");
		
	}
	public void ADB_devices() throws Exception {
		
		String outputCMD = runCommand("adb devices -l");
		System.out.println("adb devices -l");
		String[] lines = outputCMD.split("\n");
		
		for(String str:lines) {
    		System.out.println(str); 
    	}
	
	}
	
	public void ADB_WakeUpDevice(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell input keyevent KEYCODE_WAKEUP");
		System.out.println("adb -s "+Device+" shell input keyevent 26");
		runCommand("adb -s "+Device+" shell input keyevent 82");
		System.out.println("adb -s "+Device+" shell input keyevent 82");
	}
	
	public void ADB_ScreenLock(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell input keyevent 26");
		System.out.println("adb -s "+Device+" shell input keyevent 26");
	}
	
	public void ADB_WiFi_Off(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell svc wifi disable");
		System.out.println("adb -s "+Device+" shell svc wifi disable");
	}
	
	public void ADB_WiFi_On(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell svc wifi enable");
		System.out.println("adb -s "+Device+" shell svc wifi enable");
	}
	
	public void ADB_cellular_Off(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell svc data disable");
		System.out.println("adb -s "+Device+" shell svc data disable");
	}
	
	public void ADB_cellular_On(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell svc data enable");
		System.out.println("adb -s "+Device+" shell svc data enable");
	}
	
	public void ADB_GPS_On(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed +gps");
		runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed +network");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed +gps");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed +network");
		Thread.sleep(1000);
	}
	
	public void ADB_GPS_Off(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed -gps");
		runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed -network");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed -gps");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed -network");
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_On(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_Off(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_Off_On(String Device) throws Exception {
		
		runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
		
		runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
		
		
	}

}
