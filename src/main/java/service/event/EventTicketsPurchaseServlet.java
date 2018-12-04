/**
 * 
 */
package service.event;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs601.project4.AppParams;
import httpUtil.HttpReqUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.EventId;
import model.objects.ResultEmpty;
import model.objects.ResultOK;

/**
 * @author anuragjha
 *
 */
public class EventTicketsPurchaseServlet extends HttpServlet {

	private DatabaseManager dbm1;
	/**
	 * 
	 */
	public EventTicketsPurchaseServlet() {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		dbm1 = new DatabaseManager();  // close it
		dbm1.setAutoCommit(false);
		
		String result = "";

		System.out.println("in doPost of EventTicketsPurchaseServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 3) && (subPaths[1].equals("purchase")) && (subPaths[2].matches("[0-9]+")) ) {
			//TODO : update data base for transaction & tickets table 
			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);
	////////
			result = this.doPurchase(appParams.getUserid(), appParams.getEventid(), 
					appParams.getTickets());  /// /// method  !!!!!!!!
			
			System.out.println("result : " + result);
			
			if(result.equals("{\"ok\":\"Event tickets purchased\"}")) {
				resp.setStatus(HttpServletResponse.SC_OK);
				dbm1.commit();
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				dbm1.rollback();
			}

			System.out.println("good good request");

		} else {
			System.out.println("bad bad request");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}


		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		resp.getWriter().println(result);
		resp.getWriter().flush();

		
		dbm1.setAutoCommit(true);
		dbm1.close(); // closing dbm 
	}

	private String doPurchase(int userid, int eventid, int tickets) {

		String resultJson = "";
		/// create http request and read response //// /// // /



		System.out.println("Connected to database");

		boolean checkAndUpdateEventTable = this.checkAndUpdateEventTable(eventid, tickets);
		//boolean useridExist = this.checkUserid(dbm1, userid);
		if(checkAndUpdateEventTable) { //condition
			int transactionAdded = dbm1.transactionsTableAddEntry(eventid, userid, tickets, 0, 0); // add status
			int ticketid = dbm1.ticketsTableAddEntry(eventid, userid, tickets); //http request
			System.out.println("transactionAdded : " + transactionAdded + "\tticketAdded" + ticketid);

			if((transactionAdded!=0) && (ticketid!=0)) {  //condition
				ResultOK succJson = new ResultOK("Event tickets purchased");
				Gson gson = new Gson();
				resultJson = gson.toJson(succJson, ResultOK.class);

			} else {
				ResultEmpty errorJson = new ResultEmpty("Tickets could not be purchased");
				Gson gson = new Gson();
				resultJson = gson.toJson(errorJson, ResultEmpty.class);
			}

		} else {
			ResultEmpty errorJson = new ResultEmpty("Tickets could not be purchased");
			Gson gson = new Gson();
			resultJson = gson.toJson(errorJson, ResultEmpty.class);
		}




		System.out.println("result:::::: " + /*resultJson*/ resultJson);

		return resultJson;

	}

	private boolean checkAndUpdateEventTable(int eventid, int tickets) {

		ResultSet result = dbm1.eventsTableGetEventDetails(eventid);  //get event list
		Event event = new Event(result);


		System.out.println("in checkAndUpdateEventTable method - EventTicketsPurchaseServlet :");
		System.out.println(tickets+", " + eventid + ", " + event.getAvail() + ", "+ event.getEventid());
		if((tickets > 0) && (eventid > 0) && 
				(event.getEventid() == eventid) && (event.getAvail() >= tickets)) {
			int tableUpdate = dbm1.eventsTableUpdateForTickets(event.getAvail() - tickets,
					event.getPurchased() + tickets, eventid);
			
			System.out.println("Table Update : " + tableUpdate);
			if(tableUpdate > 0) {
				return true;
			}

		}
		
		return false;


	}


}
