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
public class EventTicketsPurchaseServlet extends HttpServlet {

	/**
	 * 
	 */
	public EventTicketsPurchaseServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of EventTicketsPurchaseServlet");
		
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 3) && (subPaths[1].equals("purchase")) && (subPaths[2].matches("[0-9]+")) ) {
			//TODO : update data base for transaction & tickets table 
			String result = "Purchase said amount of ticket for a particular user and event";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
			System.out.println("good good request");
			
		} else {
			System.out.println("bad bad request");
		}
		
	}
	
	
}
