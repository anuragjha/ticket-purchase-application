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
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 2) && (subPaths[1].equals("list"))) {
			//TODO : query database to get list of all events 
			
			String result = this.getResults();
			//String result = "List of all Events";
			
			resp.setStatus(HttpServletResponse.SC_OK);
			//resp.setContentType("text/html");
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
			
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
		Gson gson = new Gson();
		String resultJson = gson.toJson(eventList.getEventList(), type);
		System.out.println("result:::::: " + resultJson);
		///
//		Gson gson = new Gson();
//		String resultJson = gson.toJson(eventList, EventList.class);
//		System.out.println("result:::::: " + resultJson);
		
		
		return resultJson;
	}
	
	
	public static void main(String[] args) {
		//EventsListServlet.getResults();
	}
	

}
