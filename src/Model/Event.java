package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.*;
public class Event extends HistoricalEntity {
	private String date;
	private String location;
	private String cause;
	private String result;
	private Map<String, Integer> relatedFigure = new HashMap<>();
	private Events events = new Events();
	public String getDate() {
		return date;
	}
	public String getLocation() {
		return location;
	}
	public String getCause() {
		return cause;
	}
	public String getResult() {
		return result;
	}
	public Map<String, Integer> getRelatedFigure() {
		return relatedFigure;
	}
	public void setRelatedFigure(String name, Integer id) {
		this.relatedFigure.put(name, id);
	}
	public Event(String name,
					String date, 
					String location, 
					String cause,
					String result,  
					ArrayList<String> relatedFigure) {
		this.name = name;
		this.date = date;
		this.location = location;
		this.cause = cause;
		this.result = result;
		this.id = Events.collection.getId();
		for(String entity : relatedFigure) {
			this.relatedFigure.put(entity, null);
		}
		// When attributes are set, then the object is add to the database
		Events.collection.add(this); 
		
	}
	// Default constructor to apply ObjectMapper
	public Event() {}
	// Save to json 'this' object
	@Override
	public void save() {
		events.writeJSON(this);
	}
}
