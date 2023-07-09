package creatjson;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import crawl.*; 
import model.*;
import database.*;

public class Relation {
	public static void crawlData() {
		// Crawl all data of entities
//		CrawlFestival.crawlData();
		CrawlEvent.crawlData();
//		CrawlSite.crawlData();
//		CrawlEra.crawlData();
//		CrawlHistoricalFigure.crawlData();
	}
	// This method link Id of figure in figure to figure in relatedFigure in festival
	public static void linkCharFes() {
        ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        ArrayList<Festival> listOfFestivals = Festivals.collection.getEntityData();
        for (Festival f : listOfFestivals) {
            for (Map.Entry<String, Integer> entry : f.getRelatedFigure().entrySet()) {
                if (entry.getKey().equals("")) continue;
                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        f.setRelatedFigure(entry.getKey(), c.getId());
                        break;
                    }
                }    
            }
        }    
    }
	// This method link Id of figure in figure to figure in relatedFigure in era and vice versa
	public static void linkCharEra() {
        ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        ArrayList<Era> listOfEras = Eras.collection.getEntityData();
        // Link figure to era
        for (Era era : listOfEras) {
            for (Map.Entry<String, Integer> entry : era.getListOfKings().entrySet()) {
            	if (entry.getKey().equals("")) continue;

                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        era.setListOfKings(entry.getKey(), c.getId());
                        break;
                    }
                }    
            }
        } 
        // Link era to figure
        for(HistoricalFigure figure : listOfFigures) {
        	Set<Entry<String, Integer>> entries = figure.getEra().entrySet();
        	Entry<String, Integer> entry = entries.iterator().next();
        	if(entry.getKey().equals("Chưa rõ")) continue;
        	for(Era era : listOfEras) {
        		if(era.getName().toLowerCase().contains(entry.getKey().toLowerCase())) {
        			figure.setEra(entry.getKey(), era.getId());
        			break;
        		}
        	}
        }
	}
	// This method link Id of figure in figure to figure in relatedFigure in event
	public static void linkCharEvent() {
		HistoricalFigures.queryJSON();
        ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        Events.queryJSON();
        ArrayList<Event> listOfEvents = Events.collection.getEntityData();   
        System.out.println(listOfEvents);
        for (Event event : listOfEvents) {
            for (Map.Entry<String, Integer> entry : event.getRelatedFigure().entrySet()) {   	
                if (entry.getKey().equals("")) continue;
                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                    	System.out.println("in loop id is:"+ c.getId());
                        event.setRelatedFigure(entry.getKey(), c.getId());
                        break;
                    }
                }
                System.out.println("hello");
            }
        } 
	}
	// This method link Id of figure in figure to figure in relatedFigure in site
	public static void linkCharSite() {
		 ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
	     ArrayList<Site> listOfSites = Sites.collection.getEntityData();      
	        for (Site site : listOfSites) {
	            for (Map.Entry<String, Integer> entry : site.getRelatedFigure().entrySet()) {
	                if (entry.getKey().equals("")) continue;
	                for (HistoricalFigure c : listOfFigures) {
	                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
	                    	site.setRelatedFigure(entry.getKey(), c.getId());
	                        break;
	                    }
	                }    
	            }
	        } 
	}
	// This help with map to getKey
	public static String getEntryName(Set<Entry<String, Integer>> entrySet) {
	    Entry<String, Integer> entry = entrySet.iterator().next();
	    return entry.getKey();
	}
	// This method link Id of figure in figure to figure in father, mother, precededBy, succeededBy in HistoricalFigure
	public static void linkCharChar() {
		
		ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        for (HistoricalFigure figure : listOfFigures) {
        	
        	String fn = getEntryName(figure.getFather().entrySet());
        	String mn = getEntryName(figure.getMother().entrySet());
        	String sn = getEntryName(figure.getSucceededBy().entrySet());
        	String pn = getEntryName(figure.getPrecededBy().entrySet());
        	// Remove ( from string
            if (fn.contains("(") &&fn.lastIndexOf("(") > 0) {
            	fn = fn.substring(0, fn.lastIndexOf("(")).trim();
            }

            if ( mn.contains("(") && mn.lastIndexOf("(") > 0) {
            	mn = mn.substring(0, mn.lastIndexOf("(")).trim();
            }

            if (pn.contains("(") &&pn.lastIndexOf("(") > 0) {
            	pn = pn.substring(0, pn.lastIndexOf("(")).trim();
            }

            if (sn.contains("(") &&sn.lastIndexOf("(") > 0) {
            	sn = sn.substring(0, sn.lastIndexOf("(")).trim();
            }
            // Link figure to father, mother, succeededBy, precededBy
            for (HistoricalFigure figureToLink : listOfFigures) {
            	if(!fn.equals("Chưa rõ") && figureToLink.getName().equalsIgnoreCase(fn)) {
            		figure.setFather(fn, figureToLink.getId());
            	}
            	else if (!mn.equals("Chưa rõ") && figureToLink.getName().equalsIgnoreCase(mn)) {
            		figure.setMother(mn, figureToLink.getId());
            	}
            	else if (!pn.equals("Chưa rõ") && figureToLink.getName().equalsIgnoreCase(pn)) {
            		figure.setPrecededBy(pn, figureToLink.getId());
            	}
				else if (!sn.equals("Chưa rõ") && figureToLink.getName().equalsIgnoreCase(sn)) {
					figure.setSucceededBy(sn, figureToLink.getId());
				}
            }
	}
}
	public static void createRelation() {
		// Step 1
		// This part create relation by setting the 'value' as 'key'
		// ... implementation
		linkCharEra();
		linkCharEvent();
		linkCharFes();
		linkCharChar();
		linkCharSite();
		// Step 2
		// Save entities data to JSON files
		
		// Save entities data to JSON files
		
		Eras.saveToJSON();
		HistoricalFigures.saveToJSON();
		Festivals.saveToJSON();	
		Events.saveToJSON();
		Sites.saveToJSON();
}
}

