/**
 * 
 */
package service.user;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.Project4Logger;

/**
 * @author anuragjha
 * UserServlet handles User Details, Tickets add and Tickets transfer request
 */
public class UserServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGet of UserServlet " + req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {
			
			System.out.println("in doGet of UserServlet - GET /{userid} ");
			
			new UserDetailsHandler().doGet(req, resp);	
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of UserServlet "+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

		if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+") 
				&& (subPaths[2].equals("tickets")) && (subPaths[3].equals("add")))) {
			
			new UserTicketsAddHandler().handle(req, resp);
			
		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+") 
				&& (subPaths[2].equals("tickets")) && (subPaths[3].equals("transfer")))) {

			new UserTicketsTransferHandler().handle(req, resp); 
			
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	

}
