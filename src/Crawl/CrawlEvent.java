package Crawl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Model.Event;

public class CrawlEvent {
	private static ArrayList<String> eventLink = new ArrayList<>();
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

            String name = "Chưa rõ"; // Ten su kien
            String date = "Chưa rõ"; // Thoi gian dien ra su kien
            String location = "Chưa rõ"; // Dia diem dien ra su kien
            String cause = "Chưa rõ"; // Nguyen nhan dien ra su kien
            String result = "Chưa rõ"; // Ket qua cua su kien
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
            name = doc.select("div.page-header").text();
            
            // Lay ra bang thong tin gom thoi gian dia diem ket qua
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
                    Element firstParagraph = doc.selectFirst("p");
                    firstParagraph.select("sup").remove(); // [class~=(annotation).*]
                    String firstPContent = firstParagraph.text();

                    // The b dau tien la ten cua su kien, co the gom ca thoi gian
                    Element firstBTag = firstParagraph.selectFirst("b");
                    if (firstBTag != null) {
                        String firstBTagContent = firstBTag.text();
                        String[] splitArray = firstBTagContent.split(",");
                        if (splitArray.length > 1) {             
                            if (date.equals("Chưa rõ")) date = splitArray[1].trim();
                        }
                    }
                    Pattern p;
                    Matcher m;
                    // Loc ra ket qua cua su kien
                    p = Pattern.compile("(Kết quả|cuối cùng)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                    m = p.matcher(firstPContent);

                    if (m.find()) {
                        String findResult = m.group(0);
                        if (result.equals("Chưa rõ")) {
                            result = findResult.substring(0, findResult.length() - 1);
                        }
                    }
                    // Loc ra thoi gian cua su kien
                    p = Pattern.compile("(xảy ra|diễn ra) (từ|vào)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                    m = p.matcher(firstPContent);

                    if (m.find()) {
                        String findResult = m.group(0);
                        if (date.equals("Chưa rõ")) {
                            date = findResult.substring(0, findResult.length() - 1);
                        }
                    }
                    // Loc ra nguyen nhan cua tran chien
                    p = Pattern.compile("(nhằm|bắt nguồn|do)[^.]*[.]", Pattern.CASE_INSENSITIVE);
                    m = p.matcher(firstPContent);

                    if (m.find()) {
                        String findResult = m.group(0);
                        if (cause.equals("Chưa rõ")) {
                            cause = findResult.substring(0, findResult.length() - 1);
                        }
                    }
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
