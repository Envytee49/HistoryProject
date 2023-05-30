package Database;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import Model.Event;
public class Events {
	public static EntityData<Event> collection = new EntityData<>();
	public static String dirName = "\\Event";
	public static void writeJSON(Event event) {
		String fileName = dirName + "\\" + event.getId() + ".json";
		JsonHelper.writeJSON(fileName, event);
	}
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
	public static void saveToJSON() {
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
}
