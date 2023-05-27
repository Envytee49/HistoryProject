package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Database.*;
public class Event extends HistoricalEntity {
	private String date;
	private String location;
	private String cause;
	private String result;
	private Map<String, Integer> relatedFigure = new HashMap<>();
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
	public void setRelatedFigure(Map<String, Integer> relatedFigure) {
		this.relatedFigure = relatedFigure;
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
		Events.collection.add(this);
		
	}
	public Event() {}
	public void save() {
		Events.writeJSON(this);
	}
}
