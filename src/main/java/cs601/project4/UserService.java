/**
 * 
 */
package cs601.project4;

import org.eclipse.jetty.servlet.ServletHandler;

import server.Pro4Server;
import service.user.UserCreateServlet;
import service.user.UserServlet;

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
		
		System.out.println("User Service");
		Pro4Server server = new Pro4Server(7072);
		server.initialize(10, 100, 120);

		/*
		POST /create
		GET /{userid}
		POST /{userid}/tickets/add
		POST /{userid}/tickets/transfer
		 */
		
		server.addMapping(UserCreateServlet.class, "/create");
		server.addMapping(UserServlet.class, "/*");		
		
		server.start();
		
		
	}

}
