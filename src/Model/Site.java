package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.Sites;

public class Site extends HistoricalEntity{
	private String location;
	private String constructionDate;
	private String note;
	private String category;
	private String approved;
	private Map<String, Integer> relatedFigure = new HashMap<>();
	private Map<String, Integer> relatedFestival = new HashMap<>();
	private Sites sites = new Sites();
	
	public Site() {
		
	}
	
	public Site(String name, String location, String constructionDate, 
			String note, String category, String approved, 
			ArrayList<String> relatedFigure, ArrayList<String> relatedFestival){
		this.name = name;
		this.location = location;
		this.constructionDate = constructionDate;
		this.note = note;
		this.category = category;
		this.approved = approved;
		for(String entity : relatedFigure) {
			this.relatedFigure.put(entity, null);
		}
		for(String entity : relatedFestival) {
			this.relatedFestival.put(entity, null);
		}
		// When attributes are set, then the object is add to the database
		Sites.collection.add(this); 
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getConstructionDate() {
		return constructionDate;
	}

	public void setConstructionDate(String constructionDate) {
		this.constructionDate = constructionDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

	public Map<String, Integer> getRelatedFigure() {
		return relatedFigure;
	}

	public void setRelatedFigure(Map<String, Integer> relatedFigure) {
		this.relatedFigure = relatedFigure;
	}

	public Map<String, Integer> getRelatedFestival() {
		return relatedFestival;
	}

	public void setRelatedFestival(Map<String, Integer> relatedFestival) {
		this.relatedFestival = relatedFestival;
	}
	@Override
	public void save() {
		sites.writeJSON(this);
	}
	
}
