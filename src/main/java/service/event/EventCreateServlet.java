/**
 * 
 */
package service.event;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs601.project4.AppConstants;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.EventId;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 * EventCreateServlet class handles Event create request
 */
public class EventCreateServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";

		String[] subPaths = req.getRequestURI().split("/");

		int eventid = 0;
		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {

			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);

			if((!appParams.getEventname().equals("")) && (appParams.getNumtickets() > 0) && 
					(appParams.getUserid() > 0)) {


				eventid = this.getResult(appParams.getEventname(), appParams.getUserid(), 
						appParams.getNumtickets(), appParams.getNumtickets(), 0);

			} 

		}
		
		result = this.setResponse(resp, eventid);
		
		new HttpRespUtil().writeResponse(resp, result); 

	}
	
	/**
	 * setResponse method create response body
	 * @param resp
	 * @param eventid
	 * @return
	 */
	private String setResponse(HttpServletResponse resp, int eventid) {
		if(eventid!=0) {
			resp.setStatus(HttpServletResponse.SC_OK);
			EventId eventId = new EventId(eventid);
			return new Gson().toJson(eventId, EventId.class);

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResultEmpty errorJson = new ResultEmpty("Event unsuccessfully created");
			return new Gson().toJson(errorJson, ResultEmpty.class);
		}
		
	}


	/**
	 * getResult implements the Events create functionality
	 * @param eventname
	 * @param userid
	 * @param numtickets
	 * @param avail
	 * @param purchased
	 * @return
	 */
	private int getResult(String eventname, int userid, int numtickets, int avail, int purchased) {

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		int eventid = 0;
		if(this.checkifUserExists(userid)) {
			eventid = dbm1.eventsTableAddEntry(eventname, userid, numtickets, avail, purchased); 
		}

		dbm1.close();

		System.out.println("result : " + eventid);

		return eventid;
	}

	/**
	 * checkifUserExists checks if user is available to User Service
	 * @param userid
	 * @return
	 */
	private boolean checkifUserExists(int userid) {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/"+userid);

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		System.out.println("EventsCreateServlet : check user : "
				+ "respCode :" + httpCon.readResponseCode() +" : "+respStatus);
		if(httpCon.readResponseCode() == 200) {
			System.out.println("user exist, create event");
			return true;
		}

		return false;
	}



}
