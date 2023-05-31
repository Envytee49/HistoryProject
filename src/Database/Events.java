package database;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Event;
public class Events {
	// This is the database for the entity 
	public static EntityData<Event> collection = new EntityData<>();
	// Folder name
	public static String dirName = "\\Event";
	// Write every object to json files
	public static void writeJSON(Event event) {
		String fileName = dirName + "\\" + event.getId() + ".json";
		JsonHelper.writeJSON(fileName, event);
	}
	// Query from json files back to objects 
	// add it to the database 'collection'
	public static void queryJSON() {
		try {
			@SuppressWarnings("resource")
            Stream<Path> paths = Files.list(Paths.get(JsonHelper.PATH + dirName));
            ArrayList<Event> events = (ArrayList<Event>) paths.map(path -> {
                try {
                    return JsonHelper.mapper.<Event>readValue(path.toFile(), Event.class);
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            collection.setEntityData(events);
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
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
}
