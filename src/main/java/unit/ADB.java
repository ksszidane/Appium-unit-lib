package unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

}
