package Database;

import java.util.ArrayList;

import Model.Festival;
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
	
	
}
