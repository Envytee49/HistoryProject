package helper;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * This class is used for 
 * reducing redundant codes
*/
public class JsonHelper <T>{
	// Initialize ObjectMapper to write json files
	private ObjectMapper mapper = new ObjectMapper(); 
	// This is the absolute path to the directory of the project
	private String PATH = FileSystems
            .getDefault().getPath("./").normalize()
            .toAbsolutePath() + "/json";
	
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
	// This method return an array list of type T
	public ArrayList<T> queryJSON(String dirName, Class<T> clazz) {
	    try {
	        @SuppressWarnings("resource")
	        Stream<Path> paths = Files.list(Paths.get(PATH + dirName));
	        ArrayList<T> data = (ArrayList<T>) paths.map(path -> {
	            try {
	                return mapper.<T>readValue(path.toFile(), clazz);
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
