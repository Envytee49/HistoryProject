package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import database.Eras;

public class Era extends HistoricalEntity {

	private String belongsToTimestamp;
	private String homeland;
	private String founder;
	private String locationOfCapital;
	private String time;
	private String overview;
	private Map<String, Integer> listOfKings;
	/* Getters */
	public String getBelongsToTimestamp() {
	    return belongsToTimestamp;
	}
	
	public String getHomeland() {
	    return homeland;
	}
	
	public String getFounder() {
	    return founder;
	}
	
	public String getLocationOfCapital() {
	    return locationOfCapital;
	}
	
	public String getTime() {
	    return time;
	}
	
	public String getOverview() {
	    return overview;
	}
	
	public Map<String, Integer> getListOfKings() {
	    return listOfKings;
	}
	
	public void setListOfKings(String name, Integer id) {
	    this.listOfKings.put(name, id);
	}	
	/* Constructors */
	public Era() {}
	public Era(
	        String name,
	        String timestamp,
	        String hometown,
	        String founder,
	        String locationOfCapital,
	        String time,
	        String overview,
	        ArrayList<String> listOfKings
	) {
		this.listOfKings =  new HashMap<>();
	    this.setName(name); 
	    this.setId(Eras.collection.getId());
	    this.belongsToTimestamp = timestamp;
	    this.homeland = hometown;
	    this.founder = founder;
	    this.locationOfCapital = locationOfCapital;
	    this.time = time;
	    this.overview = overview;
	    for (String king : listOfKings){
	        this.listOfKings.put(king, null);
	    }
	    Eras.collection.add(this);
	}
	public void save(){
	    Eras.writeJSON(this);
	}

		

}
