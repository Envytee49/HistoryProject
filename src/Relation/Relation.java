package Relation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Crawl.*;
import Database.*;
import Model.Festival;
import Model.HistoricalFigure;

public class Relation {
	public static void crawlData() {
		// Crawl all data of entities
		CrawlFestival.crawlData();
		CrawlEvent.crawlData();
	}
	public static void addCharAndFesRelation() {
        // Lay nhan vat
		ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        if (listOfFigures.size() == 0) {
            CrawlHistoricalFigure.crawlData();
        }
        // Lay le hoi
        ArrayList<Festival> listOfFestivals = Festivals.collection.getEntityData();
        if (listOfFestivals.size() == 0) {
            CrawlFestival.crawlData();
        }

        for (Festival f : listOfFestivals) {
            Map<String, Integer> relatedCharList = new HashMap<>();
            for (Map.Entry<String, Integer> entry : f.getRelatedFigure().entrySet()) {
                // Duyet qua cac figures
                // Neu figures nao co ten == ten nhan vat trong relate char
                // phan le hoi thi lay id cua no
                if (entry.getKey().equals("")) continue;
                boolean found = false;
                // Tim truoc = ten that de tranh trung lap
                // Khi tim = ten khac
                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        found = true;
                        relatedCharList.put(entry.getKey(), c.getId());
                        break;
                    }
                }    
            }
            f.setRelatedFigure(relatedCharList);
        }
    }
	public static void createRelation() {
		// Step 1
		// This part create relation by setting the 'value' as 'key'
		// ... implementation
		
		// Step 2
		// Save entities data to JSON files
		Festivals.saveToJSON();
		Events.saveToJSON();
}
}
