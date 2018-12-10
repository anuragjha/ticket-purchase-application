/**
 * 
 */
package service.user;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DatabaseManager;
import model.objects.Event;
import model.objects.ResultEmpty;
import model.objects.User;

/**
 * @author anuragjha
 *
 */
public class UserDetailsHandler {

	/**
	 * 
	 */
	public UserDetailsHandler() {
		// TODO Auto-generated constructor stub
	}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String result = "";
		
		System.out.println("in doGet of UserDetailsServlet");
		
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}
		
		if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+")) && 
				(Integer.parseInt(subPaths[1]) > 0)) {
			System.out.println("good good -> in get /{userid}/: /"+subPaths[1]); 
			//TODO : query data base to get details of user
			///String result = "Get details of a user";
			result = this.getResult(Integer.parseInt(subPaths[1]));
			
			if(result.equals("{\"error\":\"User not found\"}")) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
		
		} else {
			System.out.println("bad bad -> in get /{userid}"); 
		}
		
	}


	private String getResult(int userid) {
		String resultJson  = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");
		System.out.println("User id: " + userid);
		
		String username = dbm1.userTableGetEntry(userid);
		
		if(username.equals("")) {
			System.out.println("User not found");
			ResultEmpty errorJson = new ResultEmpty("User not found");
			Gson gson = new Gson();
			resultJson = gson.toJson(errorJson, ResultEmpty.class);
			System.out.println("resultJson: " + resultJson);
		} else {
			User user = new User(userid, username);
			ResultSet result = dbm1.ticketsTableGetEventidForUser(userid); 		  //get event list
			user.setTickets(result);
			Gson gson = new Gson();
			resultJson = gson.toJson(user, User.class);
			System.out.println("result:::::: " + resultJson);
		}
		
		
		dbm1.close();

		return resultJson;
	}
	
	
//	private String getResult(int userid) {
//		String resultJson  = "";
//
//		DatabaseManager dbm1 = new DatabaseManager();
//		System.out.println("Connected to database");
//		System.out.println("User id: " + userid);
//		ResultSet result = dbm1.ticketsTableGetEventidForUser(userid);  //get event list
//		
//		User user = new User(result);  ////TODO: HERE  send correct postman req
//		
//		String username = dbm1.userTableGetEntry(userid);
//		
//		user.setUsername(username);
//		dbm1.close();
//
//		if(user.getUserid() != 0) {
//			Gson gson = new Gson();
//			resultJson = gson.toJson(user, User.class);
//			System.out.println("result:::::: " + resultJson);
//
//		} else {
//			System.out.println("User not found");
//			ResultEmpty errorJson = new ResultEmpty("User not found");
//			Gson gson = new Gson();
//			resultJson = gson.toJson(errorJson, ResultEmpty.class);
//			System.out.println("resultJson: " + resultJson);
//
//		}
//
//		return resultJson;
//	}
	
	

}
