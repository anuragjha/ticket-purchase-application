/**
 * 
 */
package userService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anuragjha
 *
 */
public class UserEventTicketsAddServlet extends HttpServlet {

	/**
	 * 
	 */
	public UserEventTicketsAddServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("in doPost of UserEventTicketsAddServlet");
		
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) && 
				(subPaths[2].equals("tickets")) && (subPaths[3].equals("add")) ) {
			//TODO : update data base for transaction & tickets table 
			
			String result = "Add tickets for a user";
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
