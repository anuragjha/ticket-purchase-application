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
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.ResultEmpty;
import model.objects.ResultOK;

/**
 * @author anuragjha
 *
 */
public class UserTicketsTransferHandler {


	protected synchronized void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";
		boolean isSuccess = false;

		System.out.println("in doPost of UserEventTicketsTransferServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) && 
				(subPaths[2].equals("tickets")) && (subPaths[3].equals("transfer")) ) {
			
			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);
			
			if((Integer.parseInt(subPaths[1]) > 0) && (appParams.getEventid() > 0) &&
					(appParams.getTickets() > 0) && (appParams.getTargetuser() > 0)) {

				isSuccess = getResult((Integer.parseInt(subPaths[1])), appParams.getEventid(),
						appParams.getTickets(), appParams.getTargetuser());  ///method here 
			}
		}

		result = this.setResponse(resp, isSuccess);

		new HttpRespUtil().writeResponse(resp, result); 

		System.out.println("good good request");


	}


	private String setResponse(HttpServletResponse resp, boolean isSuccess) {
		if(isSuccess) {
			resp.setStatus(HttpServletResponse.SC_OK);
			ResultOK ro = new ResultOK("Event tickets transfered");
			return new Gson().toJson(ro, ResultOK.class);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResultEmpty re = new ResultEmpty("Tickets could not be transfered");
			return new Gson().toJson(re, ResultEmpty.class);
		}
	}


	private boolean getResult(int userid, int eventid, int tickets, int targetuser) {
		boolean correct = false;
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

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
