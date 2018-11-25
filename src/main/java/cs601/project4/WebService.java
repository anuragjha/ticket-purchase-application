package cs601.project4;

import server.Pro4Server;
import webService.EventsServicesCallerServlet;
import webService.UsersServicesCallerServlet;

/**
Web Front End - The web front end will implement an external web service API for the 
application and will support APIs for the following operations:

# Get a list of all events -->> GET /events
# Create a new event -->> POST /events/create
# Get details about a specific event -->> GET /events/{eventid}
# Purchase tickets for an event -->> POST /events/{eventid}/purchase/{userid}
# Create a user -->> POST /users/create
# See a user's information, including details of 
all events for which the user has purchased tickets -->> GET /users/{userid}
# Transfer tickets from one user to another -->> POST /users/{userid}/tickets/transfer
 */

/**
 *
 * @author anuragjha
 *
 */
public class WebService {

	public WebService() {

	}

	
	public static void main(String[] args) {
		Pro4Server server = new Pro4Server(7070);

		server.addMapping(EventsServicesCallerServlet.class, "/events/*");
		server.addMapping(UsersServicesCallerServlet.class, "/users/*");
		
		server.start();
		
	}

}
