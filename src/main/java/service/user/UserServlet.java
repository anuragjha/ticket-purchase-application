/**
 * 
 */
package service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author anuragjha
 *
 */
public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	public UserServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("in doGet of UserServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI() + "***" + req.getQueryString());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		///
		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {
			
			System.out.println("in doGet of UserServlet - GET /{userid} ");
			
			new UserDetailsHandler().doGet(req, resp);////////////// //////////// //////// /////// /////
		
		}
		///

	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("in doPost of UserServlet");

		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI() + "***" + req.getQueryString());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+") 
				&& (subPaths[2].equals("tickets")) && (subPaths[3].equals("add")))) {
			
			System.out.println("good good : will call add tickets api");
			new UserTicketsAddHandler().handle(req, resp);      //////////////////
			
		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+") 
				&& (subPaths[2].equals("tickets")) && (subPaths[3].equals("transfer")))) {
			
			System.out.println("good good : will call transfer tickets api");
			new UserTicketsTransferHandler().handle(req, resp);      /////////////////
			
		}

	}
	
	

}
