/**
 * 
 */
package cs601.project4;

import server.Pro4Server;
import userService.UserCreateServlet;
import userService.UserDetailsServlet;
import userService.UserEventTicketsAddServlet;
import userService.UserEventTicketsTransferServlet;

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
		server.addMapping(UserCreateServlet.class, "/create");
		server.addMapping(UserDetailsServlet.class, "/*");
		server.addMapping(UserEventTicketsAddServlet.class, "/*/tickets/add");
		server.addMapping(UserEventTicketsTransferServlet.class, "/*/tickets/transfer");
		
		server.start();
		
	}

}
