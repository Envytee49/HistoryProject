package Database;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
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
	private static ObjectMapper mapper = new ObjectMapper();
	private static String PATH = "D:\\Code\\Java\\Test\\json\\Event\\";
	public static void writeJSON(Event event) {
		try {
			mapper.writeValue(new File(PATH+event.getId()+".json"), event);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void queryJSON() {
		try {
			@SuppressWarnings("resource")
            Stream<Path> paths = Files.list(Paths.get(PATH));
            ArrayList<Event> events = (ArrayList<Event>) paths.map(path -> {
                try {
                    return mapper.<Event>readValue(path.toFile(), Event.class);
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
