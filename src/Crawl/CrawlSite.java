package Crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlSite {
	public static void crawlData() {
		String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
		try {
			Document doc = Jsoup.connect(url).get();
//			Element tab1 = doc.select("table[class^=wikitable sortable]").first();
			Element tab1 = doc.select("table.wikitable.sortable").first();
			Elements row = tab1.select("tr");
			
			System.out.println(row.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		crawlData();
	}
	
}
