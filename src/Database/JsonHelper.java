package database;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * This class is used for 
 * reducing redundant codes
*/
public class JsonHelper {
	// Initialize ObjectMapper to write json files
	public static ObjectMapper mapper = new ObjectMapper(); 
	// This is the absolute path to the directory of the project
	public static String PATH = FileSystems
            .getDefault().getPath("./").normalize()
            .toAbsolutePath() + "\\json";
	
	/*
	 * writeJSON takes 2 arguments fileName : the name of the file 
	 * object : the object such as Era, Event, Festival,..
	 */
	public static void writeJSON(String fileName, Object object) {
		try { // PATH + fileName : D:\Code\Java\Test\json\Festival\1.json
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
