/**
 * 
 */
package eventService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

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
			String result = "List of all Events";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
			
		} else {
			System.out.println("bad bad request");
		}
	}

}
