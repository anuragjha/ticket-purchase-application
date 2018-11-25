/**
 * 
 */
package jsonPack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

/**
 * @author anuragjha
 *
 */
public class Proj4JsonReader {

	public Proj4JsonReader() {

	}
	
	public ReqParamsInJson reqParamsInJson(HttpServletRequest req) {
		try {
			return this.readReqParamsInJson(req);
		} catch (IOException e) {
			System.out.println("Error in reading request body");
			e.printStackTrace();
			return null;
		}
		
	}


	 private ReqParamsInJson readReqParamsInJson(HttpServletRequest req) throws IOException {
		//////////
		// this.getHttpBody(req);
		
		//////////
		Gson gson = new Gson();
		ReqParamsInJson reqParams = gson.fromJson(this.httpBody(req), ReqParamsInJson.class);
		
		return reqParams;
	}
	 
	 
	 public String httpBody(HttpServletRequest req) {
			return getHttpBody(req);

	 }
	 
	 private String getHttpBody(HttpServletRequest req) {
		 
		 byte[] bytes = new byte[req.getContentLength()];
			int read = 0;
			while(read < req.getContentLength()) {
				try {
					read += req.getInputStream().read(bytes, read, (bytes.length-read));
				} catch (IOException e) {
					System.out.println("Error in getting the input stream");
					e.printStackTrace();
				}
			}
			System.out.println("Bytes expected: " + bytes.length + " Bytes read: " + read);
			System.out.println("Request Body: " + new String(bytes, StandardCharsets.UTF_8));
			
			return new String(bytes, StandardCharsets.UTF_8);
		 
	 }

	

}
