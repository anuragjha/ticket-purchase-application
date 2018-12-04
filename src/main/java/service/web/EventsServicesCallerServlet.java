/**
 * 
 */
package service.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs601.project4.AppParams;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;

/**
 * @author anuragjha
 *  // if request for event is correct -> then create HTTP request and send to one of the "Event Server"
 *
 */
public class EventsServicesCallerServlet extends HttpServlet {

	/**
	 * constructor
	 */
	public EventsServicesCallerServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGET of EventsServiceServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		if((req.getPathInfo() == null) || (req.getPathInfo().equals("/"))) {

			System.out.println("good good -> get list of all events");
			// in GET /events
			
			this.getEventsList(resp);
			

		} else {

			String[] subPaths = req.getPathInfo().split("/");
			System.out.println("length: " + subPaths.length);
			for(String path : subPaths) {
				System.out.println("subpaths: " + path);
			}


			if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {

				System.out.println("good good -> GET /events/{eventid}");
				//TODO: get specific event > GET /events/{eventid} - call events api - GET /{eventid}
				this.getEvent(resp, subPaths[1]);
			}
			else { // /76!5
				
				System.out.println("bad bad : not correct");
			}
		}

	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("in doPost of EventsServiceServlet");
		System.out.println(req.getPathInfo() +"***"+ req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");
		System.out.println("length: " + subPaths.length);
		for(String path : subPaths) {
			System.out.println("subpaths: " + path);
		}

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			System.out.println("good good -> in POST /events/create or create/"); 
			//TODO : call event service api - POST /create
			
			this.postCreate(req, resp); ///////
		
		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("purchase") && (subPaths[3].matches("[0-9]+"))  ) {

			System.out.println("good good -> in POST /events/{eventid}/purchase/{userid}");
			//TODO: call event service api - POST /purchase/{eventid}
			//TODO: call users service api - POST /{userid}/tickets/add 
			AppParams params = new AppParams();
			this.postPurchase(req, resp, subPaths[1], subPaths[3]); ///////// /// //// /// // /
		}
		else {

			System.out.println("bad bad in do post of EventServiceServlet");
		}



		System.out.println("getContentLength :" + req.getContentLength());
		//if(req.getContentLength() > 0) {
		//	ReqParamsInJson reqParams = new Proj4HTTPReader().reqParamsInJson(req);
		//	System.out.println("reqParams: " + reqParams.getTickets());
		//}
	}
	
	
	////////////////////////////////////////////////////////
	
	
	private void postPurchase(HttpServletRequest req, HttpServletResponse resp, String eventid, String userid) {
		HttpReqUtil reqUtil =  new HttpReqUtil();
		//String httpBody = reqUtil.httpBody(req);
		
		System.out.println("eventid from subpath: " + eventid);
		System.out.println("userid from subpath: " + userid);
		//System.out.println("httpBody in postPurchase: " + httpBody);
		
		//TODO: build correct path and body
		AppParams appParams = reqUtil.reqParamsFromJsonBody(req);
		System.out.println("tickets from body: " + appParams.getTickets());
		
		
		
		String myUrl = "http://localhost:7071/purchase/" + eventid;
		HttpConnection httpConn = new HttpConnection(myUrl);
		
		httpConn.setDoOutput(true);
		httpConn.setRequestMethod("POST");
		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
		httpConn.setRequestProperty("Content-Type", "application/json");
		
		httpConn.connect();
		

		String newReqBody = "{\n 	\"userid\": "+userid+","+ "\n	\"eventid\": "+
				eventid+","+ "\n	\"tickets\": "+appParams.getTickets()+ "\n}";
		System.out.println("new Req body: \n" + newReqBody);
		try {
			httpConn.getConn().getOutputStream().write(newReqBody.getBytes("UTF-8")); 
			httpConn.getConn().getOutputStream().flush();
		} catch (IOException e1) {
			System.out.println("Error in getting output stream");
			e1.printStackTrace();
		}
		
		// writing responsebody
		try {
			resp.getOutputStream().println(httpConn.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}
		
		
	}
	
	

	private void postCreate(HttpServletRequest req, HttpServletResponse resp) {
		String httpBody = new HttpReqUtil().httpBody(req);
		System.out.println("httpBody in postCreate: " + httpBody);
		
		String myUrl = "http://localhost:7071/create";
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
		
		
		// response - httpConn.readResponseBody() - in post create
		try {
			//resp.setStatus(HttpServletResponse.SC_OK);
			//resp.setContentType("application/json");
			
			
			//System.out.println("Reaching here - read response of EventsServicesServlet");
			//String responseBody = httpConn.readResponseBody();
			//resp.getWriter().println(responseBody);
			//resp.getWriter().flush();
			System.out.println("!!!!!!!!!!! here - read response of EventsServicesServlet");
			resp.getOutputStream().println(httpConn.readResponseBody()); //error here
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	
		
	}

	private void getEventsList(HttpServletResponse resp) {
		String myUrl = "http://localhost:7071/list";
		HttpConnection http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.connect();

		try {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			resp.getOutputStream().println(http.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	
	}
	
	
	private void getEvent(HttpServletResponse resp, String eventid) {
		// TODO Auto-generated method stub
		String myUrl = "http://localhost:7071/"+eventid;
		HttpConnection http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.connect();

		try {
			//resp.setStatus(HttpServletResponse.SC_OK);
			//resp.setContentType("application/json");
			resp.getOutputStream().println(http.readResponseBody());
		} catch (IOException e) {
			System.out.println("Error in getting output stream");
			e.printStackTrace();
		}	
	}


}
