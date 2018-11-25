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
public class UserDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	public UserDetailsServlet() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("in doGet of UserDetailsServlet");
		
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {
			System.out.println("good good -> in get /{userid}/: /"+subPaths[1]); 
			//TODO : query data base to get details of user
			String result = "Get details of a user";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
		
		} else {
			System.out.println("bad bad -> in get /{userid}"); 
		}
		
	}

}
