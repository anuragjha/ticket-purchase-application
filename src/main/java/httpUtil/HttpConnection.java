/**
 * 
 */
package httpUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author anuragjha
 *
 */
public class HttpConnection {

	private HttpURLConnection conn;

	
	

	/**
	 * @throws IOException 
	 * 
	 */
	public HttpConnection(String urlString) {

		try {
			URL url = new URL(urlString);
			this.conn = (HttpURLConnection)url.openConnection();
			

		} catch (MalformedURLException e) {
			System.out.println("URL not in correct format");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("URL IO error");
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @return the conn
	 */
	public HttpURLConnection getConn() {
		return conn;
	}


	public void setRequestMethod(String method) {
		try {
			this.conn.setRequestMethod(method);
		} catch (ProtocolException e) {
			System.out.println("Http method not supported");
			e.printStackTrace();
		}
	}


	public void setRequestProperty(String name, String value) {
		this.conn.setRequestProperty(name, value);
	}


	public void setDoOutput(boolean value) {
		this.conn.setDoOutput(value);
	}


	public void setDoInput(boolean value) {
		this.conn.setDoInput(value);
	}


	public void connect() {
		try {
			this.conn.connect();
		} catch (IOException e) {
			System.out.println("Error while opening the connection");
			e.printStackTrace();
		}
	}


	public int readResponseCode() {
		int code = 0;
		try {
			code = this.conn.getResponseCode();
		} catch (IOException e) {
			System.out.println("Error in getting response code");
			e.printStackTrace();
		}
		return code;
	}
	
	public Map<String, List<String>> readResponseHeader() {
		Map<String, List<String>> headers = this.conn.getHeaderFields();

		for(String headerName : headers.keySet()) {
			System.out.println("hname: " + headerName + "\thvalue: " + headers.get(headerName));
		}

		return headers;
	}

	
	public void writeRequestBody(String reqBody) {
		try {
			conn.getOutputStream().write(reqBody.getBytes("UTF-8"));
			conn.getOutputStream().flush();
		} catch (IOException e) {
			System.out.println("Error in writing to Con Output Stream");
			e.printStackTrace();
		}
	}
	

	public String readResponseBody() {
		String respBody = "";
		if(this.conn.getContentLength() > 0) {
			byte[] bytes = new byte[this.conn.getContentLength()];
			int read = 0;
			try {
				while(read < this.conn.getContentLength()) {
					read += this.conn.getInputStream().read(bytes, read, (bytes.length-read));
				}

				System.out.println("Bytes expected: " + bytes.length + " Bytes read: " + read);

				respBody = new String(bytes, StandardCharsets.UTF_8);
				System.out.println("Request Body: " + respBody);

			}catch(IOException ioe) {
				System.out.println("Error in getting input stream");
				ioe.printStackTrace();
			}
		}
		return respBody;

	}
	
	
	public String readErrorResponseBody() {
		String respBody = "";
		if(this.conn.getContentLength() > 0) {
			byte[] bytes = new byte[this.conn.getContentLength()];
			int read = 0;
			try {
				while(read < this.conn.getContentLength()) {
					read += this.conn.getErrorStream().read(bytes, read, (bytes.length-read));
				}

				System.out.println("Bytes expected: " + bytes.length + " Bytes read: " + read);

				respBody = new String(bytes, StandardCharsets.UTF_8);
				System.out.println("Request Body: " + respBody);

			}catch(IOException ioe) {
				System.out.println("Error in getting input stream");
				ioe.printStackTrace();
			}
		}
		return respBody;

	}


	public void connectPostRequest() {
		this.setRequestMethod("POST");
		this.setDoOutput(true);
		
		this.setRequestProperty("Accept-Charset", "UTF-8");
		this.setRequestProperty("Content-Type", "application/json");

		this.connect();
		
	}
	
	public void connectGetRequest() {		
		this.setRequestMethod("GET");
		this.setRequestProperty("Accept-Charset", "UTF-8");
		this.setRequestProperty("Content-Type", "application/json");
		this.connect();
		
	}



//	public static void main(String[] args) {
//
//		String myUrl = "http://localhost:7071/list";
//		HttpConnection http;
//		http = new HttpConnection(myUrl);
//		
//		
//		http.setRequestMethod("GET");
//		http.setRequestProperty("Accept-Charset", "UTF-8");
//		http.connect();
//
//		http.readResponseHeader();
//		http.readResponseBody();
//		
//
//
//	}


}
