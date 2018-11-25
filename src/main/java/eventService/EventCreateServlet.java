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
		
		System.out.println("in doPost of EventCreateServlet");
		
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			//TODO : add a event in database
			String result = "Create a new Event";
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
