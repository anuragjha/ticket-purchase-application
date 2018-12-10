/**
 * 
 */
package service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import httpUtil.HttpReqUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.ResultEmpty;
import model.objects.ResultOK;

/**
 * @author anuragjha
 *
 */
public class UserTicketsTransferHandler {

	/**
	 * 
	 */
	public UserTicketsTransferHandler() {
		// TODO Auto-generated constructor stub
	}


	protected synchronized void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";
		boolean isSuccess = false;
		
		System.out.println("in doPost of UserEventTicketsTransferServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) && 
				(subPaths[2].equals("tickets")) && (subPaths[3].equals("transfer")) ) {
			//TODO : update data base for transaction & tickets table 
			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);
			//String result = "Transfers tickets of a event from one user to otherr";
			if((Integer.parseInt(subPaths[1]) > 0) && (appParams.getEventid() > 0) &&
					(appParams.getTickets() > 0) && (appParams.getTargetuser() > 0)) {
				
				isSuccess = getResult((Integer.parseInt(subPaths[1])), appParams.getEventid(),
						appParams.getTickets(), appParams.getTargetuser());  ///method here 
			}
		}
		
		if(isSuccess) {
			ResultOK ro = new ResultOK("Event tickets transfered");
			result = new Gson().toJson(ro, ResultOK.class); 
			resp.setStatus(HttpServletResponse.SC_OK);
			System.out.println("good good request");
		} else {
			ResultEmpty re = new ResultEmpty("Tickets could not be transfered");
			result = new Gson().toJson(re, ResultEmpty.class); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println("bad bad request");
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		resp.getWriter().println(result);
		resp.getWriter().flush();

		System.out.println("good good request");


	}


	private boolean getResult(int userid, int eventid, int tickets, int targetuser) {
		boolean correct = false;
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		///////////////////////////////////////////////
		//check if user is there, check if targetuser is there ---- from USERS table
		//check if user has enough tickets for eventid so that to transfer ----- from TICKETS table
		//if all above check --- then --- delete tickets amount of rows for user
		//    --- add tickets for targetusers
		//////////////////////////////////////////////
		
		//---> to create 2 queries --
		//1. for count* //////////
		//2. for delete ///////////
		
		//int userid = dbm1.userTableAddEntry(username);
		boolean usersExistAndEnoughTickets = false;
		
		String user = dbm1.userTableGetEntry(userid); //"" or otherwise
		String targetUser = dbm1.userTableGetEntry(targetuser);
		int userTickets = dbm1.ticketsTableGetNoOfTickets(userid, eventid);
				
		if((!user.equals("")) && (!targetUser.equals("")) && ((userTickets - tickets) >=0)) {
			usersExistAndEnoughTickets = true;
		}
		//boolean eventCheck = this.checkEventsTable(eventid, tickets); //http req
		int ticketDone = 0;
		if(usersExistAndEnoughTickets) {
			
			boolean deletedTickets = dbm1.ticketsTableDeleteNoOfTickets(userid, eventid, tickets);
			
			if(deletedTickets) {
				for(int i = 1; i<= tickets; i++) {
					if(dbm1.ticketsTableAddEntry(eventid, targetuser, tickets) > 0) {
						ticketDone += 1;
					}
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
