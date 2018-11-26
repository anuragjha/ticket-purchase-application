/**
 * 
 */
package eventService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonPack.Proj4HTTPReader;
import jsonPack.ReqParamsInJson;

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
		
		//System.out.println("req body to EvenCreateServlet: " + new Proj4HTTPReader().httpBody(req));
		
		
		String[] subPaths = req.getRequestURI().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			
			ReqParamsInJson reqParams = new Proj4HTTPReader().reqParamsInJson(req);
			String respResult = "Response Result: params in webservice req body\n" 
			+ "userid: " + reqParams.getUserid() + "\n"
			+ "eventName: " + reqParams.getEventname() + "\n"
			+ "numTickets: " + reqParams.getNumtickets();
			System.out.println("respResult: " + respResult);
			
			//TODO : add a event in database
			//String result = "Create a new Event";
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(respResult.length());
			
			resp.getWriter().println(respResult);
			resp.getWriter().flush();
			
		} else {
			System.out.println("bad bad request");
		}
		
	}

}
