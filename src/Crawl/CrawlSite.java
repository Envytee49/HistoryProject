<<<<<<< HEAD
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
=======
package Crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Model.Site;


public class CrawlSite {
	public static void crawlData() {
		String url = "https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam";
		System.out.println(url);
		try {
			final Document doc = Jsoup.connect(url).get();
			Elements rows = doc.select("tr");
			
			for(Element r : rows) {
				
				if(r.select("td:nth-of-type(1)").text().equals("")) {
					continue;
				}
				
				r.select("sup").remove();
				
				ArrayList<String> relatedFigure = new ArrayList<String>();
				ArrayList<String> relatedFestival = new ArrayList<String>();
				
				String name = r.select("td:nth-of-type(1)").text();
				String location = r.select("td:nth-of-type(2)").text();
				String category = r.select("td:nth-of-type(3)").text();
				String approved = r.select("td:nth-of-type(4)").text();
				String note = r.select("td:nth-of-type(5)").text();
				String constructionDate = "";
				
				String link = "https://vi.wikipedia.org" + r.select("td:nth-of-type(1) a").attr("href");
				Document related_doc = Jsoup.connect(link).timeout(120000).get();
				Element related_div = related_doc.selectFirst("#mw-content-text > div.mw-parser-output");

				// Avoid null values
				if(related_div != null) {
					Element firstPar = related_div.selectFirst("p");
					firstPar.select("sup").remove();
					String firstParContent = firstPar.text();
					
					// get constructionDate
					if(constructionDate.equals("")) {
						Pattern p = Pattern.compile("(xây dựng|xây) (từ|vào|trong)[^.]*[.(]", Pattern.CASE_INSENSITIVE);
						Matcher m = p.matcher(firstParContent);
						
						// Find it in the first paragraph first.
						if(m.find()) {
							String foundedConstrDate = m.group(0);
							constructionDate = foundedConstrDate.substring(0, foundedConstrDate.length() - 1);
						}
						// if the first para doesn't contain info about constructionDate,
						// need to verify that there exists the section "Lich su". 
						// If it does, continue searching in that section 
						else {
							Element constrDateContainerHeading = related_div.selectFirst("h2:contains(Lịch sử)");
							if(constrDateContainerHeading != null) {
								// return the next element after h2. Probably a "p" element
								Element constrDateContainerPara = constrDateContainerHeading.nextElementSibling();
								constrDateContainerPara.select("sup").remove();
								if(constructionDate.equals("")) {
									m = p.matcher(constrDateContainerPara.text());
									if(m.find()) {
										String foundedConstrDate = m.group(0);
										constructionDate = foundedConstrDate.substring(0, foundedConstrDate.length() - 1);
									}
								}
							}
						}
					}
					
					// get Related Festival
					Element festivalHeading = related_div.selectFirst("h2:contains(Lễ hội)");
					if(festivalHeading != null) {
						Element festivalPara = festivalHeading.nextElementSibling();
						festivalPara.select("sup").remove();
						String tempFest = festivalPara.text();
						if(!relatedFestival.contains(tempFest))
							relatedFestival.add(tempFest);
					}
					
					// get Related Figures
					for(Element e : related_div.children()) {
						// Escape condition
						if(e.tagName().equals("h2")) {
							String textedElement = e.text().toLowerCase();
							if(textedElement.contains("liên quan") ||
							   textedElement.contains("tham khảo") ||	
							   textedElement.contains("xem thêm") ||
							   textedElement.contains("chú thích"))
								break;
						}
						
						Elements aTags = e.select("a");
						
						if(aTags.size() > 0) {
							for(Element aTag : aTags) {
								Element pTag = aTag.parent();
								if(!notFigureNameRegexExecution(pTag, aTag.text()) &&  !relatedFigure.contains(aTag.text())) {
									relatedFigure.add(aTag.text());
								}
							}
						}
					}
				}
				
				if(constructionDate.equals(""))
					constructionDate = "N/A";
				
				if(note.equals(""))
					note = "N/A";
				
				System.out.println("\nName: " +name);
				System.out.println("Location: "+location);
				System.out.println("Construction Date: "+constructionDate);
				System.out.println("Note: "+note);
				System.out.println("Category: "+category);
				System.out.println("Approved: "+approved);
				System.out.println("Related Figure\n");
				System.out.println(relatedFigure);
				System.out.println("Related Festival\n");
				System.out.println(relatedFestival);

				new Site(name, location, constructionDate, note, category, approved, relatedFigure, relatedFestival);
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static boolean notFigureNameRegexExecution(Element pTag, String text) {
		// empty string or a character alone
		if(text.equals("") || text.length() == 1) {
			return true;
		}
		
		// check if text is a string of numbers
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(text);
		if(m.find()) {
			return true;
		}
		
		String[] splitText = text.split(" ");
		if(splitText.length < 2) {
			return true;
		}
		else {
			for(String s : splitText) {
				if(!Character.isUpperCase(s.charAt(0))) {
					return true;
				}
			}
		}
		
		String[] invalidPhrases = {"xã", "nhà", "triều", "miếu", "sông", "phủ", "đền", "biển", "thành", "di sản", "cố đô",
                "di tích", "tổ chức", "thế kỷ", "trận", "chùa", "đường", "phố", "bản", "người", "động",
                "bộ", "sửa", "xã", "kháng chiến", "chiến khu", "quốc lộ", "cách mạng", "chú thích", "nguồn", "đảo", "chiến dịch",
                "trung đoàn", "đại đoàn", "chiều", "huyện", "tỉnh", "thủy điện", "hang", "ubnd", "ủy", "thời", "khảo cổ",
                "lịch sử", "đá", "thị trấn", "cực", "vĩ độ", "kinh độ", "tọa độ", "việt nam", "trống", "cách",
                "biên giới", "tiếng", "cờ", "ruộng", "biên giới", "này", "kiểm chứng", "'", "[", "]", "/", "km", "cm", "suối",
                "gỗ", "trám", "-"};
		
		int idLowerText = getIndexOfFirstSplittedText(pTag.text().split(" "), text);
		
		if(idLowerText == -1) {
			return true;
		}
		
		if(idLowerText == 0)
			return false;
		
		String lowerText = text.toLowerCase();
		
		for(String s : invalidPhrases) {
			
			if(
				pTag.text().split(" ")[idLowerText-1].equals(s) ||
				lowerText.contains(s)
					)
				return true;
		}
			
		return false;
	}
	
	public static int getIndexOfFirstSplittedText(String[] text, String verifiedText) {
		for(int i=0; i<text.length; i++)
			if(text[i].contains(verifiedText.split(" ")[0]))
				return i;
		return -1;
	}
	
	public static void main(String[] args) {
		crawlData();
	}
	
}
>>>>>>> b8557c0efdfab1a8dc297d73bd779ae73cee0408
