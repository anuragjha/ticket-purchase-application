/**
 * 
 */
package service.event;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cs601.project4.Project4Logger;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.EventList;

/**
 * @author anuragjha
 * EventsListServlet class handles Event List request
 */
public class EventsListServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGet of EventsListServlet "+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);
		
		if((subPaths.length == 2) && (subPaths[1].equals("list"))) {
			
			String result = this.getResults();
			
			resp.setStatus(HttpServletResponse.SC_OK);
			
			new HttpRespUtil().writeResponse(resp, result); 
			
		} else {
			System.out.println("bad bad request");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	/**
	 * getResult implements the Events List functionality
	 * @return
	 */
	private synchronized String getResults() {
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");
		
		ResultSet result = dbm1.eventsTableGetEventList();  //get event list
		EventList eventList = new EventList(result);
		dbm1.close();
		
		///https://google.github.io/gson/apidocs/com/google/gson/reflect/TypeToken.html
		Type type = new TypeToken<ArrayList<Event>>() {}.getType();
		String resultJson = new Gson().toJson(eventList.getEventList(), type);
		System.out.println("result : " + resultJson);
		
		return resultJson;
	}
	
	

}
