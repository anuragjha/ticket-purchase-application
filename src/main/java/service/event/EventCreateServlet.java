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

import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.EventId;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 *
 */
public class EventCreateServlet extends HttpServlet {

	/**
	 * 
	 */
	public EventCreateServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";

		System.out.println("in doPost of EventCreateServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());
		//System.out.println("req body to EvenCreateServlet: " + new Proj4HTTPReader().httpBody(req));

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		int eventid = 0;
		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {

			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);////////  !!!!!!!! ///

			//TODO : add a event in database
			if((appParams.getEventname() != null) && (appParams.getNumtickets() > 0) && 
					(appParams.getUserid() > 0)) {


				eventid = this.getResult(appParams.getEventname(), appParams.getUserid(), 
						appParams.getNumtickets(), appParams.getNumtickets(), 0);

			} 

		}

		if(eventid!=0) {
			resp.setStatus(HttpServletResponse.SC_OK);
			EventId eventId = new EventId(eventid);
			result = new Gson().toJson(eventId, EventId.class);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			//result = new ResultEmpty("Event unsuccessfully created");
			ResultEmpty errorJson = new ResultEmpty("Event unsuccessfully created");
			result = new Gson().toJson(errorJson, ResultEmpty.class);
		}

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		resp.getWriter().println(result);
		resp.getWriter().flush();

	}



	private int getResult(String eventname, int userid, int numtickets, int avail, int purchased) {

		boolean isSuccess = false;
		String resultJson = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		int eventid = 0;
		if(this.checkifUserExists(userid)) {
			eventid = dbm1.eventsTableAddEntry(eventname, userid, numtickets, avail, purchased); 
		}

		dbm1.close();

		System.out.println("result:::::: " + /*resultJson*/ eventid);

		return eventid;
	}

	private boolean checkifUserExists(int userid) {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection("http://localhost:7072/"+userid);

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
