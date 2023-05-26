package Database;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
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
		// Get number of files in folder
		
	}
	
	public static void saveToJSON() {
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
	public static void main(String[] args) {
		queryJSON();
	}
}
