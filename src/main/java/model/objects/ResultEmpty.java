/**
 * 
 */
package model.objects;

/**
 * @author anuragjha
 *
 */
public class ResultEmpty {

	private String error;
	
	
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}


//	/**
//	 * @param error the error to set
//	 */
//	public void setError(String error) {
//		this.error = error;
//	}


	/**
	 * 
	 */
	public ResultEmpty(String value) {
		this.error = value;
	}

}
