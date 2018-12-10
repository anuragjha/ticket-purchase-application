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

import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 *
 */
public class EventDetailsServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String result = "";
		
		System.out.println("in doGet of EventDetailsServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+")) && 
				(Integer.parseInt(subPaths[1]) > 0)) {
			System.out.println("good good -> in get /{eventid}/: /"+subPaths[1]); 

			Event event = this.getResult(Integer.parseInt(subPaths[1]));
			
			result = this.setResponse(resp, event);
			
			new HttpRespUtil().writeResponse(resp, result); 

		} else {
			System.out.println("bad bad -> in get /{eventid}/"); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	
	
	private String setResponse(HttpServletResponse resp, Event event) {
		if((event.getEventid() != 0)) {
						
			resp.setStatus(HttpServletResponse.SC_OK);
			return new Gson().toJson(event, Event.class);	

		} else {
			ResultEmpty errorJson = new ResultEmpty("Event not found");
			
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new Gson().toJson(errorJson, ResultEmpty.class);

		}
	}



	private Event getResult(int eventid) {

		String resultJson  = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		ResultSet result = dbm1.eventsTableGetEventDetails(eventid);  //get event list

		Event event = new Event(result);
		dbm1.close();

//		if(event.getEventid() != 0) {
//			Gson gson = new Gson();
//			resultJson = new Gson().toJson(event, Event.class);
//			System.out.println("result:::::: " + resultJson);
//
//		} else {
//			System.out.println("Event not found");
//			ResultEmpty errorJson = new ResultEmpty("Event not found");
//			Gson gson = new Gson();
//			resultJson = gson.toJson(errorJson, ResultEmpty.class);
//			System.out.println("resultJson: " + resultJson);
//
//		}

		return event;
	}
	
	
//	private String getResult(int eventid) {
//
//		String resultJson  = "";
//
//		DatabaseManager dbm1 = new DatabaseManager();
//		System.out.println("Connected to database");
//
//		ResultSet result = dbm1.eventsTableGetEventDetails(eventid);  //get event list
//
//		Event event = new Event(result);
//		dbm1.close();
//
//		if(event.getEventid() != 0) {
//			Gson gson = new Gson();
//			resultJson = gson.toJson(event, Event.class);
//			System.out.println("result:::::: " + resultJson);
//
//		} else {
//			System.out.println("Event not found");
//			ResultEmpty errorJson = new ResultEmpty("Event not found");
//			Gson gson = new Gson();
//			resultJson = gson.toJson(errorJson, ResultEmpty.class);
//			System.out.println("resultJson: " + resultJson);
//
//		}
//
//		return resultJson;
//	}



}
