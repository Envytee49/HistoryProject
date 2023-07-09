package crawl;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.HistoricalFigures;
import model.HistoricalFigure;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CrawlHistoricalFigure {
	private static ArrayList<String> figuresLink = new ArrayList<>();
	//method for get all page
	//implement method for checking data in table
		public static boolean workTimeCheck(String workTime) {
	        return workTime.equalsIgnoreCase("Trị vì") ||
	                workTime.equalsIgnoreCase("trị vì") ||
	                workTime.equalsIgnoreCase("Tại vị") ||
	                workTime.equalsIgnoreCase("Nhiệm kỳ") ||
	                workTime.equalsIgnoreCase("Năm tại ngũ") ||
	                workTime.equalsIgnoreCase("Hoạt động");
	    }
		
		public static boolean fatherCheck(String father) {
	        return father.equalsIgnoreCase("Thân phụ") ||
	                father.equalsIgnoreCase("Cha");                
	    }
		
		public static boolean motherCheck(String mother) {
	        return mother.equalsIgnoreCase("Thân mẫu") ||
	                mother.equalsIgnoreCase("Mẹ");
	                
	    }
		
		public static boolean parentCheck(String parent) {
	        return parent.equalsIgnoreCase("Bố mẹ") ||
	                parent.equalsIgnoreCase("Cha mẹ");
	    }
		
		
		
		public static boolean birthCheck(String birth) {
	        return birth.equalsIgnoreCase("Ngày sinh") ||
	                birth.equalsIgnoreCase("Sinh");
	    }
		
		public static boolean realNameCheck(String realName) {
	        return realName.equalsIgnoreCase("Húy") ||
	                realName.equalsIgnoreCase("Tên thật") ||
	                realName.equalsIgnoreCase("tên thật") ||
	                realName.equalsIgnoreCase("Tên đầy đủ") ||
	                realName.equalsIgnoreCase("Tên húy");
	    }
		
		public static boolean diedCheck(String died) {
			return died.equalsIgnoreCase("Mất") ||
					died.equalsIgnoreCase("Chết");
		}
		
		public static boolean eraCheck(String era) {
	        return era.equalsIgnoreCase("Hoàng tộc") ||
	                era.equalsIgnoreCase("Triều đại") ||
	                era.equalsIgnoreCase("Gia tộc") ||
	                era.equalsIgnoreCase("Kỷ nguyên");
	    }
	public static void crawlAllLinkFromLink1(){
		
		String url1 = "https://nguoikesu.com/nhan-vat";
		
		try {
			//get link
			Document docs = Jsoup.connect(url1).get();
			//get number of pages
			String page = docs.select("div.com-content-category-blog.blog > div.com-content-category-blog__navigation.w-100 > p").text();
			String[] splitPage = page.split(" ");
			int pageNum = Integer.parseInt(splitPage[splitPage.length-1]);
			
			Elements pageAddress = docs.select("div");
			//get link in page 1
			int index = 0;
			for (Element e : pageAddress.select("div div h2 a")) {
				String res = e.attr("href");
				//System.out.println("https://nguoikesu.com" + res);
				figuresLink.add("https://nguoikesu.com"+res);
				index++;
			}
			
			//get link in all other pages
			for (int i = 1; i < pageNum; i++) {
				//get page address
				String url2 = "https://nguoikesu.com/nhan-vat" + "?start=" + index;
				Document docs2 = Jsoup.connect(url2).get();
				Elements pageAddress2 = docs2.select("div");
				
				//get each page of figures in that page
				for (Element e : pageAddress2.select("div div h2 a")) {
					String res = e.attr("href");
					//add to ArrayList
					figuresLink.add("https://nguoikesu.com"+res);
					index++;
				}	
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}	
	}
	public static void crawlAllLinkFromLink2() {
		String wikiUrl = "https://vi.wikipedia.org/";
		try {
			ArrayList<String> eraList = new ArrayList<String>();
			Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam").get();
			Elements eraNames = doc.select("h3 .mw-headline"); 	 // select all era name
			Elements kingTables = doc.select("table[cellpadding=0][style*='font-size:90%']"); // select all tables with kings
			
			// Add all era to eraList
			for(Element eraName : eraNames) {
				if(eraName.text().contains("("))
					eraList.add(eraName.text().substring(0,eraName.text().indexOf('(') - 1));
				
				else 
					eraList.add(eraName.text());
			}
			
				for(int i = 0; i < kingTables.size(); i++) {
					Elements r = kingTables.get(i).select("tr");
					for(int j = 1; j < r.size(); j++) {
						String born = "Chưa rõ";
						String died = "Chưa rõ";
						String workTime = "Chưa rõ";
						String father = "Chưa rõ";
						String mother = "Chưa rõ";
						String succeededBy = "Chưa rõ";
						String preceededBy = "Chưa rõ";
						String name = "Chưa rõ";
						String era = "Chưa rõ";
						// Get name of king
						r.get(j).select("td:nth-of-type(2)").select("sup").remove();
						name = r.get(j).select("td:nth-of-type(2)").text();
						
						// Get king href
						String kingURL = wikiUrl + r.get(j).select("td:nth-of-type(2)").select("a").attr("href");
						
						Document kingDoc = Jsoup.connect(kingURL).get();
						Element kingTableContent = kingDoc.selectFirst("table[class=infobox]");
						if(kingTableContent != null) {
						Elements rowData = kingTableContent.select("tr");				
						for(Element rowContent : rowData) {
							if(rowContent.select("th") != null) {
								String thContent = rowContent.select("th").text();
								rowContent.select("td").select("sup").remove();	
								String tdContent = rowContent.select("td").text();
								if(thContent.equals("Trị vì")) workTime = tdContent;
								else if(thContent.equals("Tiền nhiệm")) preceededBy = tdContent;
								else if(thContent.equals("Kế nhiệm")) succeededBy = tdContent;
								else if(thContent.equals("Sinh")) born =  tdContent;
								else if(thContent.equals("Mất")) died = tdContent;
								else if(thContent.equals("Thân phụ"))father = tdContent;
								else if(thContent.equals("Thân mẫu")) mother = tdContent;
								}
							}
						}
						era = eraList.get(i);
						System.out.println("Ten: " + name);
						System.out.println("Sinh: " + born);
						System.out.println("Mất: " + died);
						System.out.println("Tại vị: " + workTime);
						System.out.println("Thân phụ: " + father);
						System.out.println("Thân mẫu: " + mother);
						System.out.println("Kế nhiệm: " + succeededBy);
						System.out.println("Tiền nhiệm: " + preceededBy);
						System.out.println("Kỷ nguyên: " + era);
						
						
						
							new HistoricalFigure(name, born, died, workTime, era, father, mother, succeededBy, preceededBy);
						
							
					}
				}
				
			
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void crawlFigures(String url) {
		String born = "Chưa rõ";
		String died = "Chưa rõ";
		String workTime = "Chưa rõ";
		String father = "Chưa rõ";
		String mother = "Chưa rõ";
		String succeededBy = "Chưa rõ";
		String preceededBy = "Chưa rõ";
		String name = "Chưa rõ";
		String era = "Chưa rõ";
		
		try {
			Document doc = Jsoup.connect(url).get();
			Elements info = doc.select("div.com-content-article.item-page.page-list-items > div.com-content-article__body > div.infobox > table > tbody");
			//get name in page header
			Elements firstFoundCharName = doc.select("div[class=page-header] > h2");
	            if (firstFoundCharName != null) name = firstFoundCharName.text();
			for (Element row : info.select("tr")) {
				//System.out.println(head.select("th").text());
				String tmp = row.select("th").text();
				
				if (birthCheck(tmp)) {
					Elements borntmp = row.select("td");
					if (borntmp != null) {
						borntmp.select("sup").remove();
						born = borntmp.text();
					}
				}
				else if (diedCheck(tmp)) {
					Elements diedtmp = row.select("td");
					if (diedtmp != null) {
						diedtmp.select("sup").remove();
						died = diedtmp.text();
					}
				}
				else if (workTimeCheck(tmp)) {
					workTime = row.select("td").text();
					Elements timetmp = row.select("td");
					if (timetmp != null) {
						timetmp.select("sup").remove();
						workTime = timetmp.text();
					}
				}
				else if (parentCheck(tmp)) {
					mother = row.select("td").text();
					father = row.select("td").text();
					Elements mothertmp = row.select("td");
					Elements fathertmp = row.select("td");
					if (mothertmp != null) {
						mothertmp.select("sup").remove();
						mother = mothertmp.text();
					}
					if (fathertmp != null) {
						fathertmp.select("sup").remove();
						father = fathertmp.text();
					}
				}
				else if (motherCheck(tmp)) {
					mother = row.select("td").text();
					Elements mothertmp = row.select("td");
					if (mothertmp != null) {
						mothertmp.select("sup").remove();
						mother = mothertmp.text();
					}
				}
				else if (fatherCheck(tmp)) {
					
					father = row.select("td").text();
					Elements fathertmp = row.select("td");
					if (fathertmp != null) {
						fathertmp.select("sup").remove();
						father = fathertmp.text();
					}
				}
				else if (tmp.equals("Kế nhiệm")) {
					succeededBy = row.select("td").text();
					Elements succeedtmp = row.select("td");
					if (succeedtmp != null) {
						succeedtmp.select("sup").remove();
						succeededBy = succeedtmp.text();
					}
				}
				else if (tmp.equals("Tiền nhiệm")) {
					preceededBy = row.select("td").text();
					Elements preceedtmp = row.select("td");
					if (preceedtmp != null) {
						preceedtmp.select("sup").remove();
						preceededBy = preceedtmp.text();
					}
				}
				
				else if (eraCheck(tmp)) {
					era = row.select("td").text();
					Elements eratmp = row.select("td");
					if (eratmp != null) {
						eratmp.select("sup").remove();
						era = eratmp.text();
					}
				}
				
			}
			
			//if the page doesn't have table, get information from paragraph tag <p>
			 Element contentBody = doc.select("div[class=com-content-article__body]").first();

	            // Take the first pTag
	            Elements contentBodyElements = contentBody.children();

	            for (Element item : contentBodyElements) {
	                if (item.tagName().equals("p")) {
	                    Element firstParagraph = item;
	                    // Get notes
	                    // [class~=(annotation).*]
	                    firstParagraph.select("sup").remove();
	                    // Get children tag of p
//	                    Elements pATags = firstParagraph.select("a");
	                    // Content
	                    String firstPContent = firstParagraph.text();

//	                    Element firstBTag = firstParagraph.select("b").first();

	                    Pattern birthRegex = Pattern.compile("\\(([^)]*)\\)", Pattern.UNICODE_CASE);
	                    Matcher birthMatch = birthRegex.matcher(firstPContent);

	                    while (birthMatch.find()) {
	                        String firstResult = birthMatch.group(0);

	                        // Take string have format (...) => Get ... - in () text
	                        // Cases not DOB
	                        Pattern checkValid = Pattern.compile("sinh|tháng|năm|-|–");
	                        Matcher matchValid = checkValid.matcher(firstResult);

	                        if (matchValid.find()) {
	                            // Omit Chinese character: ...,/; ... => Get part after ...
	                            int startIndex = firstResult.lastIndexOf(',');
	                            if (startIndex == -1) {
	                                startIndex = firstResult.lastIndexOf(';');
	                                if (startIndex == -1) {
	                                    startIndex = firstResult.lastIndexOf('；');
	                                    if (startIndex == -1) {
	                                        startIndex = 1;
	                                    } else startIndex++;
	                                } else startIndex++;
	                            } else startIndex++;

	                            String contentInParen = firstResult.substring(startIndex, firstResult.length() - 1);
	                            if (contentInParen.contains("trị vì")) {
	                                if (workTime.equals("Chưa rõ")) {
	                                    workTime = contentInParen;
	                                }
	                            } else {
	                                // Seperate birth and died
	                                String[] splitString = {};
	                                if (contentInParen.contains("-")) {
	                                    splitString = contentInParen.split("-");
	                                } else {
	                                    splitString = contentInParen.split("–");
	                                }

	                                if (splitString.length == 1) {
	                                    if (born.equals("Chưa rõ")) born = splitString[0].trim();
	                                } else {
	                                    if (born.equals("Chưa rõ")) born = splitString[0].trim();
	                                    if (died.equals("Chưa rõ")) died = splitString[1].trim();
	                                }
	                            }
	                            break;
	                        } else {
	                            int start = firstPContent.indexOf(')');
	                            firstPContent = firstPContent.substring(start + 1);
	                            birthMatch = birthRegex.matcher(firstPContent);
	                        }
	                    }
	                                        
	                }
	                	                			
	            }
			System.out.println("Ten: " + name);
			System.out.println("Sinh: " + born);
			System.out.println("Mất: " + died);
			System.out.println("Tại vị: " + workTime);
			System.out.println("Thân phụ: " + father);
			System.out.println("Thân mẫu: " + mother);
			System.out.println("Kế nhiệm: " + succeededBy);
			System.out.println("Tiền nhiệm: " + preceededBy);
			System.out.println("Kỷ nguyên: " + era);
//			
			boolean check = false;
			for(HistoricalFigure figure : HistoricalFigures.collection.getEntityData()) {
				if(name.equals(figure.getName())) {
					 check = true;
					 break;
				}
			}
			if (!check) {
				new HistoricalFigure(name, born, died, workTime, era, father, mother, succeededBy, preceededBy);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void crawlDataFromLink1() {
		for (String url : figuresLink) {	
			crawlFigures(url);
		}
	}
	public static void crawlData(){
		//get link
		crawlAllLinkFromLink2();
		crawlAllLinkFromLink1();
		crawlDataFromLink1();
		
	}

}