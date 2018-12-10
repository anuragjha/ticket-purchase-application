/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import cs601.project4.AppConstants;
import cs601.project4.InitJsonReader;
import cs601.project4.Project4Init;
import httpUtil.HttpConnection;
import model.objects.AppParams;

/**
 * @author anuragjha
 *
 */
public class ResponseWebServiceTest {

	@Before
	public void initialize() {
		Project4Init init = (Project4Init) InitJsonReader.
				project4InitJsonReader("project4Init.json", Project4Init.class);
		AppConstants.setInit(init);
	}
	
	
	//web service  - get event list
	@Test
	public void getEventsTest() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/events");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//web service  - create event - correct reqBody
	@Test 
	public void getEventCreateTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/events/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"userid\": 1, \"eventname\": \"qwerty12\", \"numtickets\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//web service  - create event - wrong reqBody
	@Test  
	public void postEventCreateTest1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/events/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();


		String reqBody = "{ \"userid\": 1, \"eventnAAAAame\": \"qwerty12\", \"numtickets\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


	//web service  - get a event details - event exists
	@Test  
	public void getEventDetailsTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/events/61");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}
	//web service  - get a event details - event does not exist
	@Test  
	public void getEventDetailsTest1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/events/2121");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}



	//web service  - purchase event - right reqBody
	@Test  
	public void getEventPurchaseTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathWebService()+"/events/63/purchase/7");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{\"tickets\": 6}";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//web service  - purchase event - wrong reqBody
	@Test  
	public void getEventPurchaseTest1() {  
		HttpConnection httpCon = null;
		System.out.println("path : " + AppConstants.getInit().getBasepathWebService()+"/events/60/purchase/2");
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathWebService()+"/events/60/purchase/2");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{\"ticketsss\": 6}";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}

	////////////////////
	//Web service  - create user - correct reqBody  2 users cannot have same username
	@Test  
	public void postUserCreate() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"username\": \"Supermsdan1111\" }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//User service  - create user - Wrong reqBody
	@Test  
	public void postUserCreate1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"usesrname\": \"asd\" }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//User service  - create user - empty param value
	@Test  
	public void postUserCreate2() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"username\": \"\" }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


	//web service  - user details - correct reqBody
	@Test  
	public void getUserDetails() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/"+3);

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");
		//httpCon.setDoOutput(true);

		httpCon.connect();


		int respcode = httpCon.readResponseCode();
		httpCon.readResponseBody();

		assertEquals(200, respcode);

	}

	//web service  - user details - wrong userid
	@Test  
	public void getUserDetails1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/"+77);

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");
		//httpCon.setDoOutput(true);

		httpCon.connect();


		int respcode = httpCon.readResponseCode();
		httpCon.readErrorResponseBody();

		assertEquals(400, respcode);

	}
	//Web service  - get a User details - user exists   but has no tickets                             
	@Test  
	public void getUserDetails0() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/43");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	
	//user service  - correct user - correct event  - correct transfer id - correct req
	@Test  
	public void postUserTicketsTransfer() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathWebService()+"/users/2/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 60, \"tickets\": 1, \"targetuser\": 6 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	
	//Web service  - correct user - correct event  - wrong transfer id - correct req
	@Test  
	public void postUserTicketsTransfer1() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathWebService()+"/users/6/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 62, \"tickets\": 2, \"targetuser\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//Web service  - correct user - wrong event  - correct transfer id - correct req
	@Test  
	public void postUserTicketsTransfer2() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathWebService()+"/users/6/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 633, \"tickets\": 2, \"targetuser\": 1 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//user service  - wrong user - correct event  - correct transfer id - correct req
	@Test  
	public void postUserTicketsTransfer3() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathWebService()+"/users/654/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 62, \"tickets\": 2, \"targetuser\": 1 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}

	
	
	
	
}
