/**
 * 
 */
package eventService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			String result = "Details of an Event";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
		
		} else {
			System.out.println("bad bad -> in get /{eventid}/"); 
		}
	}

}
