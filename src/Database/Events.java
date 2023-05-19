package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import Model.Event;
import org.json.simple.JSONObject;
public class Events {
	public static EntityData<Event> collection = new EntityData<>();
	public static void writeJSON(Event event) {
		JSONObject entity = new JSONObject();
		entity.put("id", event.getId());
		entity.put("name", event.getName());
		entity.put("date", event.getDate());
		entity.put("cause", event.getCause());
		entity.put("result", event.getResult());
		JSONObject relatedFigure = new JSONObject();
		for (Map.Entry<String, Integer> entry : event.getRelatedFigure().entrySet()) {
			if(entry.getKey()=="") continue;
			relatedFigure.put(entry.getKey(), entry.getValue());
		}
		entity.put("relatedFigure", relatedFigure);
		
		try {
	         FileWriter file = new FileWriter("D:\\Code\\Java\\Test\\json\\Event\\"+event.getId()+".json");
	         file.write(entity.toJSONString());
	         file.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	}
	public static void saveToJSON() {
		for(Event event : collection.getEntityData()) {
			event.save();
		}
	}
}
