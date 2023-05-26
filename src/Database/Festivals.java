package Database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import Model.Festival;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class Festivals {
	public static EntityData<Festival> collection = new EntityData<>();
	private static ObjectMapper mapper = new ObjectMapper();
	private static String PATH = "D:\\Code\\Java\\Test\\json\\Festival\\";
	public static void writeJSON(Festival festival) {
		try {
			mapper.writeValue(new File(PATH+festival.getId()+".json"), festival);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static void saveToJSON() {
		for(Festival fes : collection.getEntityData()) {
			fes.save();
		}
	}
}
