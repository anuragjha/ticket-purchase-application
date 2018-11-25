/**
 * 
 */
package webService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anuragjha
 *
 */
public class UsersServicesCallerServlet extends HttpServlet {

	/**
	 * 
	 */
	public UsersServicesCallerServlet() {
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGET of UsersServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 2) && subPaths[1].matches("[0-9]+")) {

			System.out.println("good good -> in get of /users/{userid}");
			//TODO: call users api -> GET /{userid}	
		} else {

			System.out.println("bad bad -> in get of /users/{userid}");
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of UsersServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		//POST /users/create

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			System.out.println("good good -> in POST /users/create or create/"); 
			//TODO : call users service api - POST /create
		
		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("tickets") && (subPaths[3].equals("transfer"))) {
			System.out.println("good good -> in POST /users/{userid}/tickets/transfer");
			//TODO: call users service api - POST /{userid}/tickets/transfer
			//TODO: call users service api - POST /{userid}/tickets/add  to update ticket table
		
		} else {
			System.out.println("bad bad in do post of UserServiceServlet");
		}




	}

}
