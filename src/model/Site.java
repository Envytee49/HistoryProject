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
	public String getLocation() {
		return location;
	}

	public String getConstructionDate() {
		return constructionDate;
	}

	public String getNote() {
		return note;
	}

	public String getCategory() {
		return category;
	}

	public String getApproved() {
		return approved;
	}

	public Map<String, Integer> getRelatedFigure() {
		return relatedFigure;
	}

	public void setRelatedFigure(String name, Integer id) {
		this.relatedFigure.put(name, id);
	}
	public Site() {}
	
	public Site(String name, String location, String constructionDate, 
			String note, String category, String approved, 
			ArrayList<String> relatedFigure){
		this.setId(Sites.collection.getId());
		this.setName(name);
		this.location = location;
		this.constructionDate = constructionDate;
		this.note = note;
		this.category = category;
		this.approved = approved;
		for(String entity : relatedFigure) {
			this.relatedFigure.put(entity, null);
		}

		// When attributes are set, then the object is add to the database
		Sites.collection.add(this); 
	}
	@Override
	public void save() {
		Sites.writeJSON(this);
	}
	
}
