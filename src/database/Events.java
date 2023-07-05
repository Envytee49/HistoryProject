package database;
import model.Event;
public class Events implements DataManipulation{
	// This is the database for the entity - aggregation
	public static EntityData<Event> collection = new EntityData<>();
	// This is an object to interact with data
	private JsonHelper<Event> json = new JsonHelper<>();
	// Folder name
	private String dirName = "/Event";
	// Write every object to json files
	@Override
	public void writeJSON(Object object) {
		String fileName = dirName + "/" + ((Event) object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	@Override
	public void queryJSON() {
		collection.setEntityData(json.queryJSON(dirName,Event.class));
		collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	@Override
	public void saveToJSON() {
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
}
