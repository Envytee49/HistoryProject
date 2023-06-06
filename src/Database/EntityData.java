package database;

import java.util.ArrayList;
import java.util.Collections;

import model.HistoricalEntity;

/* This is generic 
 * as to whatever class 
 * that extends HistoricalEnitity
 * is labeled as 'T'*/
public class EntityData <T extends HistoricalEntity> { 
	private int count = 1; // ID of entities
	private ArrayList<T> entityData = new ArrayList<>();
	public int getId() { // getID returns an increment id
		return count++;
	}
	public ArrayList<T> getEntityData() { 
		return entityData;
	}
	public void setEntityData(ArrayList<T> entityData) {
		this.entityData = entityData;
	}
	public void add(T entity) {
		entityData.add(entity);
	}
	public void sortByName() { // Sort object by their name
		Collections.sort(entityData, (o1, o2) -> o1.getName().compareTo(o2.getName())); 
	}
	public void sortById() { // Sort object by their id
		Collections.sort(entityData,(o1,o2) -> o1.getId() > o2.getId() ? 1 : -1);
	}
	
	
}
