/**
 * 
 */
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
public class ResponseEventServiceTest {


	@Before
	public void initialize() {
		Project4Init init = (Project4Init) InitJsonReader.
				project4InitJsonReader("project4Init.json", Project4Init.class);
		AppConstants.setInit(init);
	}

	//Event service  - get a event list
	@Test  
	public void getEventListTest() {  


		HttpConnection httpCon = null;

		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/list");


		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}


	//Event service  - get a event details - event exist
	@Test  
	public void getEventDetailsTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/63");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		//httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}

	//Event service  - get a event details - event does not exist
	@Test  
	public void getEventDetailsTest2() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/6430");

		httpCon.setRequestMethod("GET");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


	//Event service  - create event - correct reqBody
	@Test  
	public void postEventCreateTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"userid\": 1, \"eventname\": \"EventService111\", \"numtickets\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}


	//web service  - create event - wrong reqBody - wrong params
	@Test  
	public void postEventCreateTest1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();



		String reqBody = "{ \"usesrid\": 7, \"eventname\": \"qwerty123\", \"numtickets\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//web service  - create event - wrong reqBody  ---  --- --- ---- json syntax exception
	@Test  
	public void postEventCreateTest2() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/create");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		String reqBody = "{ \"userid\": , \"eventname\": \"qwerty123\", \"numtickets\": 99 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}


	//Event service  - purchase event - right reqBody
	@Test  
	public void getEventPurchaseTest() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/purchase/60");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{ \"userid\": 1, \"eventid\": 60, \"tickets\": 1 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readResponseBody();

		assertEquals("HTTP/1.1 200 OK", respStatus);

	}


	//web service  - purchase event - wrong reqBody - userid wrong
	@Test  
	public void getEventPurchaseTest1() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/purchase/61");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{ \"userid\": 1, \"eaventid\": 60, \"tickets\": 15 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//web service  - purchase event - wrong reqBody - eventid wrong
	@Test  
	public void getEventPurchaseTest2() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/purchase/2");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{ \"userid\": 7, \"eventid\": 661, \"tickets\": 15 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}
	//web service  - purchase event - wrong reqBody - no of tickets wrong
	@Test  
	public void getEventPurchaseTest3() {  
		HttpConnection httpCon = null;
		httpCon = new HttpConnection(AppConstants.getInit().getBasepathEventService()+"/purchase/2");

		httpCon.setRequestMethod("POST");
		httpCon.setRequestProperty("Content-Type", "application/json");

		httpCon.setDoOutput(true);

		httpCon.connect();

		//String reqBody = "{ \"userid\": 2, \"eventid\": 2, \"tickets\": 6 }";
		String reqBody = "{ \"userid\": 7, \"eventid\": 61, \"tickets\": 155 }";
		httpCon.writeRequestBody(reqBody);


		String respStatus = httpCon.readResponseHeader().get(null).get(0);
		httpCon.readErrorResponseBody();

		assertEquals("HTTP/1.1 400 Bad Request", respStatus);

	}



}
