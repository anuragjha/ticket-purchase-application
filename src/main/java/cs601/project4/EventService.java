/**
 * 
 */
package cs601.project4;

import eventService.EventCreateServlet;
import eventService.EventDetailsServlet;
import eventService.EventTicketsPurchaseServlet;
import eventService.EventsListServlet;
import server.Pro4Server;

/**
Event Service - The event service will manage the list of events and the number of tickets 
sold and available for each. When a ticket is purchased it is the responsibility of the Event 
Service to notify the User Service of the user's purchase. The API will support the following 
operations:

# Create a new event -->> POST /create
# Get a list of all events -->> GET /list
# Get details about a specific event -->> GET /{eventid}
# Purchase tickets for an event, updating the user's ticket list -->> POST /purchase/{eventid}

 */


/**
 * @author anuragjha
 *
 */
public class EventService {

	public static void main(String[] args) {
		Pro4Server server = new Pro4Server(7071);

		/*
		POST /create
		GET /list
		GET /{eventid}
		POST /purchase/{eventid}
		 */
		server.addMapping(EventCreateServlet.class, "/create");
		server.addMapping(EventsListServlet.class, "/list");
		server.addMapping(EventDetailsServlet.class, "/*");
		server.addMapping(EventTicketsPurchaseServlet.class, "/purchase/*");

		server.start();

	}










}
