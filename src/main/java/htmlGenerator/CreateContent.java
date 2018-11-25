/**
 * 
 */
package htmlGenerator;

import java.io.UnsupportedEncodingException;

/**
 * @author anuragjha
 *
 */
public class CreateContent {
	
	private StringBuilder responseHeader;
	private StringBuilder responseBody;
	
	
	/**
	 * constructor
	 */
	public CreateContent() {
		this.responseHeader = new StringBuilder();
		this.responseBody = new StringBuilder();
	}
	
	
	
	/**
	 * buildUserForm method builds the form to Search for Recipe
	 * @return
	 */
	public String buildUserForm() {
		//this.responseHeader.append("HTTP/1.0 200 OK\n" + "\r\n");
		this.writeHtmlHead("Search Recipes");
		this.writeHtmlBodyStart();
		this.writeUserSearchForm();
		this.writeHtmlBodyEnd();

		//getting content length
		//this.responseHeader.append("Content-Length: "+ this.getContentLength(this.responseBody.toString()) + "\n\r\n");

		//return this.responseHeader.toString() + this.responseBody.toString();
		return this.responseBody.toString();
	}
	
	
	/**
	 * getContentLength method gets the content length
	 * @param responseBody
	 * @return
	 */
	public int getContentLength(String responseBody) {
		int contentLength = 0;
		try {
			contentLength  = responseBody.getBytes("UTF-8").length;
			return contentLength;
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unable to get Content-Length");
			return contentLength;
		}

	}
	
	///////////////////////////////////////// HTML general
	/**
	 * writeHtmlHead writes HTML head
	 * @param title
	 */
	private void writeHtmlHead(String title)	{
		this.responseBody.append("<!DOCTYPE html>");
		this.responseBody.append("<html lang=\"en\">");
		this.responseBody.append("<head>");
		this.responseBody.append("<meta charset=\"utf-8\"/>");		
		this.responseBody.append("<title>" + title+ "</title>");		   		  
		this.responseBody.append("</head>");
	}
	
	/**
	 * writeHtmlBodyStart method writes HTML body start
	 */
	private void writeHtmlBodyStart()	{
		this.responseBody.append("<body>");		
		//this.responseBody.append("<div class=\"container\">");
	}
	
	/**
	 * writeReviewSearchForm method writes HTML form for Review Search
	 */
	private void writeUserSearchForm() {
		this.responseBody.append("<h3>Search for Recipes</h3>");	
		this.responseBody.append("<form action=\"/recipessearch\" method=\"post\">");	
		this.responseBody.append("<label name=\"searchTerm\" value=\"searchTerm\">Search Term</label> <br/>");	
		this.responseBody.append("<input type=\"text\" name=\"query\" /> <br/>");
		this.responseBody.append("<input type=\"submit\" value=\"Submit\" />");	    
		this.responseBody.append("</form>");

	}
	
	/**
	 * writeHtmlBodyEnd method writes HTML body end
	 */
	private void writeHtmlBodyEnd() {
		this.responseBody.append("</body>");
		this.responseBody.append("</html>");	
	}
	

}
