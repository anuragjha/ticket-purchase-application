/**
 * 
 */
package service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.Event;
import model.objects.ResultEmpty;
import model.objects.ResultOK;
import model.objects.UserId;

/**
 * @author anuragjha
 *
 */
public class UserTicketsAddHandler {

	/**
	 * 
	 */
	public UserTicketsAddHandler() {
		// TODO Auto-generated constructor stub
	}


	protected synchronized void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";
		boolean isSuccess = false;

		System.out.println("in doPost of UserEventTicketsAddServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) && 
				(subPaths[2].equals("tickets")) && (subPaths[3].equals("add")) ) {
			//TODO : update data base for transaction & tickets table 
			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);
			//result = "Add tickets for a user";

			if((Integer.parseInt(subPaths[1]) > 0) && (appParams.getEventid() > 0) &&
					(appParams.getTickets() > 0)) {

				isSuccess = getResult((Integer.parseInt(subPaths[1])), appParams.getEventid(),
						appParams.getTickets());  ///method here 

			} 
		}
		
		if(isSuccess) {
			ResultOK ro = new ResultOK("Event tickets added");
			result = new Gson().toJson(ro, ResultOK.class); 
			resp.setStatus(HttpServletResponse.SC_OK);
			System.out.println("good good request");
		} else {
			ResultEmpty re = new ResultEmpty("Tickets could not be added");
			result = new Gson().toJson(re, ResultEmpty.class); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println("bad bad request");
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		resp.getWriter().println(result);
		resp.getWriter().flush();

	}


	private boolean getResult(int userid, int eventid, int tickets) {
		//String resultJson = "";
		boolean correct = false;
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		//int userid = dbm1.userTableAddEntry(username);
		boolean userExist = false;
		String user = dbm1.userTableGetEntry(userid); //"" or otherwise
		if(!user.equals("")) {
			userExist = true;
		}
		//boolean eventCheck = this.checkEventsTable(eventid, tickets); //http req
		int ticketDone = 0;
		if(userExist/* && eventCheck*/) {
			for(int i = 1; i<= tickets; i++) {
				if(dbm1.ticketsTableAddEntry(eventid, userid, tickets) > 0) {
					ticketDone += 1;
				}
			}
		}
		if(ticketDone == tickets) {
			correct = true;
		}

		dbm1.close();

		System.out.println("reyturn of getResult:::::: " + /*resultJson*/ ticketDone);
		
		return correct;
	}


}
