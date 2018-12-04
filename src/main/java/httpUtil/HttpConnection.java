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

	//private URL url;
	private HttpURLConnection conn;

	
	

	/**
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
			System.out.println("Unable to create connection");
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


	public Map<String, List<String>> readResponseHeader() {
		Map<String, List<String>> headers = this.conn.getHeaderFields();

		for(String headerName : headers.keySet()) {
			System.out.println("hname: " + headerName + "\thvalue: " + headers.get(headerName));
		}

		return headers;
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
			}
		}
		return respBody;

	}


	public static void main(String[] args) {

		String myUrl = "http://localhost:7071/list";
		HttpConnection http = new HttpConnection(myUrl);
		//http.fetch(myUrl);
		http.setRequestMethod("GET");
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.connect();

		http.readResponseHeader();
		http.readResponseBody();


	}


}
