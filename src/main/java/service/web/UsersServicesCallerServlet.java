/**
 * 
 */
package service.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import model.DatabaseManager;
import model.objects.Event;
import model.objects.Ticket;
import model.objects.User;
import model.objects.UserWs;

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

			this.getUser(resp, subPaths[1]); //////////////// /////// //// /// // /

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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

		
		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			
			//TODO: call users service api - POST /{userid}/tickets/add  to update ticket table
			this.postCreate(resp, new HttpReqUtil().httpBody(req));
			System.out.println("UserServicesCallerServlet : postCreate");
		
		} else if ((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("tickets") && (subPaths[3].equals("transfer"))) {
			System.out.println("good good -> in POST /users/{userid}/tickets/transfer");
			//TODO: call users service api - POST /{userid}/tickets/transfer
			

			this.postTransfer(resp, req);

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			System.out.println("bad bad in do post of UserServiceServlet");
		}

	}



	///////////////////////////////////////////////////////////////////

//	private void postAdd(HttpServletResponse resp, HttpServletRequest req) {
//		String httpBody = new HttpReqUtil().httpBody(req);
//		System.out.println("httpBody in postTransfer: " + httpBody);
//
//		String myUrl = "http://localhost:7072"+req.getPathInfo();
//		HttpConnection httpConn = new HttpConnection(myUrl);
//		
//		httpConn.setDoOutput(true);
//		httpConn.setRequestMethod("POST");
//		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
//		httpConn.setRequestProperty("Content-Type", "application/json");
//
//		httpConn.connect();
//		
//		try {
//			httpConn.getConn().getOutputStream().write(httpBody.getBytes("UTF-8")); 
//			httpConn.getConn().getOutputStream().flush();
//		} catch (IOException e1) {
//			System.out.println("Error in getting output stream");
//			e1.printStackTrace();
//		}
//
//
//		// response - httpConn.readResponseBody()
//		try {
//			resp.getOutputStream().println(httpConn.readResponseBody());
//		} catch (IOException e) {
//			System.out.println("Error in getting output stream");
//			e.printStackTrace();
//		}	
//
//	}
	
	
	

	private void postTransfer(HttpServletResponse resp, HttpServletRequest req) {
		
		String result = "";
		
		String httpBody = new HttpReqUtil().httpBody(req);
		System.out.println("httpBody in postTransfer: " + httpBody);

		String myUrl = "http://localhost:7072"+req.getPathInfo();
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);
		
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
		httpConn.setRequestProperty("Content-Type", "application/json");

		httpConn.connect();
		//sending request body(json)
		try {
			httpConn.getConn().getOutputStream().write(httpBody.getBytes("UTF-8")); 
			httpConn.getConn().getOutputStream().flush();
		} catch (IOException e1) {
			System.out.println("Error in getting output stream");
			e1.printStackTrace();
		}


		// response - httpConn.readResponseBody()
		//try {
			if(httpConn.readResponseCode() == 200) {
				resp.setStatus(HttpServletResponse.SC_OK);
				result = httpConn.readResponseBody();
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result = httpConn.readErrorResponseBody();
			}
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());

			try {
				resp.getWriter().println(result);
				resp.getWriter().flush();
			} catch (IOException e) {
				System.out.println("Error in writing response");
				e.printStackTrace();
			}
			
			
			
			//resp.getOutputStream().println(httpConn.readResponseBody());
//		} catch (IOException e) {
//			System.out.println("Error in getting output stream");
//			e.printStackTrace();
//		}	

	}


	private void postCreate(HttpServletResponse resp, String httpBody) {
		
		String result = "";
		
		System.out.println("httpBody in postCreate: " + httpBody);

		String myUrl = "http://localhost:7072/create";
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
		httpConn.setRequestProperty("Content-Type", "application/json");

		httpConn.connect();

		try {
			httpConn.getConn().getOutputStream().write(httpBody.getBytes("UTF-8")); 
			httpConn.getConn().getOutputStream().flush();
		} catch (IOException e1) {
			System.out.println("Error in getting output stream");
			e1.printStackTrace();
		}


		// response - httpConn.readResponseBody()
		try {
			if(httpConn.readResponseCode() == 200) {
				resp.setStatus(HttpServletResponse.SC_OK);
				result = httpConn.readResponseBody();
				
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				result = httpConn.readErrorResponseBody();
				System.out.println("error req body : " + result);
	
			}
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.setContentLength(result.length());

			resp.getWriter().println(result);
			resp.getWriter().flush();
		
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	

	}


	private void getUser(HttpServletResponse resp, String userid) {
		// TODO Auto-generated method stub

		String myUrl = "http://localhost:7072/"+userid;
		HttpConnection http = null;
		http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.setRequestProperty("Content-Type", "application/json");
		http.connect();

		try {
			String userServiceResponse; 
			resp.setContentType("application/json");
			//resp.getOutputStream().println(http.readResponseBody());
			
			if(http.readResponseCode() == 200) {
				System.out.println("in 200 ok response condition");
				////
				resp.setStatus(HttpServletResponse.SC_OK);
				userServiceResponse = http.readResponseBody();
				UserWs userWs = this.createResponseFromUserServiceResponse(userServiceResponse);
				String finalResult = new Gson().toJson(userWs, UserWs.class);
				////
				
				resp.getOutputStream().println(finalResult);
			
			} else {
				System.out.println("in 400 response condition");
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.getOutputStream().println(http.readErrorResponseBody());
			
			}
			
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}

	}


	private UserWs createResponseFromUserServiceResponse(String userServiceResponseBody) {

		User userDetails = new Gson().fromJson(userServiceResponseBody, User.class);
		System.out.println("createResponseFromUserServiceResponse: " +
				userDetails.getUserid() + " " + userDetails.getUsername());
		
		UserWs userWs = new UserWs(userDetails.getUserid(), userDetails.getUsername());//response object
		
		DatabaseManager dbm1 = new DatabaseManager();
		System.out.println("Connected to database");
		
		for(Ticket ticket : userDetails.getTickets()) {
			System.out.println("Event id: " + ticket.getEventid());
			//////
			userWs.addEvent(this.getEventDetails(ticket.getEventid()));
			//////
		}
		
		dbm1.close();
		
		return userWs;
		
	}


	private Event getEventDetails(int eventid) {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection("http://localhost:7071/"+eventid);

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String respStatus = httpCon.readResponseHeader().get(null).get(0);
		Event thisEvent = new Gson().fromJson(httpCon.readResponseBody(), Event.class);
		return thisEvent;
	}






}
