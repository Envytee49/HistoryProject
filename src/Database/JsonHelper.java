package Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
	public static ObjectMapper mapper = new ObjectMapper();
	public static String PATH = FileSystems
            .getDefault().getPath("./").normalize()
            .toAbsolutePath() + "\\json";
	public static void writeJSON(String fileName, Object object) {
		try {
			mapper.writeValue(new File(PATH+ fileName), object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
