package relation;
import crawl.*;
import database.*;


public class Relation {
	public static void crawlData() {
		// Crawl all data of entities
		CrawlFestival.crawlData();
		CrawlEvent.crawlData();
//		CrawlSite.crawlData();
	}
	public static void createRelation() {
		// Step 1
		// This part create relation by setting the 'value' as 'key'
		// ... implementation
		
		
		// Step 2
		// Save entities data to JSON files
//		Sites.saveToJSON();
		Festivals festivals = new Festivals();
		Events events = new Events();
		
		festivals.saveToJSON();	
		events.saveToJSON();
}
}
