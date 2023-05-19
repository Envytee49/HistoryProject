package Relation;
import Crawl.*;
import Database.*;
public class Relation {
	public static void crawlData() {
//		CrawlFestival.crawlData();
		CrawlEvent.crawlData();
	}
	public static void createRelation() {
//		Festivals.saveToJSON();
		Events.saveToJSON();
}
}
