package test;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test{
	public static String nameFormat(String name) {
		String[] arrayName = name.trim().split(" ");
		for (int i = 0; i < arrayName.length; i++) {
			String firstLetter = arrayName[i].substring(0, 1).toUpperCase();
			String lastLetter = arrayName[i].substring(1).toLowerCase();
			arrayName[i] = firstLetter + lastLetter;
		}
		name = name.join(" ", arrayName);
		return name;
	}
	private static ArrayList<String> getHistoricalFigures(
			ArrayList<String> historicalFigures, String text) {
		for (String i : historicalFigures) {
			if (text.toUpperCase().contains(i) && !historicalFigures.contains(nameFormat(i)))
				historicalFigures.add(nameFormat(i));
		}

		return historicalFigures;
	}
	// Get data from NKS
	private static ArrayList<String> getHistoricalFiguresToNKS(
			ArrayList<String> historicalFigures, String nameEvent) {
		try {
			int commaIndex = nameEvent.length();
			for (int j = 0; j < nameEvent.length(); j++)
				if (nameEvent.charAt(j) == ',')
					commaIndex = j;
			String link = "https://www.google.com.vn/search?q=" + nameEvent.substring(0, commaIndex).trim()
					+ " nguoikesu";
			Elements elementsNKS = Jsoup.connect(link).get().select(".yuRUbf > a");
			if(elementsNKS == null) System.out.println("hello2");
			for (Element element : elementsNKS) {
				if (element.attr("href").contains("https://nguoikesu.com/nhan-vat")) {
					for (Element elementChild : element.children()) {
						if (elementChild.outerHtml().contains("</h3>")) {
							historicalFigures.add(elementChild.text().replace("- Người Kể Sử", "")
									.replace("- NguoiKeSu.com", "").trim());
						}
					}
				}
				if (element.attr("href").contains("https://nguoikesu.com/dong-lich-su")) {
					String text = Jsoup.connect(element.attr("href")).get().select("#jm-maincontent").text();
					historicalFigures = getHistoricalFigures(historicalFigures, text);
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
		int count = 0;
		for (Element i : data1) {
			ArrayList<String> historicalFigures = new ArrayList<>();
			String date;
			String nameEvent;
			if (i.html().contains("</a>")) {
				date = "năm " + getDateFromHtml(i.html());
				nameEvent = getEventFromHtml(i.html());
				// lấy nhân vật lịch sử liên quan từ event
					historicalFigures = getHistoricalFiguresToNKS(historicalFigures, nameEvent);

				ArrayList<String> relatedFigures = new ArrayList<>();
				for(String figure : historicalFigures) {
					if(!figure.contains("- Lịch sử Việt Nam")&& !figure.contains("nhà")) 
						relatedFigures.add(figure);
					else if(figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
						relatedFigures.add(figure.substring(0,figure.indexOf("-")).trim()); 
				}
				count++;
				System.out.println(relatedFigures);
//				new Event(nameEvent, date, "Chưa rõ", "Chưa rõ", "Chưa rõ", relatedFigures);
			}
			System.out.println(count);
			
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
				historicalFigures = getHistoricalFiguresToNKS(historicalFigures, nameEvent);
			ArrayList<String> relatedFigures = new ArrayList<>();
			for(String figure : historicalFigures) {
				if(!figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
					relatedFigures.add(figure);
				else if(figure.contains("- Lịch sử Việt Nam") && !figure.contains("nhà")) 
					relatedFigures.add(figure.substring(0,figure.indexOf("-")).trim()); 
			}
//			new Event(nameEvent, date, "Chưa rõ", "Chưa rõ", "Chưa rõ", relatedFigures);
			System.out.println(relatedFigures);
			count++;
			System.out.println(count);
		}
		System.out.println(count);

	}
	public static void main(String[] args) {
//		Events.queryJSON();
//		ArrayList<Event> event = Events.collection.getEntityData();
//		for (Event e : event) {
//		    for (Map.Entry<String, Integer> entry : e.getRelatedFigure().entrySet()) {		
//		        if (entry.getKey().contains("- Lịch sử Việt Nam") ||
//		            entry.getKey().contains("- Nhân Vật Lịch Sử") ||
//		            entry.getKey().contains("- Nhân Vật")) {
//		            String newKey = entry.getKey().substring(0,entry.getKey().indexOf("-")).trim();
//		            Integer value = entry.getValue();
//		            e.getRelatedFigure().remove(entry.getKey());
//		            e.getRelatedFigure().put(newKey, value);
//		            System.out.println(newKey);
//		        }
//		    }
//		}
//		
//		for (Event e : event) {
//			Events.writeJSON(e);
//		}
//		Relation.createRelation();
//		
	getDataFromHTML();	
	}
	
}
