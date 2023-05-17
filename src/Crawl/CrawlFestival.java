package Crawl;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Database.Festivals;
import Model.Festival;

public class CrawlFestival {
	public static void crawlData() {
		try {
			String URL = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
			Document doc = Jsoup.connect(URL).get();
			Elements row = doc.select("table.prettytable.wikitable tr");
			for(Element r : row) {
				String date = r.select("td:nth-of-type(1)").text();
				String location = r.select("td:nth-of-type(2)").text();
				String name = r.select("td:nth-of-type(3)").text();
				String firstTime = r.select("td:nth-of-type(4)").text();
				if (name.equals("")) continue;
				String content = r.select("td:nth-of-type(5)").text();
				String[] splitContent = content.split(",");
				ArrayList<String> relatedFigure = new ArrayList<>();
				for(String split : splitContent) {
					relatedFigure.add(split);
				}
				String note = r.select("td:nth-of-type(6)").text();
				new Festival(name, date, location, firstTime, note, relatedFigure);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		crawlData();
//		ArrayList<Festival> f = Festivals.collection.getEntityData();
//		for(int i = 0; i < f.size(); i++) {
//			System.out.println(f.get(i).getId()+" "+f.get(i).getName()+" "+
//								f.get(i).getDate()+" "+f.get(i).getLocation()+ " "+
//								f.get(i).getFirstTime()+" "+f.get(i).getNote()+" "+ f.get(i).getRelatedFigure());
//		}
//	}
}
