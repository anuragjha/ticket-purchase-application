/**
 * 
 */
package service.event;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.EventList;

/**
 * @author anuragjha
 *
 */
public class EventsListServlet extends HttpServlet {

	/**
	 * 
	 */
	public EventsListServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGet of EventsListServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		
		if((subPaths.length == 2) && (subPaths[1].equals("list"))) {
			
			String result = this.getResults();
			
			resp.setStatus(HttpServletResponse.SC_OK);
			
			new HttpRespUtil().writeResponse(resp, result); 
			
		} else {
			System.out.println("bad bad request");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	
	private String getResults() {
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
