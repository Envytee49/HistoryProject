package Crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		
		try {
			
			final Document doc = Jsoup.connect(url).get();
			Elements rows = doc.select("tr");
			
			for(Element r : rows) {
				
				if(r.select("td:nth-of-type(1)").text().equals("")) {
					continue;
				}
				
//				if(!r.select("td:nth-of-type(1)").text().equals("Lò gốm Cổ Sành")) {
//					continue;
//				}
				
				r.select("sup").remove();
				
				Elements r_rows = r.select("td");
				
				if(r_rows.size() == 5) {
				
					ArrayList<String> relatedFigure = new ArrayList<String>();
					ArrayList<String> relatedFestival = new ArrayList<String>();
					
					String name = r.select("td:nth-of-type(1)").text();
					String location = r.select("td:nth-of-type(2)").text();
					String category = r.select("td:nth-of-type(3)").text();
					String approved = r.select("td:nth-of-type(4)").text();
					String note = r.select("td:nth-of-type(5)").text();
					String constructionDate = "";
					
					String link = "";
					if(r.select("td:nth-of-type(1) a").attr("href").contains("http"))
						link = r.select("td:nth-of-type(1) a").attr("href");
					else link = "https://vi.wikipedia.org" + r.select("td:nth-of-type(1) a").attr("href");
					
					if(link.equals("https://vi.wikipedia.org"))
						continue;
					
//					System.out.println(link);
					
					Document related_doc = Jsoup.connect(link).get();
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
//									System.out.println(aTag.text()+"\n");
									if(!notFigureNameRegexExecution(pTag.text(), aTag.text()) && 
											!relatedFigure.contains(aTag.text())) 
									{
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
					System.out.println("Related Figure");
					System.out.println(relatedFigure);
					System.out.println("Related Festival");
					System.out.println(relatedFestival);
					
					new Site(name, location, constructionDate, note, category, approved, relatedFigure, relatedFestival);
				}
				else if(r_rows.size() == 4) {
					ArrayList<String> relatedFigure = new ArrayList<String>();
					ArrayList<String> relatedFestival = new ArrayList<String>();
					
					String name = r.select("td:nth-of-type(2)").text();
					String location = r.select("td:nth-of-type(3)").text();
					String category = "";
					String approved = "";
					String note = r.select("td:nth-of-type(4)").text();
					String constructionDate = "";
					
					category = setCategoryForNinhBinh(note);
					approved = setApprovedForNinhBinh(note);
					constructionDate = setConstructionDateForNinhBinh(note);
					
					relatedFigure = characterName(note);
					
					if(note.contains("lễ") || note.contains("hội")) {
						relatedFestival.add("Hội " + name);
					}
					
					new Site(name, location, constructionDate, note, category, approved, relatedFigure, relatedFestival);
				}
				
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static ArrayList<String> characterName(String note) {
		ArrayList<String> ans = new ArrayList<>();
		String[] lowerSplit = note.split(" ");
		for(String str : lowerSplit) {
			if(str.length() == 0)
				continue;
			if(Character.isUpperCase(str.charAt(0))) {
				int idChar = getIndexOfFirstSplittedText(lowerSplit, str);
				if(idChar < 2)
					continue;
				if(idChar >= lowerSplit.length - 2)
					break;
				if(Character.isUpperCase(lowerSplit[idChar+1].charAt(0))) {
					String dummy_name = lowerSplit[idChar-2] + lowerSplit[idChar-1] + str + " " + lowerSplit[idChar+1];
					if(!notFigureNameRegexExecution(note, dummy_name))
						ans.add(str+" "+lowerSplit[idChar+1]+" "+lowerSplit[idChar+2]);
				}
			}
		}
		
		return ans;
	}
	
	private static String setConstructionDateForNinhBinh(String note) {
		String lower = note.toLowerCase();
		String date = "";
		String[] lowerSplit = note.split(" ");
		if(lower.contains("di chỉ khảo cổ")) {
			for(String s : lowerSplit) {
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(s);
				if(m.find() && s.compareTo(date) < 0) {
					date = s;
				}
			}
			return date + " years ago";
		}
		if(lower.contains("chùa") && lower.contains("thế kỷ")) {
			int idCentury = getIndexOfFirstSplittedText(lowerSplit, "thế kỷ");
			return "Century "+lowerSplit[idCentury+2];
		}
		if(lower.contains("xây dựng năm")) {
			int idYear = getIndexOfFirstSplittedText(lowerSplit, "xây dựng năm");
			return lowerSplit[idYear+3];
		}
		return "N/A";
	}

	private static String setApprovedForNinhBinh(String note) {
		String lower = note.toLowerCase();
		if(lower.contains("năm") || lower.contains("quốc gia")) {
			String[] lowerSplit = lower.split(" ");
			for(String s : lowerSplit) {
				Pattern p = Pattern.compile("[0-9]");
				Matcher m = p.matcher(s);
				if(m.find() && s.compareTo("1990") >= 0 && s.compareTo("2023") <= 0) {
					return s;
				}
			}
		}
		
		return "N/A";
	}

	private static String setCategoryForNinhBinh(String text) {
		String lower = text.toLowerCase();
		if(lower.contains("kiến trúc nghệ thuật"))
			return "Kiến trúc nghệ thuật";
		if(lower.contains("đền") || lower.contains("chùa") || lower.contains("thờ") || lower.contains("đình")
				|| lower.contains("lịch sử văn hóa"))
			return "Lịch sử văn hóa";
		if(lower.contains("động") || lower.contains("núi") || lower.contains("hang"))
			return "Danh thắng";
		if(lower.contains("chiến") || lower.contains("cách mạng") || lower.contains("huấn luyện") || lower.contains("họp"))
			return "Lịch sử";
		if(lower.contains("di chỉ khảo cổ"))
			return "Di chỉ khảo cổ";
		return "N/A";
	}
	
	private static boolean notFigureNameRegexExecution(String pTag, String text) {
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
		
		String[] validPhrases = {"bia", "ông", "bà", "ngài", "bác", "vua", "chúa", "tướng", "chú", "tước", "sĩ", 
									"hùng", "đế", "chủ tịch", "thống", "đốc", "đồng chí", "lãnh tụ", "anh", "chị",
									"úy", "tá", "trưởng", "sỹ", "tập", "biểu", "niên", "nhà thơ",
									"nhà văn", "học", "sư", "thầy", "nhà báo", "sứ", "hoàng",
									"hậu", "thần", "nhân", "tử", "giả" , "vương", "quan",
									"trần", "nguyễn", "lê", "hồ", "vũ", "phan", "phùng", "ngô", "mai", "đoàn", "phạm",
									"huỳnh", "võ", "trương", "bùi", "đặng", "đỗ", "đinh", "dương"
									};
//		System.out.println("text param: "+text);
		int idLowerText = getIndexOfFirstSplittedText(pTag.split(" "), text);
//		System.out.println("id of text param "+idLowerText);
//		System.out.println("text at: "+pTag.split(" ")[idLowerText]);
		
		if(idLowerText < 1)
			return true;
		
		if(idLowerText == 1 || idLowerText == 2) {
			String[] prefixArr = Arrays.copyOfRange(pTag.split(" "), 0, idLowerText);
			StringBuilder sbs = new StringBuilder();
			for(String st : prefixArr)
				sbs.append(st+" ");
			String prefix = sbs.toString().toLowerCase();
//			System.out.println("prefix: "+prefix);
			for(String s : validPhrases) {
//				System.out.println("s: "+s+"  lowerText.contains(s): "+prefix.contains(s));
				for(String str : prefixArr) {
					if(str.toLowerCase().equals(s) && prefix.contains(s))
						return false;
				}
				if(s.equals("dương") && !prefix.contains(s))
					return true;
			}
		}
		else {
			String[] lowerTextArr = Arrays.copyOfRange(pTag.split(" "), idLowerText-3, idLowerText+splitText.length-1);
//			System.out.println("Print 1, end words:"+
//					pTag.split(" ")[idLowerText-3]+" "+pTag.split(" ")[idLowerText+splitText.length-2]);
			StringBuilder sb = new StringBuilder();
			for(String s : lowerTextArr) {
				sb.append(s + " ");
			}
			String lowerText = sb.toString().toLowerCase();
			
//			System.out.println("String builder: "+sb.toString()+"\n");
			
			for(String s : validPhrases) {
				for(String str : lowerTextArr) {
//					System.out.println("str: "+str.toLowerCase()+" s: "+s);
//					System.out.println(str.toLowerCase().equals(s));
//					System.out.println(lowerText.contains(s));
					if(str != null)
						if(str.toLowerCase().equals(s) && lowerText.contains(s))
							return false;
				}
				if(s.equals("dương") && !lowerText.contains(s))
					return true;
			}
		}
		
			
		return false;
	}
	
	public static int getIndexOfFirstSplittedText(String[] text, String verifiedText) {
		for(int i=text.length-1; i>=0; i--)
			if(text[i].contains(verifiedText.split(" ")[0]))
				return i;
		return -1;
	}
}
