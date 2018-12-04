/**
 * 
 */
package model.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author anuragjha
 *
 */
public class EventList {

	private ArrayList<Event> eventList;
	
	/**
	 * 
	 */
	public EventList(ResultSet result) {
		eventList = new ArrayList<Event>();
		try {
			
			while(result.next()) {
				eventList.add(new Event(
						result.getInt(1), result.getString(2), result.getInt(3),
						result.getInt(4), result.getInt(5), result.getInt(6)));
			}
		} catch (SQLException e) {
			System.out.println("Error in initializing eventList object from ResultSet");
			e.printStackTrace();
		}
	}

	/**
	 * @return the eventList
	 */
	public ArrayList<Event> getEventList() {
		return eventList;
	}

	
}
