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

import cs601.project4.AppParams;
import httpUtil.HttpReqUtil;
import model.DatabaseManager;
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

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {

			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);////////  !!!!!!!! ///

			//TODO : add a event in database
			if(appParams != null) {
				
				result = this.getResult(appParams.getEventname(), appParams.getUserid(), 
						appParams.getNumtickets(), appParams.getNumtickets(), 0);
				
				if(result.contains("Event unsuccessfully created")) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

				} else {
					resp.setStatus(HttpServletResponse.SC_OK);	
					//					resp.setContentType("application/json");
					//					resp.setCharacterEncoding("UTF-8");
					//					resp.setContentLength(result.length());

				}

			} else {
				System.out.println("bad bad request");
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			}
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}




		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		resp.getWriter().println(result);
		resp.getWriter().flush();

	}


	private String getResult(String eventname, int userid, int numtickets, int avail, int purchased) {

		String resultJson = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		int eventid = dbm1.eventsTableAddEntry(eventname, userid, numtickets, avail, purchased); 
		dbm1.close();

		if(eventid!=0) {
			EventId eventId = new EventId(eventid);
			Gson gson = new Gson();
			resultJson = gson.toJson(eventId, EventId.class);
		} else {
			//result = new ResultEmpty("Event unsuccessfully created");
			ResultEmpty errorJson = new ResultEmpty("Event unsuccessfully created");
			Gson gson = new Gson();
			resultJson = gson.toJson(errorJson, ResultEmpty.class);
		}

		//Gson gson = new Gson();
		//AppParams eventid = gson.toJson(result, AppParams.class);
		System.out.println("result:::::: " + /*resultJson*/ resultJson);

		return resultJson;
	}

}
