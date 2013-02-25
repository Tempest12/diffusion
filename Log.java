import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Date;

/**
 * Logging Utility. Static class.
 * @author Shane del Solar
 * @version 0.00
 */

public class Log
{
	private static File logFile = null;
	private static PrintWriter logWriter = null;

	private static boolean ready = false;
	
	//Log Levels Explained
	private static final int debugLevel = 0;//0 = Debug and above
	private static final int infoLevel = 1;//1 = Info and above
	private static final int errorLevel = 2;//3 = Fatal and above
	private static final int fatalLevel = 3;//2 = Error and above
	private static final int specialOnly = 4;//4 = Special only BTW things marked special always log.
	private static int logLevel = 0;
	
	/**
	 * Initializes the Logging utility. *WARNING STOP EXECUTION WHEN AN ERROR OCCURS*
	 * @param startingLevel - The starting level that the log will record.
	 * @see Main.die(String)
         */
	public static void init(int startingLevel)
	{
		logFile = new File("log.txt");
		if(logFile.exists())
		{
			//Start a new log file every time
			logFile.delete();
			try
			{				
				logFile.createNewFile();
			}			
			catch(IOException ioe)
			{		
				Main.die("Log Error: An IOException has occured while trying to create the log file. Is directory readonly? Exception Message: " + ioe.getMessage() + "\".");
			}
		}
		else
		{
			try
			{
				logFile.createNewFile();
			}
			catch(IOException ioe)
			{
				Main.die("Log Error: An IOException has occured while trying to create the log file. Is directory readonly? Exception Message: " + ioe.getMessage() + "\".");
			}
		} 

		try
		{
			logWriter = new PrintWriter(logFile);	
		}
		catch(FileNotFoundException fnfe)
		{
			Main.die("Log Error: Could not find or create log file. Looked in this path: \"" + logFile.getPath() + "\" and the exception message: \"" + fnfe.getMessage() + "\".");
		}		
		
		ready = !logWriter.checkError(); 

		printTimeStamp();
		logWriter.println(" : Log opened");

		ready = !logWriter.checkError();
	}
	
	/**
	 * Uninitializes the Logging Utility.
	 */
	public static void uninit()
	{
		if(ready)
		{
			printTimeStamp();
			logWriter.println(" : Log Closed");
		}
		
		logWriter.close();
	}
	
	/**
	 * Returns the status of the underlying output stream.
	 * @return boolean - The status of the PrintWriter
	 */
	public static boolean isLogReady()
	{
		return ready;//False means an error has occured
	}
	
	/**
	 * Logs the given message as DEBUG level if the current level allows.
	 * @param message - The message that should be logged.
	 */
	public static void writeDebug(String message)
	{
		if(ready && debugLevel >= logLevel)
		{
			printTimeStamp();
			logWriter.println("   DEBUG: " + message);
		}	

		ready = !logWriter.checkError();
	}
	
	/**
	 * Logs the given message as INFO level if the current level allows.
	 * @param message - The message that should be logged.
	 */
	public static void writeInfo(String message)
	{
		if(ready && infoLevel >= logLevel)
		{
			printTimeStamp();
			logWriter.println("    INFO: " + message);
		}	

		ready = !logWriter.checkError();
	}
	
	/**
	 * Logs the given message as ERROR level if the current level allows.
	 * @param message - The message that should be loggeed.
	 */
	public static void writeError(String message)
	{
		if(ready && errorLevel >= logLevel)
		{
			printTimeStamp(); 
			logWriter.println("   ERROR: " + message);
		}	

		ready = !logWriter.checkError();
	}
	
	/**
	 * Logs the given message as FATAL level if the current level allows.
	 * @param message - The message that should be logged.
	 */
	public static void writeFatal(String message)
	{
		if(ready && fatalLevel >= logLevel)
		{
			printTimeStamp();
			logWriter.println("   FATAL: " + message);
		}	

		ready = !logWriter.checkError();
	}
	
	/**
	 * Logs the given message as SPECIAL level. *ALWAYS LOGS*
	 * @param message - The message that should be logged.
	 */
	public static void writeSpecial(String message)
	{
		if(ready)
		{
			printTimeStamp();
			logWriter.println(" SPECIAL: " + message);
		}	

		ready = !logWriter.checkError();
	}
	
	/**
	 * Changes the current Log level
	 * @param newLevel - The next Log level.
	 */
	public static void setLevel(int newLevel)
	{
		logLevel = newLevel;
	}
	
	/**
	 * Returns the current Log level
	 * @return int - The current Log level.
	 */
	public static int getCurrentLevel()
	{
		return logLevel;
	}
	
	/**
	 * Prints the current time into the log. Caled before each entry message is printed.
	 */
	private static void printTimeStamp()
	{
		if(ready)
		{
			Date temp = new Date();
			logWriter.print(temp.toString());
		}
		
		ready = !logWriter.checkError();
	}
}
