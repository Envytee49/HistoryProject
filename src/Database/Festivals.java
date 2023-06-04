package database;
import model.Festival;
public class Festivals extends DataManipulation{
	// This is the database for the entity 
	public static EntityData<Festival> collection = new EntityData<>();
	// This is an object to interact with data
	private JsonHelper<Festival> json = new JsonHelper<>();
	// Folder name
	private String dirName = "\\Festival";
	// Write every object to json files
	@Override
	public void writeJSON(Object object) {
		String fileName = dirName + "\\" + ((Festival) object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	@Override
	public void queryJSON() {
		collection.setEntityData(json.queryJSON(dirName));
		collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	@Override
	public void saveToJSON() {
		for(Festival fes : collection.getEntityData()) {
			fes.save();
		}
	}
}
