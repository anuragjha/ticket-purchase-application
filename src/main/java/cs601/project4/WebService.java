package cs601.project4;

import server.Pro4Server;
import service.web.EventsServicesCallerServlet;
import service.web.UsersServicesCallerServlet;

/**
 *
 * @author anuragjha
 * Web service class starts the web service
 */
public class WebService {
	
	private static Project4Init project4Init;
	private static Project4Logger p4Logger;
	
	
	/**
	 * constructor
	 * @param init
	 */
	public WebService(Project4Init init) {
		project4Init = init;
		this.initializeLogger();
	}
	
	
	/**
	 * @return the webInit
	 */
	public static Project4Init getProject4Init() {
		return project4Init;
	}
	
	
	/**
	 * initializeLogger method opens the logger to be used
	 */
	private void initializeLogger() {
		Project4Logger.initialize("Web Service - " + project4Init.getPortWS(), project4Init.getLoggerFile());
	}
	
	
	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication() {
		System.out.println("Web Service");
		
		Pro4Server server = new Pro4Server(project4Init.getPortWS());
		server.initialize(project4Init.getMinThreads(), project4Init.getMaxThreads(), 
				project4Init.getTimeout());
		
		server.addMapping(EventsServicesCallerServlet.class, "/events/*");
		server.addMapping(UsersServicesCallerServlet.class, "/users/*");
		
		server.start();
	}
	
	public static void main(String[] args) {
		
		Project4Init init;
		
		if(new CmdLineArgsValidator().check(args))	{
			//reading configuration file content into Project4Init object
			init = (Project4Init) InitJsonReader.
					project4InitJsonReader(args[0], Project4Init.class);
			AppConstants.setInit(init);
		} else {
			init = null;
			System.out.println("Unable to initialize, exiting system");
			System.exit(1);
		}
		
		WebService webService = new WebService(init);

		webService.startApplication();

		Project4Logger.close();
	}

}
