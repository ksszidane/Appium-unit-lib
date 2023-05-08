package unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ADB {
	
	String os = System.getProperty("os.name").toLowerCase();
	
	Process p; 
	
	//adb shell dumpsys window | grep -E 'mCurrentFocus'
	
	public String OS_ver(String Device) throws InterruptedException, IOException {
        
		Process p = null;
		
		if (os.contains("win")) {
			p = Runtime.getRuntime().exec("adb -s "+Device+" shell getprop ro.build.version.release");
		} else if (os.contains("mac")) {
			p = Runtime.getRuntime().exec("/opt/homebrew/bin/adb -s "+Device+" shell getprop ro.build.version.release");
		}
		
		

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = reader.readLine();

        System.out.println("Android OS Version: " + line);

        p.destroy();
        
        return line;
	}
	
	
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
		ADB_connect(Device);
		ADB_devices();
		
	}
	
	public void ADB_connect(String Device) throws Exception {
		
		if (os.contains("win")) {
			String outputCMD = runCommand("adb connect "+Device);
			String[] lines = outputCMD.split("\n");
			System.out.println("adb connect "+Device);
			for(String str:lines) {
	    		System.out.println(str); 
	    	}

        } else if (os.contains("mac")) {
        	String outputCMD = runCommand("/opt/homebrew/bin/adb connect "+Device);
        	String[] lines = outputCMD.split("\n");
        	System.out.println("adb connect "+Device);
        	for(String str:lines) {
        		System.out.println(str); 
        	}
        }
		
	}
	
	public void ADB_stopADB() throws Exception {
		
		if (os.contains("win")) {
			String outputCMD = runCommand("adb kill-server");
			String[] lines = outputCMD.split("\n");
			System.out.println("adb kill-server");
			for(String str:lines) {
	    		System.out.println(str); 
	    	}

        } else if (os.contains("mac")) {
        	String outputCMD = runCommand("/opt/homebrew/bin/adb kill-server");
        	String[] lines = outputCMD.split("\n");
        	System.out.println("adb kill-server");
        	for(String str:lines) {
        		System.out.println(str); 
        	}
        }
		
	}
	
	public void ADB_startADB() throws Exception {
		
		if (os.contains("win")) {
			String outputCMD = runCommand("adb start-server");
			String[] lines = outputCMD.split("\n");
			System.out.println("adb start-server");
			for(String str:lines) {
	    		System.out.println(str); 
	    	}

        } else if (os.contains("mac")) {
        	String outputCMD = runCommand("/opt/homebrew/bin/adb start-server");
        	String[] lines = outputCMD.split("\n");
        	System.out.println("adb start-server");
        	for(String str:lines) {
        		System.out.println(str); 
        	}
        }
		
	}
	public void ADB_devices() throws Exception {
		
		if (os.contains("win")) {
			String outputCMD = runCommand("adb devices -l");
			String[] lines = outputCMD.split("\n");
			System.out.println("adb devices -l");
			for(String str:lines) {
	    		System.out.println(str); 
	    	}

        } else if (os.contains("mac")) {
        	String outputCMD = runCommand("/opt/homebrew/bin/adb devices -l");
        	String[] lines = outputCMD.split("\n");
        	System.out.println("adb devices -l");
        	for(String str:lines) {
        		System.out.println(str); 
        	}
        }
	}
	
	public void ADB_WakeUpDevice(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell input keyevent KEYCODE_WAKEUP");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell input keyevent KEYCODE_WAKEUP");
        }

		System.out.println("adb -s "+Device+" shell input KEYCODE_WAKEUP");
		//runCommand("adb -s "+Device+" shell input keyevent 82");
		//System.out.println("adb -s "+Device+" shell input keyevent 82");
	}
	
	public void ADB_ScreenLock(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell input keyevent 26");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell input keyevent 26");
        }
		
		System.out.println("adb -s "+Device+" shell input keyevent 26");
	}
	
	public void ADB_AppStop(String Device, String PakageName) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm clear " + PakageName);

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm clear " + PakageName);
        }
		
		System.out.println("adb -s "+Device+" shell pm clear " + PakageName);
	}
	
	public void ADB_forcestop(String Device, String PakageName) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell am force-stop" + PakageName);

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell am force-stop" + PakageName);
        }
		
		System.out.println("adb -s "+Device+" shell am force-stop" + PakageName);
	}
	
	public void ADB_WiFi_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell svc wifi disable");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell svc wifi disable");
        }
		
		System.out.println("adb -s "+Device+" shell svc wifi disable");
	}
	
	public void ADB_WiFi_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell svc wifi enable");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell svc wifi enable");
        }
		
		System.out.println("adb -s "+Device+" shell svc wifi enable");
	}
	
	public void ADB_cellular_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell svc data disable");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell svc data disable");
        }
		
		System.out.println("adb -s "+Device+" shell svc data disable");
	}
	
	public void ADB_cellular_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell svc data enable");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell svc data enable");
        }
		
		System.out.println("adb -s "+Device+" shell svc data enable");
	}
	
	public void ADB_GPS_On(String Device) throws Exception {

		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed +gps");
			runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed +network");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell settings put secure location_providers_allowed +gps");
    		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell settings put secure location_providers_allowed +network");
        }
	
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed +gps");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed +network");
		Thread.sleep(1000);
	}
	
	public void ADB_GPS_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed -gps");
			runCommand("adb -s "+Device+" shell settings put secure location_providers_allowed -network");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell settings put secure location_providers_allowed -gps");
    		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell settings put secure location_providers_allowed -network");
        }
		
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed -gps");
		System.out.println("adb -s "+Device+" shell settings put secure location_providers_allowed -network");
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
        }
		
		//System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION"); //항상
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
        }
		
		//System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION"); //항상
		Thread.sleep(1000);
	}
	
	public void NUGUAPP_permission_LOCATION_Off_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
			runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
        }
		
		//runCommand("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
		
		//runCommand("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		//System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION");
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_COARSE_LOCATION");
		Thread.sleep(1000);
		
	}
	
	public void ChipsApp_permission_LOCATION_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION");
        }
		
		//System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION"); //항상
		Thread.sleep(1000);
	}
	
	public void ChipsApp_permission_LOCATION_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION");
        }
		
		//System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.ACCESS_FINE_LOCATION"); //항상
		Thread.sleep(1000);
	}
	
	public void ChipsApp_permission_MIC_On(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.RECORD_AUDIO");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.RECORD_AUDIO");
        }
		
		//System.out.println("adb -s "+Device+" shell pm grant com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm grant com.skt.aidev.nugufriends android.permission.RECORD_AUDIO"); //항상
		Thread.sleep(1000);
	}
	
	public void ChipsApp_permission_MIC_Off(String Device) throws Exception {
		
		if (os.contains("win")) {
			runCommand("adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.RECORD_AUDIO");

        } else if (os.contains("mac")) {
        	runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.RECORD_AUDIO");
        }
		
		//System.out.println("adb -s "+Device+" shell pm revoke com.skt.aladdin android.permission.ACCESS_FINE_LOCATION"); //사용하는 동안
		System.out.println("adb -s "+Device+" shell pm revoke com.skt.aidev.nugufriends android.permission.RECORD_AUDIO"); //항상
		Thread.sleep(1000);
	}
	
	public void permission_draw_over_other_apps_Off(String Device, String AppPackage) throws Exception {
		
		String ver = this.OS_ver(Device);
		
		if (os.contains("win")) {
			if(ver.contains("13")) {
				runCommand("adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW ignore");
				System.out.println("adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW ignore");
			} else {
				runCommand("adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
				System.out.println("adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
			}

        } else if (os.contains("mac")) {
        	if(ver.contains("13")) {
        		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW ignore");
				System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW ignore");
        	} else {
        		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
        		System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
        	}
        	
        }
		Thread.sleep(1000);
	}
	
	public void permission_draw_over_other_apps_On(String Device, String AppPackage) throws Exception {
		
		String ver = this.OS_ver(Device);
		
		if (os.contains("win")) {
			if(ver.contains("13")) {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW allow");
				System.out.println("adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW allow");
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
				System.out.println("adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
			}
			

        } else if (os.contains("mac")) {
        	
        	if(ver.contains("13")) {
        		System.out.println("구동 OS : " + os);
        		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW allow");
				System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " SYSTEM_ALERT_WINDOW allow");
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
				System.out.println("/opt/homebrew/bin/adb  -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.SYSTEM_ALERT_WINDOW");
			}
        }

		Thread.sleep(1000);
	}
	
	public void permission_BATTERY_OPTIMIZATIONS_Off(String Device, String AppPackage) throws Exception {
		
		String ver = this.OS_ver(Device);
		
		if (os.contains("win")) {
			if(ver.contains("13")) {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS ignore");
				System.out.println("adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS ignore");
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
				System.out.println("adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
			}


        } else if (os.contains("mac")) {
        	if(ver.contains("13")) {
        		System.out.println("구동 OS : " + os);
        		runCommand("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS ignore");
				System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS ignore");
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
	        	System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell pm revoke " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
			}
        	
        }
		
		Thread.sleep(1000);
	}
	
	public void permission_BATTERY_OPTIMIZATIONS_On(String Device, String AppPackage) throws Exception {
		
		String ver = this.OS_ver(Device);
		
		if (os.contains("win")) {
			if(ver.contains("13")) {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS allow");
				System.out.println("adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS allow");
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
				System.out.println("adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"); //항상
				
			}
			
	
        } else if (os.contains("mac")) {
        	if(ver.contains("13")) {
				runCommand("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS allow");
				System.out.println("구동 OS : " + os);
				System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell appops set " + AppPackage + " REQUEST_IGNORE_BATTERY_OPTIMIZATIONS allow" );
			} else {
				System.out.println("구동 OS : " + os);
				runCommand("/opt/homebrew/bin/adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
	        	System.out.println("/opt/homebrew/bin/adb -s "+Device+" shell pm grant --user 0 " + AppPackage + " android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS"); //항상
			}
        	
    		
        }
		
	}

}
