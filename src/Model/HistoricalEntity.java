package model;
import database.*;
public class HistoricalEntity {
	protected int id;
	protected String name;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public HistoricalEntity() {
		
	}
	public HistoricalEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
