package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import Model.Festival;
import org.json.simple.JSONObject;
public class Festivals {
	public static EntityData<Festival> collection = new EntityData<>();
	public static void writeJSON(Festival festival) {
		JSONObject entity = new JSONObject();
		entity.put("id", festival.getId());
		entity.put("name", festival.getName());
		entity.put("date", festival.getDate());
		entity.put("location", festival.getLocation());
		entity.put("firstTime", festival.getFirstTime());
		entity.put("note", festival.getNote());
		JSONObject relatedFigure = new JSONObject();
		for (Map.Entry<String, Integer> entry : festival.getRelatedFigure().entrySet()) {
			if(entry.getKey()=="") continue;
			relatedFigure.put(entry.getKey(), entry.getValue());
		}
		entity.put("relatedFigure", relatedFigure);
		
		try {
	         FileWriter file = new FileWriter("D:\\Code\\Java\\Test\\json\\Festival\\"+festival.getId()+".json");
	         file.write(entity.toJSONString().replace("\\",""));
	         file.close();
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
