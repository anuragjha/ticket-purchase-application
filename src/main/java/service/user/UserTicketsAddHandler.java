/**
 * 
 */
package service.user;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs601.project4.Project4Logger;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.Event;
import model.objects.ResultEmpty;
import model.objects.ResultOK;
import model.objects.UserId;

/**
 * @author anuragjha
 * UserTicketsAddHandler class handles User ticket add request
 */
public class UserTicketsAddHandler {

	/**
	 * handle method handles tickets add request
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of UserEventTicketsAddServlet "+ req.getRequestURI());
		
		String result = "";
		boolean isSuccess = false;

		String[] subPaths = req.getRequestURI().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) && 
				(subPaths[2].equals("tickets")) && (subPaths[3].equals("add")) ) {
			
			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);

			if((Integer.parseInt(subPaths[1]) > 0) && (appParams.getEventid() > 0) &&
					(appParams.getTickets() > 0)) {

				isSuccess = getResult((Integer.parseInt(subPaths[1])), appParams.getEventid(),
						appParams.getTickets());
			} 
		}

		result = this.setResponse(resp, isSuccess);

		new HttpRespUtil().writeResponse(resp, result); 

	}

	/**
	 * setResponse method creates response body
	 * @param resp
	 * @param isSuccess
	 * @return
	 */
	private String setResponse(HttpServletResponse resp, boolean isSuccess) {
		if(isSuccess) {
			resp.setStatus(HttpServletResponse.SC_OK);
			ResultOK ro = new ResultOK("Event tickets added");
			return new Gson().toJson(ro, ResultOK.class); 
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResultEmpty re = new ResultEmpty("Tickets could not be added"); 
			return new Gson().toJson(re, ResultEmpty.class);
		}
	}

	/**
	 * getResult implements the User tickets add functionality
	 * @param userid
	 * @param eventid
	 * @param tickets
	 * @return
	 */
	private synchronized boolean getResult(int userid, int eventid, int tickets) {
		//String resultJson = "";
		boolean correct = false;
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		boolean userExist = false;
		String user = dbm1.userTableGetEntry(userid);
		if(!user.equals("")) {
			userExist = true;
		}
		
		int ticketDone = 0;
		if(userExist) {
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
		return correct;
	}


}
