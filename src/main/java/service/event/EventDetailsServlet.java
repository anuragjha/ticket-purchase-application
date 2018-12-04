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

import model.DatabaseManager;
import model.objects.Event;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 *
 */
public class EventDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	public EventDetailsServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGet of EventDetailsServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {
			System.out.println("good good -> in get /{eventid}/: /"+subPaths[1]); 
			//TODO : query data base to get details of event
			//String result = "Details of an Event";
			String result = this.getResult(Integer.parseInt(subPaths[1]));
			if(!result.contains("error")) {
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.setContentLength(result.length());
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.setContentLength(result.length());
			}
			resp.getWriter().println(result);
			resp.getWriter().flush();

		} else {
			System.out.println("bad bad -> in get /{eventid}/"); 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private String getResult(int eventid) {

		String resultJson  = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");

		ResultSet result = dbm1.eventsTableGetEventDetails(eventid);  //get event list

		Event event = new Event(result);
		dbm1.close();

		if(event.getEventid() != 0) {
			Gson gson = new Gson();
			resultJson = gson.toJson(event, Event.class);
			System.out.println("result:::::: " + resultJson);

		} else {
			System.out.println("Event not found");
			ResultEmpty errorJson = new ResultEmpty("Event not found");
			Gson gson = new Gson();
			resultJson = gson.toJson(errorJson, ResultEmpty.class);
			System.out.println("resultJson: " + resultJson);

		}

		return resultJson;
	}



}
