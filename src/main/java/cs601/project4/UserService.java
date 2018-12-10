/**
 * 
 */
package cs601.project4;

import server.Pro4Server;
import service.user.UserCreateServlet;
import service.user.UserServlet;

/**
 * 
 * @author anuragjha
 * UserService class starts the user service
 */
public class UserService {
	
	private static Project4Init project4Init;
	private static Project4Logger p4Logger;
	
	
	/**
	 * constructor
	 * @param init
	 */
	public UserService(Project4Init init) {
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
		Project4Logger.initialize("User Service - " + project4Init.getPortUS(), project4Init.getLoggerFile());
	}
	
	
	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication() {
		System.out.println("User Service");
		
		Pro4Server server = new Pro4Server(project4Init.getPortUS());
		server.initialize(project4Init.getMinThreads(), project4Init.getMaxThreads(), 
				project4Init.getTimeout());
		
		
		server.addMapping(UserCreateServlet.class, "/create");
		server.addMapping(UserServlet.class, "/*");		
		
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
		
		UserService userService = new UserService(init);

		userService.startApplication();

		Project4Logger.close();
	}
	
	
//	
//	public static void main(String[] args) {
//		
//		System.out.println("User Service");
//		Pro4Server server = new Pro4Server(7072);
//		server.initialize(10, 100, 120);
//
//		/*
//		POST /create
//		GET /{userid}
//		POST /{userid}/tickets/add
//		POST /{userid}/tickets/transfer
//		 */
//		
//		server.addMapping(UserCreateServlet.class, "/create");
//		server.addMapping(UserServlet.class, "/*");		
//		
//		server.start();
//		
//		
//	}

}
