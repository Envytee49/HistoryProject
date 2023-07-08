package database;

import model.Era;
public class Eras {
    // This is the database for the entity - aggregation
	public static EntityData<Era> collection = new EntityData();
    // This is an object to interact with data
    private static JsonHelper<Era> json = new JsonHelper<>();
    // Folder name
    private static String DIR_NAME = "/Era";
    // Write every object to json files
        public static void writeJSON(Object object){
            String fileName = DIR_NAME + "/" + ((Era) object).getId() + ".json";
            json.writeJSON(fileName, object);
        }
	// Query from json files back to objects 
	// add it to the database 'collection'
    public static void queryJSON() {
            collection.setEntityData(json.queryJSON(DIR_NAME,Era.class));
            collection.sortById();
    }
    /*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
    public static void saveToJSON() {
        for(Era era : collection.getEntityData()) {
            era.save();
        }
    }
}
