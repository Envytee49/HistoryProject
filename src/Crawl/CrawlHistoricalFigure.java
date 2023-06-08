<<<<<<< HEAD
package Crawl;
=======
package crawl;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
>>>>>>> b8557c0efdfab1a8dc297d73bd779ae73cee0408

import model.HistoricalFigure;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlHistoricalFigure extends CrawlHelper{
	private static ArrayList<String> figuresLink = new ArrayList<>();
	//method for get all page
	public static void crawlAllLink(){
		
		String url = "https://nguoikesu.com/nhan-vat";
		
		try {
			//get link
			Document docs = Jsoup.connect(url).get();
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

	public static void crawlDataFromLink() {
		for (String url : figuresLink) {	
			crawlFigures(url);
		}
	}
	
	public static void crawlData(){
		//get link
		crawlAllLink();
		crawlDataFromLink();
	}

}