package relation;
import Crawl.*;
import Database.Eras;
import database.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Relation {
	public static void crawlData() {
		// Crawl all data of entities
//		CrawlFestival.crawlData();
//		CrawlEvent.crawlData();
//		CrawlSite.crawlData();

		CrawlEra.crawlData();
	}
	public static boolean checkKing(String s) {
		String lowerCase = s.toLowerCase();
		return lowerCase.contains("đế") ||
				lowerCase.contains("vương") ||
				lowerCase.contains("vua");
//                lowerCase.contains("chúa");
	}
	public static boolean checkQueen(String s) {
		String lowerCase = s.toLowerCase();
		return lowerCase.contains("hoàng hậu");
	}

	public static boolean eraFilter(String s) {
		return s.equalsIgnoreCase("vương") ||
				s.equalsIgnoreCase("nhà") ||
				s.equalsIgnoreCase("gia") ||
				s.equalsIgnoreCase("tộc");
	}
	public static void createRelation() {
		addCharEraRelation();
		addCharEraRelation();
		Eras.save();
	}
	public static void addCharEraRelation() {
		// Lay nhan vat
		ObservableList<model.HistoricalFigure> listOfFigures = database.HistoricalFigures.collection.getData();
		if (listOfFigures.size() == 0) {
			CrawlHistoricalFigure.crawlData();
		}
		// Lay era
		ObservableList<Era> listOfEras = Eras.collection.getData();
		if (listOfEras.size() == 0) {
			CrawlEra.crawlData();
		}

		for (Era e : listOfEras) {
			Map<String, Integer> relatedCharList = new HashMap<>();
			for (Map.Entry<String, Integer> entry : e.getListOfKingsId().entrySet()) {
				if (entry.getKey().equals("")) continue;
				boolean found = false;
				for (HistoricalFigure c : listOfFigures) {
					// Kiem tra trieu dai => neu trieu dai cua nhan vat tim thay ten
					// Khac trieu dai dang xet => sai
					if (!c.getEra().getKey().equals("Chưa rõ")) {
						// Neu ten trieu dai khong giong voi ten trieu dai dang xet
						// Thu kiem tra xem ten cua no co chu in hoa la gi
						if (!c.getEra().getKey().equalsIgnoreCase(e.getName())) {
							boolean foundEra = false;
							String[] splitNames = c.getEra().getKey().split(" ");
							// Chi can tim thay chu (in hoa ky tu dau tien) lien quan la tinh
							// K tinh truong hop chu nha,...
							for (String splitName : splitNames) {
								if (
										Character.isUpperCase(splitName.charAt(0)) &&
												!eraFilter(splitName)
								) {
									Pattern p = Pattern.compile(Pattern.quote(splitName), Pattern.CASE_INSENSITIVE);
									Matcher m = p.matcher(e.getName());

									if (m.find()) {
										foundEra = true;
										// relatedCharList.put(entry.getKey(), c.getId());
//                                        if (c.getEra().getValue() == null) {
//                                            c.setEra(e.getName(), e.getId());
//                                        }
										break;
									}
								}
							}
							// Khong dung trieu dai
							if (!foundEra) continue;
						}
					}

					// Dung de loai bo truong hop la hoang hau,...
					// Vi list nvat lien quan la vua
					boolean isQueen = false;

					Set<String> allPossibleNames = c.fetchAllPossibleNames();
					for (String name : allPossibleNames) {
						// Nhan vat dang xet la hoang hau
						if (name.toLowerCase().contains("hoàng hậu")) {
							isQueen = true;
							break;
						}
					}
					// Neu la hoang hau thi xet th khac
					if (isQueen) continue;

					for (String name : allPossibleNames) {
						// Loc het chu TQ voi dau ngoac ra
						String nameToCompare = name; // Ten nay se dung de tim nhan vat
						Pattern p = Pattern.compile("[^\\p{IsHan}]*[\\p{IsHan}]", Pattern.CASE_INSENSITIVE);
						Matcher m = p.matcher(name);

						// Neu ten co chu TQ thi loc khong thi thoi
						if (m.find()) {
							String result = m.group();
							// Truong hop chu Trung Quoc o dau thi tra ve chuoi rong - ""
							// Trong truong hop do thi cu de no la ten bthg
							if (!result.substring(0, result.length() - 1).equals("")) {
								if (result.contains("(")) {
									nameToCompare = result.substring(0, result.lastIndexOf("(")).trim();
								} else {
									nameToCompare = result.substring(0, result.length() - 1).trim();
								}
							}
						}

						// Loc ra cac dau ngoac - trong truong hop ten k co chu Han
						// K tinh truong hop co ngoac o dau VD - (abc) abc...
						if (
								nameToCompare.equals(name) &&
										nameToCompare.contains("(") &&
										nameToCompare.lastIndexOf("(") > 0
						) {
							nameToCompare = name.substring(0, name.lastIndexOf("(")).trim();
						}

						// Neu ten de so sanh = ten tu tieu de crawl duoc
						// thi chi viec so sanh giong nhau
						// con neu la ten khac cua nhan vat thi kiem tra chua xau
						if (nameToCompare.equals(c.getName())) {
							if (entry.getKey().equalsIgnoreCase(nameToCompare)) {
								found = true;
								relatedCharList.put(entry.getKey(), c.getId());
								if (c.getEra().getKey().equals("Chưa rõ")) {
									c.setEra(e.getName(), e.getId());
								} else if (c.getEra().getValue() == null) {
									if (!e.getAliases().contains(c.getEra().getKey())) {
										e.addAlias(c.getEra().getKey());
									}
									c.setEra(e.getName(), e.getId());
								}
								break;
							}
						} else {
							if (entry.getKey().length() > nameToCompare.length()) {
								p = Pattern.compile(Pattern.quote(nameToCompare), Pattern.CASE_INSENSITIVE);
								m = p.matcher(entry.getKey());
							} else {
								p = Pattern.compile(Pattern.quote(entry.getKey()), Pattern.CASE_INSENSITIVE);
								m = p.matcher(nameToCompare);
							}
							if (m.find()) {
								found = true;
								relatedCharList.put(entry.getKey(), c.getId());
								if (c.getEra().getKey().equals("Chưa rõ")) {
									c.setEra(e.getName(), e.getId());
								} else if (c.getEra().getValue() == null) {
									if (!e.getAliases().contains(c.getEra().getKey())) {
										e.addAlias(c.getEra().getKey());
									}
									c.setEra(e.getName(), e.getId());
								}
								break;
							}
						}
					}
					// Neu tim thay thi thoat khoi vong lap
					if (found) break;
				}
				if (!found) relatedCharList.put(entry.getKey(), null);
			}
			e.setListOfKingsId(relatedCharList);
		}
	}
=======
		CrawlHistoricalFigure.crawlData();
	}
	public static void createRelation() {
		// Step 1
		// This part create relation by setting the 'value' as 'key'
		// ... implementation
		
		
		// Step 2
		// Save entities data to JSON files
		
		//Create instances
		Festivals festivals = new Festivals();
		Events events = new Events();
		HistoricalFigures figures = new HistoricalFigures();
		//Sites sites = new Sites();
		
		// Save entities data to JSON files
		figures.saveToJSON();
//		festivals.saveToJSON();	
//		events.saveToJSON();
		//sites.saveToJSON();
}
>>>>>>> b8557c0efdfab1a8dc297d73bd779ae73cee0408
}
