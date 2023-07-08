package database;
import model.Festival;
public class Festivals{
	// This is the database for the entity 
	public static EntityData<Festival> collection = new EntityData<>();
	// This is an object to interact with data
	private static JsonHelper<Festival> json = new JsonHelper<>();
	// Folder name
	private static String dirName = "/Festival";
	// Write every object to json files
	public static void writeJSON(Object object) {
		String fileName = dirName + "/" + ((Festival) object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	public static void queryJSON() {
		collection.setEntityData(json.queryJSON(dirName, Festival.class));
		collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	public static void saveToJSON() {
		for(Festival fes : collection.getEntityData()) {
			fes.save();
		}
	}
}
