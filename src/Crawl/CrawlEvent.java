package Crawl;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Event;

public class CrawlEvent {
	private static ArrayList<String> eventLink = new ArrayList<>();
	// This method is to crawl all links from URL 
	public static void crawlAllLink() {
		String url = "https://nguoikesu.com/tu-lieu/quan-su?filter_tag[0]=";
		try {
			Document doc = Jsoup.connect(url).get();
			String page = doc.selectFirst("p.com-content-category-blog__counter.counter.float-end.pt-3.pe-2").text();
			String[] splitPage = page.split(" ");
			int pageNum = Integer.parseInt(splitPage[splitPage.length-1]);	
			for(int i = 1; i <= pageNum; i++) {
				if(i == 1) {
					Elements linkEvent = doc.select("h2 a");
					for(Element event : linkEvent) {
						String urlEvent = "https://nguoikesu.com/" + event.attr("href");
						eventLink.add(urlEvent);
					}
				}
				else {
				url += "&start=" +(i-1)*5;
				doc = Jsoup.connect(url).get();
				Elements linkEvent = doc.select("h2 a");
				for(Element event : linkEvent) {
					String urlEvent = "https://nguoikesu.com/" + event.attr("href");
					eventLink.add(urlEvent);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void crawlEventData(String url) {
		try {
            Document doc = Jsoup.connect(url).timeout(120000).get();

            String name = "Chưa rõ"; 
            String date = "Chưa rõ"; 
            String location = "Chưa rõ"; 
            String cause = "Chưa rõ"; 
            String result = "Chưa rõ"; 
            ArrayList<String> relatedFigure = new ArrayList<>();
            // Get related figures
            Elements parList = doc.select("p"); // This returns a list of all paragraphs
            if (parList != null) {
            	for(Element para : parList) { // para is a paragraph with tag <p>
            		Elements aTagInPara = para.select("a");
            		if (aTagInPara != null) {
                        for (Element a : aTagInPara) {
                            String hrefValue = a.attr("href");
                            if (hrefValue.contains("/nhan-vat") && !hrefValue.contains("nha-")) {
                                String aTagValue = a.text();
                                if (!relatedFigure.contains(aTagValue)) {
                                    relatedFigure.add(aTagValue);
                                }
                            }
                        }
                    }
            	}	      
            }
            // Get event name
            name = doc.select("div.page-header").text().replace("\u2013", "-");
            
            // Get date, location, cause and result
            Element infoTable = doc.selectFirst("table[cellpadding=0]");
            if(infoTable != null) {
            	Elements infoRow = infoTable.select("tr");
				for(Element row : infoRow) {
					String title = row.select("td:nth-of-type(1)").text();
					row.select("sup").remove();
					if(title.equals("Thời gian")) {
						date = row.select("td:nth-of-type(2)").text().replace("\u2013", "-");
						}
					else if(title.equals("Địa điểm")) {
						location = row.select("td:nth-of-type(2)").text();
					}
					else if(title.contains("Nguyên nhân")) {
						cause = row.select("td:nth-of-type(2)").text();
					}
					else if(title.equals("Kết quả")) {
						result = row.select("td:nth-of-type(2)").text();
					}					
				}
            }     
            // if infoTable == NULL then
            
            new Event(name,date,location,cause,result,relatedFigure);
            
		}catch (IOException e) {
		e.printStackTrace();
	}
	}
	public static void crawlDataFromLink() {
		for(String url : eventLink) {
			crawlEventData(url);
		}	
	}
	public static void crawlData() {
		crawlAllLink();
		crawlDataFromLink();
	}			
}	
