/**
 * 
 */
package service.web;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cs601.project4.AppConstants;
import cs601.project4.Project4Logger;
import httpUtil.HttpConnection;
import httpUtil.HttpReqUtil;
import httpUtil.HttpRespUtil;
import model.objects.AppParams;
import model.objects.PurchaseReq;
import model.objects.ResultEmpty;

/**
 * @author anuragjha
 *  EventsServicesCallerServlet class for Web Service uses Events Service
 */
public class EventsServicesCallerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("in doGET of EventsServiceServlet" + req.getRequestURI());
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

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
		
		Project4Logger.write(Level.INFO, "Request : " + req.getRequestURI(), 1);

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

	/**
	 * postPurchase method handles purchasing of ticket
	 * @param req
	 * @param resp
	 * @param eventid
	 * @param userid
	 */
	private void postPurchase(HttpServletRequest req, HttpServletResponse resp, 
			String eventid, String userid) {

		HttpReqUtil reqUtil =  new HttpReqUtil();

		AppParams appParams = reqUtil.reqParamsFromJsonBody(req);

		String myUrl = AppConstants.getInit().getBasepathEventService()+ "/purchase/" + eventid;
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);

		httpConn.connectPostRequest();

		PurchaseReq pr = new PurchaseReq(Integer.parseInt(userid),
				Integer.parseInt(eventid), appParams.getTickets());
		String newReqBody = new Gson().toJson(pr, PurchaseReq.class);
		
//		String newReqBody = "{\n 	\"userid\": "+userid+","+ "\n	\"eventid\": "+
//				eventid+","+ "\n	\"tickets\": "+appParams.getTickets()+ "\n}";

		httpConn.writeRequestBody(newReqBody);

		// writing responsebody
		if(httpConn.readResponseCode() == 200) {
			resp.setStatus(HttpServletResponse.SC_OK);
			new HttpRespUtil().writeResponse(resp, httpConn.readResponseBody());
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			new HttpRespUtil().writeResponse(resp, httpConn.readErrorResponseBody());
		}
	}


	/**
	 * postCreate method handles Event Service Create
	 * @param req
	 * @param resp
	 */
	private void postCreate(HttpServletRequest req, HttpServletResponse resp) {
		
		String httpBody = new HttpReqUtil().httpBody(req);

		String myUrl = AppConstants.getInit().getBasepathEventService()+"/create";
		HttpConnection httpConn = null;
		httpConn = new HttpConnection(myUrl);

		httpConn.connectPostRequest();

		httpConn.writeRequestBody(httpBody);

		if(httpConn.readResponseCode() == 200) {
			resp.setStatus(HttpServletResponse.SC_OK);
			new HttpRespUtil().writeResponse(resp, httpConn.readResponseBody());

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			new HttpRespUtil().writeResponse(resp, httpConn.readErrorResponseBody());

		}
	}
	

	/**
	 * getEventsList method handles request for Event List
	 * @param resp
	 */
	private void getEventsList(HttpServletResponse resp) {

		String myUrl = AppConstants.getInit().getBasepathEventService()+"/list";
		HttpConnection http = null;
		http = new HttpConnection(myUrl);

		http.connectGetRequest();
		
		//reading response
		String respBody = http.readResponseBody();

		if(respBody.length() < 5) {
			
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			ResultEmpty re = new ResultEmpty("No events found");
			respBody = new Gson().toJson(re, ResultEmpty.class); 

		} else {
			resp.setStatus(HttpServletResponse.SC_OK);

		}
		new HttpRespUtil().writeResponse(resp, respBody);
	}

	/**
	 * getEvent method handles request for a detail of a event
	 * @param resp
	 * @param eventid
	 */
	private void getEvent(HttpServletResponse resp, String eventid) {

		String myUrl = AppConstants.getInit().getBasepathEventService()+"/"+eventid;
		HttpConnection http = null;
		http = new HttpConnection(myUrl);

		http.connectGetRequest();

		if(http.readResponseCode() == 200) {	
			resp.setStatus(HttpServletResponse.SC_OK);
			new HttpRespUtil().writeResponse(resp, http.readResponseBody());

		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			new HttpRespUtil().writeResponse(resp, http.readErrorResponseBody());
		}	
	}


}
