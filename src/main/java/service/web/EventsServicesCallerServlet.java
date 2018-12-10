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

import cs601.project4.AppConstants;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.objects.AppParams;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 *  // if request for event is correct -> then create HTTP request and send to one of the "Event Server"
 *
 */
public class EventsServicesCallerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGET of EventsServiceServlet" + req.getRequestURI());

		if((req.getPathInfo() == null) || (req.getPathInfo().equals("/"))) {

			this.getEventsList(resp);

		} else {

			String[] subPaths = req.getPathInfo().split("/");

			if((subPaths.length == 2) && (subPaths[1].matches("[0-9]+"))) {

				this.getEvent(resp, subPaths[1]);
			}
			else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);		
			}
		}

	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doPost of EventsServiceServlet" + req.getRequestURI());

		String[] subPaths = req.getPathInfo().split("/");

		if((subPaths.length == 2) && (subPaths[1].equals("create"))) {
			System.out.println("good good -> in POST /events/create or create/"); 

			this.postCreate(req, resp);

		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("purchase") && (subPaths[3].matches("[0-9]+"))  ) {

//			AppParams params = new AppParams();
			this.postPurchase(req, resp, subPaths[1], subPaths[3]);
		}
		else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);	
		}

		System.out.println("getContentLength :" + req.getContentLength());
	}


	////////////////////////////////////////////////////////


	private void postPurchase(HttpServletRequest req, HttpServletResponse resp, 
			String eventid, String userid) {
		
		HttpReqUtil reqUtil =  new HttpReqUtil();

		AppParams appParams = reqUtil.reqParamsFromJsonBody(req);

		String myUrl = AppConstants.getInit().getBasepathEventService()+ "/purchase/" + eventid;
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);

		
		
//		httpConn.setRequestMethod("POST");
//		httpConn.setDoOutput(true);
//		
//		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
//		httpConn.setRequestProperty("Content-Type", "application/json");
//
//		httpConn.connect();
		
		
		httpConn.connectPostRequest();

		String newReqBody = "{\n 	\"userid\": "+userid+","+ "\n	\"eventid\": "+
				eventid+","+ "\n	\"tickets\": "+appParams.getTickets()+ "\n}";
//		System.out.println("new Req body: \n" + newReqBody);
		
		
//		try {
//			httpConn.getConn().getOutputStream().write(newReqBody.getBytes("UTF-8")); 
//			httpConn.getConn().getOutputStream().flush();
//		} catch (IOException e1) {
//			System.out.println("Error in getting output stream");
//			e1.printStackTrace();
//		}
		
		httpConn.writeRequestBody(newReqBody);

		// writing responsebody
			resp.setContentType("application/json");
//			if(httpConn.readResponseHeader().get(null).get(0).equals("HTTP/1.1 200 OK")) {
			if(httpConn.readResponseCode() == 200) {
				resp.setStatus(HttpServletResponse.SC_OK);
//				resp.getOutputStream().println(httpConn.readResponseBody());
//				resp.getOutputStream().flush();
				
			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//				resp.getOutputStream().println(httpConn.readErrorResponseBody());
//				resp.getOutputStream().flush();
			}
			new HttpRespUtil().writeResponse(resp, httpConn.readResponseBody());
			
	


	}



	private void postCreate(HttpServletRequest req, HttpServletResponse resp) {
		String httpBody = new HttpReqUtil().httpBody(req);

		String myUrl = AppConstants.getInit().getBasepathEventService()+"/create";
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);
		
		
		httpConn.connectPostRequest();
		
		
		
//		httpConn.setDoOutput(true);
//		httpConn.setRequestMethod("POST");
//		httpConn.setRequestProperty("Accept-Charset", "UTF-8");
//		httpConn.setRequestProperty("Content-Type", "application/json");
//
//		httpConn.connect();
		httpConn.writeRequestBody(httpBody);
//		try {
//			httpConn.getConn().getOutputStream().write(httpBody.getBytes("UTF-8")); 
//			httpConn.getConn().getOutputStream().flush();
//		} catch (IOException e1) {
//			System.out.println("Error in getting output stream");
//			e1.printStackTrace();
//		}

		// response - httpConn.readResponseBody() - in post create
//		try {
			System.out.println("in read response of EventsServicesServlet");

//			String respStatus = httpConn.readResponseHeader().get(null).get(0);

//			resp.setContentType("application/json");

//			if(respStatus.contains("200")) {
			if(httpConn.readResponseCode() == 200) {
				resp.setStatus(HttpServletResponse.SC_OK);
//				resp.getOutputStream().println(httpConn.readResponseBody());
				new HttpRespUtil().writeResponse(resp, httpConn.readResponseBody());

			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				//resp.getOutputStream().println(httpConn.readErrorResponseBody());
				new HttpRespUtil().writeResponse(resp, httpConn.readErrorResponseBody());

			}


//		} catch (IOException e) {
//			System.out.println("Error in getting output stream");
//			e.printStackTrace();
//		}	

	}

	private void getEventsList(HttpServletResponse resp) {
		//		String myUrl = "http://localhost:7071/list";
		String myUrl = AppConstants.getInit().getBasepathEventService()+"/list";
		HttpConnection http = null;
		http = new HttpConnection(myUrl);

		
//		http.setRequestMethod("GET");
//		http.setRequestProperty("Accept-Charset", "UTF-8");
//		http.connect();
		
		http.connectGetRequest();


		//reading response
		String respBody = http.readResponseBody();

		resp.setContentType("application/json");
		if(respBody.length() < 5) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			ResultEmpty re = new ResultEmpty("No events found");
			respBody = new Gson().toJson(re, ResultEmpty.class);

			//	////////////////			resp.getOutputStream().println(respBody);
			new HttpRespUtil().writeResponse(resp, respBody); 

		} else {

			resp.setStatus(HttpServletResponse.SC_OK);
			//				resp.getOutputStream().println(respBody);
			new HttpRespUtil().writeResponse(resp, respBody);
		}	
	}


	private void getEvent(HttpServletResponse resp, String eventid) {
		// TODO Auto-generated method stub
		//String myUrl = "http://localhost:7071/"+eventid;
		String myUrl = AppConstants.getInit().getBasepathEventService()+"/"+eventid;
		HttpConnection http = null;
		http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
//		http.setRequestMethod("GET");
//		http.setRequestProperty("Accept-Charset", "UTF-8");
//		http.setRequestProperty("Content-Type", "application/json");
//		http.connect();
		http.connectGetRequest();

//		try {  // response
			resp.setContentType("application/json");

			//String respStatus = http.readResponseHeader().get(null).get(0);

//			if(respStatus.contains("200")) {
			if(http.readResponseCode() == 200) {	
				resp.setStatus(HttpServletResponse.SC_OK);
//				resp.getOutputStream().println(http.readResponseBody());
				new HttpRespUtil().writeResponse(resp, http.readResponseBody());

			} else {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				//resp.getOutputStream().println(http.readErrorResponseBody());
				new HttpRespUtil().writeResponse(resp, http.readErrorResponseBody());

			}

			//resp.getOutputStream().println(http.readResponseBody());

//		} catch (IOException e) {
//			System.out.println("Error in getting output stream");
//			e.printStackTrace();
//		}	
	}


}
