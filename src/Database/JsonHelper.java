package database;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.HistoricalEntity;
/*
 * This class is used for 
 * reducing redundant codes
*/
public class JsonHelper <T extends HistoricalEntity>{
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
	public void writeJSON(String fileName, Object object) {
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
	public ArrayList<T> queryJSON(String dirName) {
		try {
            @SuppressWarnings("resource")
			Stream<Path> paths = Files.list(Paths.get(PATH + dirName));
            ArrayList<T> data = (ArrayList<T>) paths.map(path -> {
                try {
                    return mapper.<T>readValue(path.toFile(), new TypeReference<>(){});
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            return data;
        } catch (IOException e){
            e.printStackTrace();
        }
		return null;
	}
}
