package Database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.Event;
import Model.Festival;
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
	public static void queryJSON() {
		try {
            @SuppressWarnings("resource")
			Stream<Path> paths = Files.list(Paths.get("D:\\Code\\Java\\Test\\json\\Festival\\"));
            ArrayList<Festival> festivals = (ArrayList<Festival>) paths.map(path -> {
                try {
                    return mapper.<Festival>readValue(path.toFile(), Festival.class);
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            collection.setEntityData(festivals);
            collection.sortById();
        } catch (IOException e){
            e.printStackTrace();
        }
	}
	public static void saveToJSON() {
		for(Festival fes : collection.getEntityData()) {
			fes.save();
		}
	}
}
