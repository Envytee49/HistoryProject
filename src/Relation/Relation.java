package relation;
import crawl.*;
import database.*;


public class Relation {
	public static void crawlData() {
		// Crawl all data of entities
//		CrawlFestival.crawlData();
//		CrawlEvent.crawlData();
//		CrawlSite.crawlData();
		CrawlHistoricalFigure.crawlData();
	}
	public static void createRelation() {
		// Step 1
		// This part create relation by setting the 'value' as 'key'
		// ... implementation
		
		
		// Step 2
		// Save entities data to JSON files
		
		//Create instances
		Festivals festivals = new Festivals();
		Events events = new Events();
		HistoricalFigures figures = new HistoricalFigures();
		//Sites sites = new Sites();
		
		// Save entities data to JSON files
		figures.saveToJSON();
//		festivals.saveToJSON();	
//		events.saveToJSON();
		//sites.saveToJSON();
}
}
