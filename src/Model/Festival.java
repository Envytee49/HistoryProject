package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Database.*;
public class Festival extends HistoricalEntity {
	private String date;
	private String location;
	private String firstTime;
	private String note;
	private Map<String, Integer> relatedFigure = new HashMap<>();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	public Map<String, Integer> getRelatedFigure() {
		return relatedFigure;
	}
	public void setRelatedFigure(Map<String, Integer> relatedFigure) {
		this.relatedFigure = relatedFigure;
	}
	public Festival(String name,
					String date, 
					String location, 
					String firstTime, 
					String note, 
					ArrayList<String> relatedFigure) {
		this.name = name;
		this.date = date;
		this.location = location;
		this.firstTime = firstTime;
		this.note = note;
		this.id = Festivals.collection.getId();
		for(String entity : relatedFigure) {
			this.relatedFigure.put(entity, null);
		}
		Festivals.collection.add(this);
		
	}
	public Festival() {}
	public void save() {
		Festivals.writeJSON(this);
	}
}
