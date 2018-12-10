/**
 * 
 */
package cs601.project4;

/**
 * @author anuragjha
 * WebInit class holds the config information 
 *
 */
public class Project4Init {

	private int portWS;
	private int portUS;
	private int portES;
	private String loggerFile;
	
	//ThreadPool 
	private int minThreads;
	private int maxThreads;
	private int timeout;
	
	//URI basepath
	private String basepathWebService;
	private String basepathUserService;
	private String basepathEventService;

	//database
	private String username;
	private String password;
	private String db;
	private String dbHostname;
	
	
	
	/**
	 * constructor
	 */
	public Project4Init()	{
		
	}
	
	
	/**
	 * @return the port
	 */
	public  int getPortWS() {
		return portWS;
	}

	/**
	 * @return the port
	 */
	public  int getPortES() {
		return portES;
	}
	
	/**
	 * @return the port
	 */
	public  int getPortUS() {
		return portUS;
	}


	/**
	 * @return the loggerFile
	 */
	public  String getLoggerFile() {
		return loggerFile;
	}
	
	/**
	 * @return the basepathWebService
	 */
	public  String getBasepathWebService() {
		return basepathWebService;
	}


	/**
	 * @return the basepathUserService
	 */
	public  String getBasepathUserService() {
		return basepathUserService;
	}


	/**
	 * @return the basepathEventService
	 */
	public  String getBasepathEventService() {
		return basepathEventService;
	}

	
	
	/**
	 * @return the minThreads
	 */
	public  int getMinThreads() {
		return minThreads;
	}


	/**
	 * @return the maxThreads
	 */
	public  int getMaxThreads() {
		return maxThreads;
	}


	/**
	 * @return the timeout
	 */
	public  int getTimeout() {
		return timeout;
	}


	
	
	
	/**
	 * @return the username
	 */
	public  String getUsername() {
		return username;
	}


	/**
	 * @return the password
	 */
	public  String getPassword() {
		return password;
	}


	/**
	 * @return the db
	 */
	public  String getDb() {
		return db;
	}

	
	

	/**
	 * @return the dbHostname
	 */
	public String getDbHostname() {
		return dbHostname;
	}


	@Override
	public String toString()	{
		StringBuilder sb = new StringBuilder();
		
		//sb.append("port: " + this.port + "\n");
		//sb.append("loggerFile: " + this.loggerFile + "\n");

		return sb.toString();
	}
	
	
	/**
	 * @param args
	 */
	public  void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
