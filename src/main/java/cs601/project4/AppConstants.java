/**
 * 
 */
package cs601.project4;

/**
 * @author anuragjha
 * AppConstants class contains config parameters
 */
public class AppConstants {

	private static Project4Init init;
	
	/**
	 * @return the configFile
	 */
	public static Project4Init getInit() {
		return init;
	}

	/**
	 * @param configFile the configFile to set
	 */
	public static void setInit(Project4Init configFile) {
		
		AppConstants.init = configFile;
	}

	/**
	 * 
	 */
	public AppConstants() {
		// TODO Auto-generated constructor stub
	}

	
	
}
