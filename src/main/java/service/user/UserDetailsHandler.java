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

import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.ResultEmpty;
import model.objects.User;

/**
 * @author anuragjha
 *
 */
public class UserDetailsHandler {

	
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
			result = this.getResult(resp, Integer.parseInt(subPaths[1]));
			
			if(result.equals("{\"error\":\"User not found\"}")) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			} else {
				resp.setStatus(HttpServletResponse.SC_OK);
			}
			
			new HttpRespUtil().writeResponse(resp, result); 
		
		} else {
			System.out.println("bad bad -> in get /{userid}"); 
		}
		
	}


	private String getResult(HttpServletResponse resp, int userid) {
		String resultJson  = "";

		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");
		System.out.println("User id: " + userid);
		
		String username = dbm1.userTableGetEntry(userid);
		
		resultJson = this.setResponse(resp, dbm1, userid, username);
		
		
		dbm1.close();

		return resultJson;
	}


	private String setResponse(HttpServletResponse resp, DatabaseManager dbm1, int userid, String username) {
		if(username.equals("")) {
			System.out.println("User not found");
			ResultEmpty errorJson = new ResultEmpty("User not found");
			return new Gson().toJson(errorJson, ResultEmpty.class);
		} else {
			User user = new User(userid, username);
			ResultSet result = dbm1.ticketsTableGetEventidForUser(userid); 		  //get events for user
			user.setTickets(result);
			return new Gson().toJson(user, User.class);
		}
	}


}
