package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class HTTPFetcher {
	
	static HTTPFetcher httpFetcher = new HTTPFetcher();
	static String  requestLine = new String();
	static String contentLength = new String();
	static StringBuffer requestBody = new StringBuffer(); 
	
	
	/**
	 * @return the requestBody
	 */
	public static StringBuffer getRequestBody() {
		return requestBody;
	}

	/**
	 * @return the requestLine
	 */
	public static String getRequestLine() {
		return requestLine;
	}

	/**
	 * @return the contentLength
	 */
	public static String getContentLength() {
		return contentLength;
	}

	public static HTTPFetcher download(int PORT, String host, String method, String path, String query) {

		//StringBuffer buf = new StringBuffer();

		try (
				Socket sock = new Socket(host, PORT); //create a connection to the web server
				OutputStream out = sock.getOutputStream(); //get the output stream from socket
				InputStream instream = sock.getInputStream(); //get the input stream from socket
				//wrap the input stream to make it easier to read from
				BufferedReader reader = new BufferedReader(new InputStreamReader(instream))
				) { 

			//send request
			String request = new String();
			if(method.equals("GET")) {
				request = getRequest(host, path);
			} else if(method.equals("POST")) {
				request = postRequest(host, path, query);
			}

			out.write(request.getBytes());
			out.flush();

			String line = reader.readLine();
			requestLine = line;
			line = reader.readLine();
			contentLength = line;
			line = reader.readLine();
			//String requestBody = new String();
			while(line != null) {
				
				//System.out.println(line);
				requestBody.append(line); //append the newline stripped by readline
				line = reader.readLine();
			} 

		} catch (IOException e) {
			System.out.println("HTTPFetcher::download " + e.getMessage());
		}
		return httpFetcher;
	}

	private static String getRequest(String host, String path) {
		String request = "GET " + path + " HTTP/1.1" + "\n" //GET request
				+ "Host: " + host + "\n" //Host header required for HTTP/1.1
				+ "Connection: close\n" //make sure the server closes the connection after we fetch one page
				+ "\r\n";								
		return request;
	}

	private static String postRequest(String host, String path, String query) {
		String request = "POST " + path + " HTTP/1.1" + "\n"//POST request
				+ "Host: " + host + "\n"    //Host header required for HTTP/1.1
				+ "Content-Length: " + query.getBytes().length + "\n" 
				+ "Connection: close\n" //make sure the server closes the connection after we fetch one page
				+ "\r\n" + query;	
		System.out.println("string:\n" + request);
		return request;
	}

	public static void main(String[] args) {
		
		//System.out.println(download(8080, "localhost", "/reviewsearch"));
		//System.out.println(download(8080, "localhost", "POST" ,  "/reviewsearch", "query=harness").requestBody);
		//System.out.println(download(8080, "localhost", "POST" ,  "/find", "asin=3998899561").requestBody); //3998899561
		//System.out.println(download(9090, "localhost", "POST" ,  "/slackbot", "message= bring harness").requestBody);
	}

}