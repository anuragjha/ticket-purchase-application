package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cs601.project4.AppConstants;
import cs601.project4.InitJsonReader;
import cs601.project4.Project4Init;
import httpUtil.HttpConnection;

/**
 * @author anuragjha
 *
 */
public class ResponseUserServiceTest {
	
	@Before
	public void initialize() {
		Project4Init init = (Project4Init) InitJsonReader.
				project4InitJsonReader("project4Init.json", Project4Init.class);
		AppConstants.setInit(init);
	}

	//User service  - create user - correct reqBody  2 users cannot have same username
	@Test  
	public void postUserCreate() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"username\": \"newr1as23t\" }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}
	
	//User service  - create user - Wrong reqBody
	@Test  
	public void postUserCreate1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"usesrname\": \"\" }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//User service  - create user - empty param value
	@Test  
	public void postUserCreate2() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/create");

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
	
	
	
	//User service  - get a User details - user exists  
	@Test  
	public void getUserDetails() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/3");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}
	//User service  - get a User details - user exists   but has no tickets
	//                              
	@Test  
	public void getUserDetails0() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/1");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	
	//User service  - get a User details - user does not exists
	@Test  
	public void getUserDetails1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathUserService()+"/73");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}

	//user service  - correct user - correct event  - correct add  -- only affects tickets table
	@Test  
	public void postUserTicketsAdd() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/3/tickets/add");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 63, \"tickets\": 3 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//user service  - wrong user - correct event  - wrong add  -- only affects tickets table
	@Test  
	public void postUserTicketsAdd1() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/99/tickets/add");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 63, \"tickets\": 3 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//user service  - correct user - wrong event  - wrong add  -- only affects tickets table
	@Test  
	public void postUserTicketsAdd2() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/1/tickets/add");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": -999, \"tickets\": 3 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


	//user service  - correct user - correct event  - correct transfer id - correct req
	@Test  
	public void postUserTicketsTransfer() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/3/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 63, \"tickets\": 1, \"targetuser\": 7 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//user service  - correct user - correct event  - wrong transfer id - correct req
	@Test  
	public void postUserTicketsTransfer1() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/3/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 63, \"tickets\": 2, \"targetuser\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//user service  - correct user - wrong event  - correct transfer id - correct req
	@Test  
	public void postUserTicketsTransfer2() {
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/3/tickets/transfer");

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
		httpCon = new HttpConnection(
				AppConstants.getInit().getBasepathUserService()+"/333/tickets/transfer");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"eventid\": 63, \"tickets\": 2, \"targetuser\": 1 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


}



