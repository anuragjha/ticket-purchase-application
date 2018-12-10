/**
 * 
 */
package httpUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import model.objects.AppParams;

/**
 * @author anuragjha
 *
 */
public class HttpReqUtil {

	public HttpReqUtil() {

	}

	public AppParams reqParamsFromJsonBody(HttpServletRequest req) {
		try {
			return this.readReqParamsFromJson(req);
		} catch (IOException e) {
			System.out.println("Error in reading request body");
			e.printStackTrace();
			return null;
		}

	}


	private AppParams readReqParamsFromJson(HttpServletRequest req) throws IOException {

		AppParams reqParams = null;
		try {
			Gson gson = new Gson();
			reqParams = gson.fromJson(this.httpBody(req), AppParams.class);
		} catch(JsonSyntaxException jse) {
			reqParams = new AppParams();
			System.out.println("Error in Json body");
		}

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
				System.out.println("HttpReqUtil:getHttpBody - Error in getting the input stream");
				e.printStackTrace();
			}
		}
		System.out.println("Bytes expected: " + bytes.length + " Bytes read: " + read);
		System.out.println("Request Body: " + new String(bytes, StandardCharsets.UTF_8));

		return new String(bytes, StandardCharsets.UTF_8);

	}

	public String[] reqSubPaths(HttpServletRequest req) {
		return req.getPathInfo().split("/");
	}
	
	
	public void sendGetReq() {
		
	}
	







}
