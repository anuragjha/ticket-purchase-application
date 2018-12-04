/**
 * 
 */
package service.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;

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
			
		} else if ((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("tickets") && (subPaths[3].equals("transfer"))) {
			System.out.println("good good -> in POST /users/{userid}/tickets/transfer");
			//TODO: call users service api - POST /{userid}/tickets/transfer
			

			this.postTransfer(resp, req);

		} else {
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
		String httpBody = new HttpReqUtil().httpBody(req);
		System.out.println("httpBody in postTransfer: " + httpBody);

		String myUrl = "http://localhost:7072"+req.getPathInfo();
		HttpConnection httpConn = new HttpConnection(myUrl);
		
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
		try {
			resp.getOutputStream().println(httpConn.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	

	}


	private void postCreate(HttpServletResponse resp, String httpBody) {
		System.out.println("httpBody in postCreate: " + httpBody);

		String myUrl = "http://localhost:7072/create";
		HttpConnection httpConn = new HttpConnection(myUrl);
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
			resp.getOutputStream().println(httpConn.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	

	}


	private void getUser(HttpServletResponse resp, String userid) {
		// TODO Auto-generated method stub

		String myUrl = "http://localhost:7072/"+userid;
		HttpConnection http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.connect();

		try {
			resp.getOutputStream().println(http.readResponseHeader() + http.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}

	}






}
