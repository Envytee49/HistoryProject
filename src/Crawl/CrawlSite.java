package Crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Site;


public class CrawlSite {
	public static void crawlData() {
		String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
		
		try {
			final Document doc = Jsoup.connect(url).get();
			Elements rows = doc.select("tr");
			
			for(Element r : rows) {
				if(r.select("td:nth-of-type(1)").text().equals("")) {
					continue;
				}
				
				ArrayList<String> relatedFigure = new ArrayList<String>();
				ArrayList<String> relatedFestival = new ArrayList<String>();
				
				String name = r.select("td:nth-of-type(1)").text();
				String location = r.select("td:nth-of-type(2)").text();
				String category = r.select("td:nth-of-type(3)").text();
				String approved = r.select("td:nth-of-type(4)").text();
				String note = r.select("td:nth-of-type(5)").text();
				String constructionDate = "";
				String link = "https://vi.wikipedia.org" + r.select("td:nth-of-type(1) a").attr("href");
				Document related_doc = Jsoup.connect(link).get();
				Element par = related_doc.selectFirst("p");
				Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
				String firstPar = par.text();
				if(firstPar.contains("xây") || pattern.matcher(par.text()).find()) {
					if(firstPar.contains("thế kỷ") && firstPar.indexOf("thế kỷ") > firstPar.indexOf("xây")) {
						int i = firstPar.indexOf("thế kỷ");
						Pattern p1 = Pattern.compile("^[0-9]+$");
						if(p1.matcher(firstPar.substring(i+7, i+8)).find()) {
							constructionDate = firstPar.substring(i+7, i+8) + "00s";
						}
					}
					else if(firstPar.contains("năm") && firstPar.indexOf("năm") > firstPar.indexOf("xây")) {
						int i = firstPar.indexOf("năm");
						Pattern p1 = Pattern.compile("^[0-9]+$");
						if(p1.matcher(firstPar.substring(i+7, i+10)).find()) {
							constructionDate = firstPar.substring(i+7, i+10);
						}
					}
				}
				Element body = related_doc.body();
				Elements aTags = body.getElementsByTag("a");
				String name_regex = "^\\p{L}+\\s\\p{L}+\\s?(\\p{L}+)?$";
				Pattern pattern2 = Pattern.compile(name_regex);
				for(Element a : aTags) {
					String a_text = a.text();
					int i = body.text().indexOf(a_text);
					if(i >= 6) {
						String validString = body.text().substring(i-6, i);
						if(validString.contains("chúa") || validString.contains("vua") || validString.contains("ông") 
							|| validString.contains("bà") || validString.contains("tướng")
							&& pattern2.matcher(a_text).find())
						relatedFigure.add(a_text);
					}
				}
				String body_text = body.text();
				if(body_text.contains("lễ") || body_text.contains("hội")) {
					int id = ((body_text.indexOf("lễ")!=-1)?body_text.indexOf("lễ"):body_text.indexOf("hội"));
					if(body_text.charAt(id+2) >= 'A' && body_text.charAt(id+2) <= 'Z') {
						String fest_name = body_text.substring(id+2, id+10);
						int ending = id+2;
						int traversed = 0;
						while(traversed <= 8) {
							ending++;
							traversed++;
							if(fest_name.charAt(traversed) != ' ' && 
									( fest_name.charAt(traversed+1) > 'a' || fest_name.charAt(traversed+1) < 'z')) 
								break;
							
						}
						relatedFestival.add(body_text.substring(id+2, ending));
					}
				}
				System.out.println(name+" " + relatedFigure +" "+ relatedFestival+ " "+ constructionDate);
				new Site(name, location, constructionDate, note, category, approved, relatedFigure, relatedFestival);
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		crawlData();
	}
	
}
