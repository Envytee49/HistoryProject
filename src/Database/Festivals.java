package Database;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import Model.Festival;
public class Festivals {
	public static EntityData<Festival> collection = new EntityData<>();
	public static String dirName = "\\Festival";
	public static void writeJSON(Festival festival) {
		String fileName = dirName + "\\" + festival.getId() + ".json";
		JsonHelper.writeJSON(fileName, festival);
	}
	public static void queryJSON() {
		try {
            @SuppressWarnings("resource")
			Stream<Path> paths = Files.list(Paths.get(JsonHelper.PATH + dirName));
            ArrayList<Festival> festivals = (ArrayList<Festival>) paths.map(path -> {
                try {
                    return JsonHelper.mapper.<Festival>readValue(path.toFile(), Festival.class);
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
