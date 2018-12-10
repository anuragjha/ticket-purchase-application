/**
 * 
 */
package xsample;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import htmlGenerator.CreateContent;

/**
 * @author anuragjha
 *
 */
public class BlockingServlet extends HttpServlet {
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		System.out.println("Request for BlockingServlet : " + request.getRequestURL().toString());

		
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		response.getWriter().println("{ \"status\": \"ok\"}");
		//response.setContentType("text/html");
		//response.getWriter().println(new CreateContent().buildUserForm());
	}
	
	

}
