package database;
import model.Era;
public class Eras implements DataManipulation {
    // This is the database for the entity - aggregation
    public static EntityData<Era> collection = new EntityData();
    // This is an object to interact with data
    private JsonHelper<Era> json = new JsonHelper<>();
    // Folder name
    public final static String DIR_NAME = "\\Era";
    // Write every object to json files
    @Override
        public void writeJSON(Object object){
            String fileName = DIR_NAME + "\\" + ((Era) object).getId() + ".json";
            json.writeJSON(fileName, object);
        }
	// Query from json files back to objects 
	// add it to the database 'collection'
    @Override
    public void queryJSON() {
            collection.setEntityData(json.queryJSON(DIR_NAME,Era.class));
            collection.sortById();
    }
    /*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
    @Override
    public void saveToJSON() {
        for(Era era : collection.getEntityData()) {
            era.save();
        }
    }
}
