package database;
import model.HistoricalFigure;
public class HistoricalFigures implements DataManipulation {
	public static EntityData<HistoricalFigure> collection = new EntityData<>();
	private JsonHelper<HistoricalFigure> json = new JsonHelper<>();
	private String dirName = "\\HistoricalFigures";
	// Write every object to json files
	public void writeJSON(Object object) {
		String fileName = dirName + "\\" + ((HistoricalFigure) object).getId() + ".json";
		json.writeJSON(fileName, object);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	public void queryJSON() {
        collection.setEntityData(json.queryJSON(dirName,HistoricalFigure.class));
        collection.sortById();
	}
	/*
	 * For every object in the 'collection' database 
	 * Save it in JSON file
	 */
	public void saveToJSON() {
		for(HistoricalFigure fig : collection.getEntityData()) {
			fig.save();
		}
	}
}