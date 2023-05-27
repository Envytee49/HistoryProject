package Database;

import java.util.ArrayList;
import java.util.Collections;
import Model.HistoricalEntity;

public class EntityData <T extends HistoricalEntity> {
	private int count = 1;
	private ArrayList<T> entityData = new ArrayList<>();
	public int getId() {
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
	public void sortByName() {
		Collections.sort(entityData, (o1, o2) -> o1.getName().compareTo(o2.getName())); 
	}
	public void sortById() {
		Collections.sort(entityData,(o1,o2) -> o1.getId() > o2.getId() ? 1 : -1);
	}
	
	
}
