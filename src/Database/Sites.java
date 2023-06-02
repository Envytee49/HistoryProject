package Database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.Site;

public class Sites {
	// This is the database for the entity 
		public static EntityData<Site> collection = new EntityData<>();
		// Folder name
		public static String dirName = "\\Site";
		// Write every object to json files
		public static void writeJSON(Site site) {
			String fileName = dirName + "\\" + site.getId() + ".json";
			JsonHelper.writeJSON(fileName, site);
		}
		// Query from json files back to objects 
		// add it to the database 'collection'
		public static void queryJSON() {
			try {
	            @SuppressWarnings("resource")
				Stream<Path> paths = Files.list(Paths.get(JsonHelper.PATH + dirName));
	            ArrayList<Site> sites = (ArrayList<Site>) paths.map(path -> {
	                try {
	                    return JsonHelper.mapper.<Site>readValue(path.toFile(), Site.class);
	                } catch (IOException e){
	                    e.printStackTrace();
	                    return null;
	                }
	            }).collect(Collectors.toList());
	            collection.setEntityData(sites);
	            collection.sortById();
	        } catch (IOException e){
	            e.printStackTrace();
	        }
		}
		/*
		 * For every object in the 'collection' database 
		 * Save it in JSON file
		 */
		public static void saveToJSON() {
			for(Site site: collection.getEntityData()) {
				site.save();
			}
		}

}
