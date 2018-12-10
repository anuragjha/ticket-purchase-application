/**
 * 
 */
package httpUtil;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author anuragjha
 * HttpRespUtil is utility class for HttpServletResponse
 */
public class HttpRespUtil {


	public void writeResponse(HttpServletResponse resp, String result) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentLength(result.length());

		try {
			
			resp.getWriter().println(result);
			resp.getWriter().flush();
		
		} catch (IOException e) {
			System.out.println("Error in writing response");
			e.printStackTrace();
		}
		
	}

}
