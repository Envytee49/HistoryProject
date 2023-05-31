package crawl;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Festival;

public class CrawlFestival {
	public static void crawlData() {
		try {
			String URL = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam#Danh_s%C3%A1ch_m%E1%BB%99t_s%E1%BB%91_l%E1%BB%85_h%E1%BB%99i";
			Document doc = Jsoup.connect(URL).get();
			Elements row = doc.select("table.prettytable.wikitable tr");
			for(Element r : row) {
				String date = r.select("td:nth-of-type(1)").text(); // get date
				String location = r.select("td:nth-of-type(2)").text(); // get location
				String name = r.select("td:nth-of-type(3)").text(); // get name
				String firstTime = r.select("td:nth-of-type(4)").text(); // get firstTime ~ lan dau to chuc 
				if (name.equals("")) continue; // do not crawl the first row
				String content = r.select("td:nth-of-type(5)").text(); // get content of related figures
				String[] splitContent = content.split(","); 
				ArrayList<String> relatedFigure = new ArrayList<>();
				for(String split : splitContent) {
					relatedFigure.add(split);
				}
				String noteURL= "https://vi.wikipedia.org/" + r.select("td:nth-of-type(3) a").attr("href");
				Document noteDoc = Jsoup.connect(noteURL).get();
				String note ="";
				Element noteElement = noteDoc.selectFirst("p");
				noteElement.select("sup").remove();
				note = noteElement.text().split("\\.")[0];
				if(note.contains("Trình")){
					note = r.select("td:nth-of-type(6)").text();
				}
				new Festival(name, date, location, firstTime, note, relatedFigure);	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
