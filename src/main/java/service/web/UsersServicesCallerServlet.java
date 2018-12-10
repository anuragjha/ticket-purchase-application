/**
 * 
 */
package service.web;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cs601.project4.AppConstants;
import cs601.project4.Project4Logger;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.Ticket;
import model.objects.User;
import model.objects.UserWs;

/**
 * @author anuragjha
 * UsersServicesCallerServlet class for Web Service uses User Service
 */
public class UsersServicesCallerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGET of UsersServlet" +req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

		if((subPaths.length == 2) && subPaths[1].matches("[0-9]+")) {

			this.getUser(resp, subPaths[1]);

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println("bad bad -> in get of /users/{userid}");
		}

	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of UsersServlet" + req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {

			this.postCreate(resp, new HttpReqUtil().httpBody(req));

		} else if ((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("tickets") && (subPaths[3].equals("transfer"))) {

			this.postTransfer(resp, req);

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

	}

	///////////////////////////////////////////////////////////////////

	/**
	 * postTransfer handles request for transfer of tickets
	 * @param resp
	 * @param req
	 */
	private void postTransfer(HttpServletResponse resp, HttpServletRequest req) {

		String result = "";
		String httpBody = new HttpReqUtil().httpBody(req);

		String myUrl = AppConstants.getInit().getBasepathUserService()+req.getPathInfo();
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);

		httpConn.connectPostRequest();

		httpConn.writeRequestBody(httpBody);

		//reading response
		if(httpConn.readResponseCode() == 200) {
			resp.setStatus(HttpServletResponse.SC_OK);
			result = httpConn.readResponseBody();
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = httpConn.readErrorResponseBody();
		}

		new HttpRespUtil().writeResponse(resp, result);

	}

	/**
	 * postCreate method handles request for Users create
	 * @param resp
	 * @param httpBody
	 */
	private void postCreate(HttpServletResponse resp, String httpBody) {

		String result = "";

		String myUrl = AppConstants.getInit().getBasepathUserService()+"/create";
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);

		httpConn.connectPostRequest();

		httpConn.writeRequestBody(httpBody);

		//read response
		if(httpConn.readResponseCode() == 200) {
			resp.setStatus(HttpServletResponse.SC_OK);
			result = httpConn.readResponseBody();

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = httpConn.readErrorResponseBody();
		}

		new HttpRespUtil().writeResponse(resp, result);	
	}

	/**
	 * getUser handles request to get user details
	 * @param resp
	 * @param userid
	 */
	private synchronized void getUser(HttpServletResponse resp, String userid) {

		String result = "";

		String myUrl = AppConstants.getInit().getBasepathUserService()+"/"+userid;
		HttpConnection http = null;
		http = new HttpConnection(myUrl);

		http.connectGetRequest(); 

		if(http.readResponseCode() == 200) {
			resp.setStatus(HttpServletResponse.SC_OK);
			UserWs userWs = this.createResponseFromUserServiceResponse(http.readResponseBody());
			result = new Gson().toJson(userWs, UserWs.class);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			result = http.readErrorResponseBody();
		}

		new HttpRespUtil().writeResponse(resp, result);
	}

	/**
	 * createResponseFromUserServiceResponse reformats the User Service 
	 * 															response for details of a user
	 * @param userServiceResponseBody
	 * @return
	 */
	private UserWs createResponseFromUserServiceResponse(String userServiceResponseBody) {

		User userDetails = new Gson().fromJson(userServiceResponseBody, User.class);

		UserWs userWs = new UserWs(userDetails.getUserid(), userDetails.getUsername());//response object

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		for(Ticket ticket : userDetails.getTickets()) {
			userWs.addEvent(this.getEventDetails(ticket.getEventid()));
		}

		dbm1.close();

		return userWs;
	}

	/**
	 * getEventDetails method handles request for getting details of an event
	 * @param eventid
	 * @return
	 */
	private Event getEventDetails(int eventid) {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/"+eventid);

		httpCon.connectGetRequest();
		
		try {
			Event thisEvent = new Gson().fromJson(httpCon.readResponseBody(), Event.class);
			return thisEvent;
		} catch (JsonSyntaxException jse) {
			System.out.println("Error in json syntax");
			Event thisEvent = new Event(0, "", 0, 0, 0, 0);
			return thisEvent;
		}
		
	}

}
