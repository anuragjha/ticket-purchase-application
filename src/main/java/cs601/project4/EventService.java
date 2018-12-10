/**
 * 
 */
package cs601.project4;

import server.Pro4Server;
import service.event.EventCreateServlet;
import service.event.EventDetailsServlet;
import service.event.EventTicketsPurchaseServlet;
import service.event.EventsListServlet;
import service.user.UserCreateServlet;
import service.user.UserServlet;

/**
 * @author anuragjha
 * EventService Class starts the event service
 */
public class EventService {
	
	private static Project4Init project4Init;
	private static Project4Logger p4Logger;
	
	
	/**
	 * constructor
	 * @param init
	 */
	public EventService(Project4Init init) {
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
		Project4Logger.initialize("User Service - " + project4Init.getPortES(), project4Init.getLoggerFile());
	}
	
	
	/**
	 * startApplication method binds the application to the port, where it listens to client request
	 * @param port
	 */
	private void startApplication() {
		System.out.println("Event Service");
		
		Pro4Server server = new Pro4Server(project4Init.getPortES());
		server.initialize(project4Init.getMinThreads(), project4Init.getMaxThreads(), 
				project4Init.getTimeout());

		server.addMapping(EventCreateServlet.class, "/create");
		server.addMapping(EventsListServlet.class, "/list");
		server.addMapping(EventDetailsServlet.class, "/*");
		server.addMapping(EventTicketsPurchaseServlet.class, "/purchase/*");

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
		
		EventService eventService = new EventService(init);

		eventService.startApplication();

		Project4Logger.close();
	}



}
