package database;
import helper.JsonHelper;
import model.Event;
public class Events{
	// This is the database for the entity - aggregation
	public static EntityData<Event> collection = new EntityData<>();
	// This is an object to interact with data
	private static JsonHelper<Event> json = new JsonHelper<>();
	// Folder name
	private static String dirName = "/Event";
	// Write every object to json files
	public static void writeJSON(Object object) {
		String fileName = dirName + "/" + ((Event) object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	public static void queryJSON() {
		collection.setEntityData(json.queryJSON(dirName,Event.class));
		collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	public static void saveToJSON() {
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
}
