/**
 * 
 */
package cs601.project4;

import org.eclipse.jetty.servlet.ServletHandler;

import server.Pro4Server;
import userService.UserCreateServlet;
import userService.UserServlet;

/**
User Service - The user service will manage the user account information, including the events 
for which a user has purchased tickets. The API will support the following operations:

# Create a new user -->> POST /create
# Get user details -->> GET /{userid}
# Add a new ticket for a user -->> POST /{userid}/tickets/add
# Transfer tickets from one user to another -->> POST /{userid}/tickets/transfer

*/


/**
 * 
 * @author anuragjha
 *
 */
public class UserService {
	
	public static void main(String[] args) {
		
		Pro4Server server = new Pro4Server(7072);

		/*
		POST /create
		GET /{userid}
		POST /{userid}/tickets/add
		POST /{userid}/tickets/transfer
		 */
		
		//server.addMapping(UserCreateServlet.class, "/create");
		//server.addMapping(UserServlet.class, "/*");

//		server.addMapping(UserDetailsServlet.class, "/*"); //get
//		server.addMapping(UserEventTicketsAddServlet.class, "/*/tickets/add"); //post
//		server.addMapping(UserEventTicketsTransferServlet.class, "/*/tickets/transfer"); //post
		
		//ServletContextHandler sch = new ServletContextHandler();
		//sch.setContextPath("/*");
		//server.getJettyServer().setHandler(sch);
		
		//sch.addServlet(new ServletHolder(new UserCreateServlet()), "/create");
		//sch.addServlet(new ServletHolder(new UserDetailsServlet()), "/*");
		//sch.addServlet(new ServletHolder(new UserEventTicketsAddServlet()),);
		//sch.addServlet(new ServletHolder(new UserEventTicketsTransferServlet()), "/*./tickets/transfer");
	
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(UserCreateServlet.class, "/create");
		handler.addServletWithMapping(UserServlet.class, "/*");
		//handler.addServletWithMapping(UserEventTicketsAddServlet.class, "*/tickets/add");
		//handler.addServletWithMapping(UserDetailsServlet.class, "/*/tickets/transfer");
		server.getJettyServer().setHandler(handler);
		
		
		
		server.start();
		
		
	}

}
