/**
 * 
 */
package webService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jsonPack.Proj4JsonReader;
import jsonPack.ReqParamsInJson;

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
			
			////////this.postCreate(resp);
		
		} else if((subPaths.length == 4) && (subPaths[1].matches("[0-9]+")) 
				&& subPaths[2].equals("purchase") && (subPaths[3].matches("[0-9]+"))  ) {

			System.out.println("good good -> in POST /events/{eventid}/purchase/{userid}");
			//TODO: call event service api - POST /purchase/{eventid}
			//TODO: call users service api - POST /{userid}/tickets/add 
		}
		else {

			System.out.println("bad bad in do post of EventServiceServlet");
		}



		System.out.println("getContentLength :" + req.getContentLength());
		if(req.getContentLength() > 0) {
			ReqParamsInJson reqParams = new Proj4JsonReader().reqParamsInJson(req);
			System.out.println("reqParams: " + reqParams.getTickets());
		}
	}
	
	
	////////////////////////////////////////////////////////
	
	
	private void getEventsList(HttpServletResponse resp) {
		String myUrl = "http://localhost:7071/list";
		HTTPFetcher http = new HTTPFetcher(myUrl);
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
	
	
	private void getEvent(HttpServletResponse resp, String eventid) {
		// TODO Auto-generated method stub
		String myUrl = "http://localhost:7071/"+eventid;
		HTTPFetcher http = new HTTPFetcher(myUrl);
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
