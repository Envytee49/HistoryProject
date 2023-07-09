package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.*;
public class Festival extends HistoricalEntity {
	private String date;
	private String location;
	private String firstTime;
	private String note;
	private Map<String, Integer> relatedFigure;
	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public String getFirstTime() {
		return firstTime;
	}

	public String getNote() {
		return note;
	}

	public Map<String, Integer> getRelatedFigure() {
		return relatedFigure;
	}
	public void setRelatedFigure(String name, Integer id) {
		this.relatedFigure.put(name, id);
	}
	public Festival(String name,
					String date, 
					String location, 
					String firstTime, 
					String note, 
					ArrayList<String> relatedFigure) {
		this.relatedFigure = new HashMap<>();
		this.setName(name);
		this.date = date;
		this.location = location;
		this.firstTime = firstTime;
		this.note = note;
		this.setId(Festivals.collection.getId());
		for(String entity : relatedFigure) {
			this.relatedFigure.put(entity, null);
		}
		// When attributes are set, then the object is add to the database
		Festivals.collection.add(this);
		
	}
	// Default constructor to apply ObjectMapper
	public Festival() {}
	// Save to json 'this' object
	@Override
	public void save() {
		Festivals.writeJSON(this);
	}
}
