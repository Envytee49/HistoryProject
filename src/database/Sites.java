package database;
import model.Site;

public class Sites implements DataManipulation{
	// This is the database for the entity 
	public static EntityData<Site> collection = new EntityData<>();
	// This is an object to interact with data
	private JsonHelper<Site> json = new JsonHelper<>();
	// Folder name
	private String dirName = "\\Site";
	// Write every object to json files
	@Override
	public void writeJSON(Object object) {
		String fileName = dirName + "\\" + ((Site)object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	@Override
	public void queryJSON() {		
        collection.setEntityData(json.queryJSON(dirName,Site.class));
        collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	@Override
	public void saveToJSON() {
		for(Site site : collection.getEntityData()) {
			site.save();
		}
	}
}
