package me.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.Hime;

public class LogUtil {
	//ArrayList<String> toSave = new ArrayList<String>();
	//private File dir;
	//private File dataFile;
	
	  public static LogUtil instance = new LogUtil();
	
	public void log(String message, LogType type) {
		DateFormat dff = new SimpleDateFormat("HH:mm:ss");
    	java.util.Date todayy  = Calendar.getInstance().getTime();
    	String renderTime  = dff.format(todayy);
	switch(type) {
		case NORMAL:
			System.out.println("[" + Hime.instance.instance.name + " " + Hime.instance.version + "] [INFO] [" + renderTime +"]: " + message);
			//toSave.add("[" + Hime.instance.name + " v" + Hime.instance.version + "] [INFO] [" + renderTime +"]: " + message);
		//	this.save();
			break;
		case WARNING:
			System.out.println("[" + Hime.instance.name + " " + Hime.instance.version + "] [WARNING] [" + renderTime +"]: " + message);
		//	toSave.add("[" + Hime.instance.name + " v" + Hime.instance.version + "] [WARNING] [" + renderTime +"]: " + message);
			//this.save();
			break;
		case ERROR:
			System.err.println("[" + Hime.instance.name + " " + Hime.instance.version + "] [ERROR] [" + renderTime +"]: " + message);
			//toSave.add("[" + Hime.instance.name + " v" + Hime.instance.version + "] [ERROR] [" + renderTime +"]: " + message);
			//this.save();
			break;
		}
	}
/*	
	public void saveLog() {
		dir = new File(Minecraft.getMinecraft().mcDataDir, "Hime");
		if (!dir.exists()) {
			dir.mkdir();
		}
		dataFile = new File(dir, "logger.txt");
		
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {e.printStackTrace();}
		}
		
	}
	
	public void save() {
	
		
		   try {
				PrintWriter pw1 = new PrintWriter(this.dataFile);
			
				for (String str1 : toSave) {
					//System.out.println(str);
					pw1.println(str1);
				}
				pw1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	*/
	
	
	
	public static enum LogType {
		NORMAL, WARNING, ERROR
	}
	
}
