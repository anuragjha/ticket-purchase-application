/**
 * 
 */
package cs601.project4;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


/**
 * @author anuragjha
 *
 */
public class Project4Logger {

	private static Logger logger; 
	private static FileHandler logHandler;
	private static SimpleFormatter readableFormat = new SimpleFormatter();


	/**
	 * initialize method opens the logger
	 * @param logName
	 * @param logFile
	 */
	public static void initialize(String logName, String logFile)	{

		logger = Logger.getLogger(logName);

		try	{
			logHandler = new FileHandler(logFile);	

		} catch(IOException ioe)	{
			System.out.println("Error while acquiring the file handler for the logger");
		}

		logHandler.setFormatter(readableFormat);

		logger.addHandler(logHandler);

		logger.log(Level.INFO, "Logger Name: " + logName + "   |    LogFile: " + logFile, 0);

	}

	/**
	 * write method writes to the logger
	 * @param level
	 * @param msg
	 * @param thrown
	 */
	public static void write(Level level, String msg, int thrown)	{
		logger.log(level, msg, thrown);
	}

	/**
	 * close method closes the logger
	 */
	public static void close()	{
		logHandler.close();
	}


	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

