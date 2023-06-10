package relation;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import crawl.*;

import model.*;
import database.*;

public class Relation {
	static Eras eras = new Eras();
	static HistoricalFigures figures = new HistoricalFigures();
	public static void crawlData() {
		// Crawl all data of entities
		CrawlFestival.crawlData();
		CrawlEvent.crawlData();
		CrawlSite.crawlData();
		CrawlEra.crawlData();
		CrawlHistoricalFigure.crawlData();
	}
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
	public static void linkCharEra() {
        ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        ArrayList<Era> listOfEras = Eras.collection.getEntityData();

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
	public static void linkCharEvent() {
        ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        ArrayList<Event> listOfEvents = Events.collection.getEntityData();    
        for (Event event : listOfEvents) {
            for (Map.Entry<String, Integer> entry : event.getRelatedFigure().entrySet()) {
                if (entry.getKey().equals("")) continue;
                for (HistoricalFigure c : listOfFigures) {
                    if (c.getName().equalsIgnoreCase(entry.getKey())) {
                        event.setRelatedFigure(entry.getKey(), c.getId());
                        break;
                    }
                }    
            }
        } 
	}
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
	public static void linkCharChar() {
		
		ArrayList<HistoricalFigure> listOfFigures = HistoricalFigures.collection.getEntityData();
        for (HistoricalFigure figure : listOfFigures) {
        	Set<Entry<String, Integer>> father = figure.getFather().entrySet();
        	Entry<String, Integer> fatherName = father.iterator().next();
        	String fn = fatherName.getKey();
        	
        	Set<Entry<String, Integer>> mother = figure.getMother().entrySet();
        	Entry<String, Integer> motherName = mother.iterator().next();
        	String mn = motherName.getKey();
        	
        	Set<Entry<String, Integer>> succeededBy = figure.getSucceededBy().entrySet();
        	Entry<String, Integer> succeededName = succeededBy.iterator().next();
        	String sn = succeededName.getKey();
        	
        	Set<Entry<String, Integer>> precededBy = figure.getPrecededBy().entrySet();
        	Entry<String, Integer> precededName = precededBy.iterator().next();
        	String pn = precededName.getKey();
        	
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
		
		//Create instances
		Festivals festivals = new Festivals();
		Events events = new Events();
		HistoricalFigures figures = new HistoricalFigures();
		Sites sites = new Sites();
		Eras eras = new Eras();
		
		// Save entities data to JSON files
		
		eras.saveToJSON();
		figures.saveToJSON();
		festivals.saveToJSON();	
		events.saveToJSON();
		sites.saveToJSON();
}
}

