package crawl;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import model.HistoricalFigure;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CrawlHistoricalFigure extends crawl.CrawlHelper {
	private static ArrayList<String> figuresLink1 = new ArrayList<>();
	//method for get all page
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
				figuresLink1.add("https://nguoikesu.com"+res);
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
					figuresLink1.add("https://nguoikesu.com"+res);
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

	            // Chỉ lấy ở thẻ p đầu tiên vì hầu như thông tin tập hợp ở đó
	            // Có thể thiếu trường hợp
	            Elements contentBodyElements = contentBody.children();

	            for (Element item : contentBodyElements) {
	                if (item.tagName().equals("p")) {
	                    Element firstParagraph = item;
	                    // Loc ra cac the chu thich
	                    // [class~=(annotation).*]
	                    firstParagraph.select("sup").remove();
	                    // Lấy các thẻ a là thẻ con của p
	                    Elements pATags = firstParagraph.select("a");
	                    // Noi dung doan van ban sau khi loc
	                    String firstPContent = firstParagraph.text();

//	                    Element firstBTag = firstParagraph.select("b").first();

	                    Pattern birthRegex = Pattern.compile("\\(([^)]*)\\)", Pattern.UNICODE_CASE);
	                    Matcher birthMatch = birthRegex.matcher(firstPContent);

	                    while (birthMatch.find()) {
	                        String firstResult = birthMatch.group(0);

	                        // Lay ra doan xau co format (...) => lay ... - đoạn text trong ngoặc
	                        // Truong hop doan trong ngoac khong phai ngay sinh
	                        Pattern checkValid = Pattern.compile("sinh|tháng|năm|-|–");
	                        Matcher matchValid = checkValid.matcher(firstResult);

	                        if (matchValid.find()) {
	                            // Loai bo phan chu Han: ...,/; ... => lay phan ... sau
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
	                                // Chia ra nam sinh voi nam mat
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
	                    for (Element a : pATags) {
	                        String hrefValue = a.attr("href");
	                        if (hrefValue.contains("nha-")) {
	                            if (era.equals("Chưa rõ")) {
	                                era = a.text();
	                                break;
	                            }
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
			
			if (name != null) {
				new HistoricalFigure(name, born, died, workTime, era, father, mother, succeededBy, preceededBy);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void crawlDataFromLink1() {
		for (String url : figuresLink1) {	
			crawlFigures(url);
		}
	}
	public static void crawlData(){
		//get link
		crawlAllLinkFromLink1();
		crawlDataFromLink1();
		crawlAllLinkFromLink2();
	}
	public static void main(String[] args) {
		crawlData();
	}

}