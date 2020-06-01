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
	
	public void ADB_WakeUpDevice() throws Exception {
		
		runCommand("adb shell input keyevent KEYCODE_WAKEUP");
		runCommand("adb shell input keyevent 82");
		
	}
	
	public void ADB_ScreenLock() throws Exception {
		
		runCommand("adb shell input keyevent KEYCODE_WAKEUP");
		runCommand("adb shell input keyevent 82");
		
	}

}
