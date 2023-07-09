package crawl;
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
	// Get data from NKS
	private static ArrayList<String> getHistoricalFiguresFromNKS(
			ArrayList<String> historicalFigures, String nameEvent) {
		try {
			int commaIndex = nameEvent.length();
			for (int j = 0; j < nameEvent.length(); j++)
				if (nameEvent.charAt(j) == ',')
					commaIndex = j;
			String link = "https://www.google.com.vn/search?q=" + nameEvent.substring(0, commaIndex).trim()
					+ " nguoikesu";
			Elements elementsNKS = Jsoup.connect(link).get().select(".yuRUbf > a");
			for (Element element : elementsNKS) {
				if (element.attr("href").contains("https://nguoikesu.com/nhan-vat")) {
					for (Element elementChild : element.children()) {
						if (elementChild.outerHtml().contains("</h3>")) {
							historicalFigures.add(elementChild.text().replace("- Người Kể Sử", "")
									.replace("- NguoiKeSu.com", "").trim());
						}
					}
				}
			}
		} catch (IOException e) {

		}
		return historicalFigures;
	}

	// Get date
	private static String getDateFromHtml(String html) {
		if (html.contains("</b>")) {
			int indexEnd;
			int indexStart = 999;
			String dateToHtml = html;
			for (int i = 0; i < html.length(); i++) {
				if (html.charAt(i) == '<' && html.charAt(i + 1) == 'b')
					indexStart = i + 3;
				if (i > indexStart && html.charAt(i) == '<' && html.charAt(i + 1) == '/') {
					indexEnd = i;
					dateToHtml = html.substring(indexStart, indexEnd);
					break;
				}
			}
			return dateToHtml.trim();
		}
		return null;
	}

	// Get name
	private static String getEventFromHtml(String html) {
		if (html.contains("</a>")) {
			if (html.contains("</b>")) {
				html = html.replace(getDateFromHtml(html), "");
				html = html.replace("<b>", "");
				html = html.replace("</b>", "");
			}
			while (html.contains("<a")) {
				int indexStart = 999;
				int indexEnd;
				for (int i = 0; i < html.length(); i++) {
					if (html.charAt(i) == '<' && html.charAt(i + 1) == 'a') {
						indexStart = i;
					}
					if (i > indexStart && html.charAt(i) == '>') {
						indexEnd = i + 1;
						String subString = html.substring(indexStart, indexEnd);
						html = html.replace(subString, "");
						indexStart = 999;
					}
				}
			}
			return html.replace("</a>", "").trim();
		}
		return null;
	}

	public static void getDataFromHTML() {
		String url = "https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Elements data1 = doc.select(".mw-parser-output > p");
		Elements data2 = doc.select(".mw-parser-output > p+dl > dd");

//		 Data1
		for (Element i : data1) {
			ArrayList<String> historicalFigures = new ArrayList<>();
			String date;
			String nameEvent;
			if (i.html().contains("</a>")) {
				date = "năm " + getDateFromHtml(i.html());
				nameEvent = getEventFromHtml(i.html());

				// Related Figure

				historicalFigures = getHistoricalFiguresFromNKS(historicalFigures, nameEvent);
				
				ArrayList<String> relatedFigures = new ArrayList<>();
				for(String figure : historicalFigures) {
					if(!figure.contains("- Lịch sử Việt Nam")&& !figure.contains("nhà")) 
						relatedFigures.add(figure);
					else if(figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
						relatedFigures.add(figure.substring(0,figure.indexOf("-")).trim()); 
				}
				System.out.println(relatedFigures);
				new Event(nameEvent, date, "Chưa rõ", "Chưa rõ", "Chưa rõ", relatedFigures);
			}
			
		}
		// Data2
		for (Element i : data2) {
			ArrayList<String> historicalFigures = new ArrayList<>();
			String date;
			String nameEvent;

			Element parentOfData2 = i.parent().previousElementSibling();
			String yearData = parentOfData2.html();

			if (getDateFromHtml(i.html()) != null)
				date = getDateFromHtml(i.html()) + " năm " + getDateFromHtml(yearData);
			else
				date = "năm " + getDateFromHtml(yearData);
			nameEvent = getEventFromHtml(i.html());

			// Related Figures
			historicalFigures = getHistoricalFiguresFromNKS(historicalFigures, nameEvent);

			ArrayList<String> relatedFigures = new ArrayList<>();
			for(String figure : historicalFigures) {
				if(!figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
					relatedFigures.add(figure);
				else if(figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
					relatedFigures.add(figure.substring(0,figure.indexOf("-")).trim()); 
			}
			new Event(nameEvent, date, "Chưa rõ", "Chưa rõ", "Chưa rõ", relatedFigures);
		}
	}
	public static void crawlData() {
		crawlAllLink();
		crawlDataFromLink();
		getDataFromHTML();
	}			
}	
