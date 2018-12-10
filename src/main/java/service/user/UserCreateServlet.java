/**
 * 
 */
package service.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.DatabaseManager;
import model.objects.AppParams;
import model.objects.ResultEmpty;
import model.objects.UserId;

/**
 * @author anuragjha
 * UserCreateServlet class handles create request for User Service
 */
public class UserCreateServlet extends HttpServlet {


	@Override
	protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String result = "";
		int userid = 0;

		System.out.println("in doPost of UserCreateServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getRequestURI().split("/");

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {

			AppParams appParams = new HttpReqUtil().reqParamsFromJsonBody(req);

			if(appParams.getUsername()!= null && (appParams.getUsername().length() > 0)) {

				userid = this.getResult(appParams.getUsername());
	
			} 
		}
		
		result = this.setResponse(resp, userid);
		
		new HttpRespUtil().writeResponse(resp, result); 

	}
	

	private String setResponse(HttpServletResponse resp, int userid) {
		if(userid!=0) {
			UserId userId = new UserId(userid);
			resp.setStatus(HttpServletResponse.SC_OK);
			return new Gson().toJson(userId, UserId.class);
			
		} else {
			ResultEmpty errorJson = new ResultEmpty("User unsuccessfully created");
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return new Gson().toJson(errorJson, ResultEmpty.class);
			
		}
	}




	public int getResult(String username) {

		int userid = 0;
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");


		if(dbm1.userTableIfUsernameExist(username) == 0) {
			userid = dbm1.userTableAddEntry(username);
			dbm1.close();
		}

		return userid;
	}


}
