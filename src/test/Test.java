package test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Events;
import database.HistoricalFigures;
import model.Event;
import relation.Relation;

public class Test{

	public static void main(String[] args) {
		Events.queryJSON();
		ArrayList<Event> event = Events.collection.getEntityData();
		for (Event e : event) {
		    for (Map.Entry<String, Integer> entry : e.getRelatedFigure().entrySet()) {		
		        if (entry.getKey().contains("- Lịch sử Việt Nam") ||
		            entry.getKey().contains("- Nhân Vật Lịch Sử") ||
		            entry.getKey().contains("- Nhân Vật")) {
		            String newKey = entry.getKey().substring(0,entry.getKey().indexOf("-")).trim();
		            Integer value = entry.getValue();
		            e.getRelatedFigure().remove(entry.getKey());
		            e.getRelatedFigure().put(newKey, value);
		            System.out.println(newKey);
		        }
		    }
		}
		
		for (Event e : event) {
			Events.writeJSON(e);
		}
		Relation.createRelation();
		
		}
	
}
