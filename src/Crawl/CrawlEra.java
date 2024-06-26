package crawl;

import model.Era;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CrawlEra extends Crawl {

	 public CrawlEra() {
	        crawlData();
	    }

	    public static void crawlFromWiki() {
	        try {
	            Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Vua_Việt_Nam").timeout(120000).get();

	            // Lay ra content table
	            Element contentDiv = doc.selectFirst("div[class=mw-parser-output]");
	            Elements contentDivChild = contentDiv.children();

	            // Lay ra bang wiki
	            Element wikiTable = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(91)");
	            Elements tableRows = wikiTable.select("tr");
	            System.out.println("Number of table row: " + tableRows.size());

	            for (int i = 0; i < contentDivChild.size(); ++i) {
	                Element e = contentDivChild.get(i);
	                if (e.tagName().equals("h2")) {
	                    // The span chua ten dong lich su
	                    Element titleSpan = e.selectFirst("span[class=mw-headline]");

	                    if (titleSpan != null) {
	                        String titleSpanContent = titleSpan.text();

	                        Pattern p = Pattern.compile("(Thời|Bắc).*", Pattern.CASE_INSENSITIVE);
	                        Matcher m = p.matcher(titleSpanContent);

	                        if (m.find()) {
	                            String currentTimeStamp = titleSpanContent; // Dong thoi gian dang xet
//	                            System.out.println("Current timestamp: " + currentTimeStamp);
	                            String currentEra = ""; // Trieu dai dang xet
	                            String founder = "Chưa rõ"; // Nguoi sang lap - vi vua dau tien?
	                            String location = "Chưa rõ"; // Dia chi kinh do - neu co
	                            String hometown = "Chưa rõ"; // Que huong - neu co
	                            String eraTime = "Chưa rõ"; // Thoi gian cua trieu dai
	                            String overview = "Chưa rõ"; // Mo ta cua trieu dai
	                            String link = null;
	                            ArrayList<String> kingList = new ArrayList<>();

	                            while (!contentDivChild.get(i + 1).tagName().equals("h2")) {
	                                i++;
	                                Element currentElement = contentDivChild.get(i);
	                                if (currentElement.tagName().equals("h3")) {
	                                    Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
	                                    if (eraNameElement != null) {
	                                        Element aTag = eraNameElement.selectFirst("a");
	                                        if (aTag != null) {
	                                            link = "https://vi.wikipedia.org" + aTag.attr("href");
	                                        }

	                                        String eraNameEleContent = eraNameElement.text();
	                                        // Xem xem co dau ngoac nao khong
	                                        int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
//	                                        System.out.println("Number of parentheses: " + countParen);
//	                                        System.out.println("Current title: " + eraNameEleContent);
	                                        // Khong ro nam nao?
	                                        if (countParen == 0) {
	                                            currentEra = eraNameEleContent;
	                                        }
	                                        // Chi co 1 moc tgian
	                                        else if (countParen == 1) {
	                                            int startParen = eraNameEleContent.indexOf("(");
	                                            int endParen = eraNameEleContent.indexOf(")");
	                                            currentEra = eraNameEleContent.substring(0, startParen - 1).trim();
	                                            eraTime = eraNameEleContent.substring(startParen + 1, endParen).trim();
	                                        }
	                                        // Co 2 moc thoi gian => phai lay 2 cai
	                                        else if (countParen == 2) {
	                                            int startParenFirst = eraNameEleContent.indexOf("(");
	                                            int endParenFirst = eraNameEleContent.indexOf(")");
	                                            int startParenSec = eraNameEleContent.lastIndexOf("(");
	                                            int endParenSec = eraNameEleContent.lastIndexOf(")");
	                                            currentEra = eraNameEleContent.substring(0, startParenFirst - 1);
	                                            String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
	                                            String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
	                                            String[] firstParenSplit = firstParenContent.split("–");
	                                            String[] secParenSplit = secondParenContent.split("–");
	                                            if (firstParenSplit.length < 2) {
	                                                firstParenSplit = firstParenContent.split("-");
	                                            }
	                                            if (secParenSplit.length < 2) {
	                                                secParenSplit = secondParenContent.split("-");
	                                            }
	                                            eraTime = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
	                                        }
	                                    }
	                                } else if (currentElement.tagName().equals("table")) {
	                                    if (
	                                            currentElement.attr("cellpadding").equals("2") ||
	                                            (currentElement.hasClass("wikitable") &&
	                                                    !currentElement.hasAttr("cellpadding"))
	                                    ) {
	                                        Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
	                                        if (eraNameElement != null) {
	                                            Element aTag = eraNameElement.selectFirst("a");
	                                            if (aTag != null) {
	                                                link = "https://vi.wikipedia.org" + aTag.attr("href");
	                                            }
	                                            String eraNameEleContent = eraNameElement.text();
	                                            // Xem xem co dau ngoac nao khong
	                                            int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();
//	                                        System.out.println("Number of parentheses: " + countParen);
//	                                            System.out.println("Current title: " + eraNameEleContent);
	                                            // Khong ro nam nao?
	                                            if (countParen == 0) {
	                                                currentEra = eraNameEleContent;
	                                            }
	                                            // Chi co 1 moc tgian
	                                            else if (countParen == 1) {
	                                                int startParen = eraNameEleContent.indexOf("(");
	                                                int endParen = eraNameEleContent.indexOf(")");
	                                                currentEra = eraNameEleContent.substring(0, startParen - 1).trim();
	                                                eraTime = eraNameEleContent.substring(startParen + 1, endParen).trim();
	                                            }
	                                            // Co 2 moc thoi gian => phai lay 2 cai
	                                            else if (countParen == 2) {
	                                                int startParenFirst = eraNameEleContent.indexOf("(");
	                                                int endParenFirst = eraNameEleContent.indexOf(")");
	                                                int startParenSec = eraNameEleContent.lastIndexOf("(");
	                                                int endParenSec = eraNameEleContent.lastIndexOf(")");
	                                                currentEra = eraNameEleContent.substring(0, startParenFirst - 1);
	                                                String firstParenContent = eraNameEleContent.substring(startParenFirst + 1, endParenFirst);
	                                                String secondParenContent = eraNameEleContent.substring(startParenSec + 1, endParenSec);
	                                                String[] firstParenSplit = firstParenContent.split("–");
	                                                String[] secParenSplit = secondParenContent.split("–");
	                                                if (firstParenSplit.length < 2) {
	                                                    firstParenSplit = firstParenContent.split("-");
	                                                }
	                                                if (secParenSplit.length < 2) {
	                                                    secParenSplit = secondParenContent.split("-");
	                                                }
	                                                eraTime = firstParenSplit[0].trim() + " - " + secParenSplit[1].trim();
	                                            }
	                                        }
	                                    } else {
	                                        // Cac vi vua cua trieu dai dang xet
	                                        Elements tableDatas = currentElement.select("tbody > tr > td:nth-child(2)");
	                                        if (tableDatas != null) {
	                                            for (Element td : tableDatas) {
	                                                td.select("sup").remove();
	                                                Element tdATag = td.selectFirst("a");
	                                                if (tdATag != null) {
	                                                    String kingName = tdATag.text();
//	                                                    System.out.println("King name: " + kingName);
	                                                    kingList.add(kingName);
	                                                }
	                                            }

	                                            // Lay cac thong tin nhu nguoi sang lap - 2, que huong - 3, kinh do - 4
	                                            for (int j = 1; j < tableRows.size(); ++j) {
	                                                // Gia tri cua the td dau tien la ten cua trieu dai
	                                                Element firstTd = tableRows.get(j).selectFirst("td");
	                                                if (firstTd != null) {
	                                                    String eraValue = firstTd.text();
//	                                                    System.out.println("Current era: " + eraValue);
	                                                    if (!currentEra.equals("")) {
	                                                        if (eraValue.contains(currentEra)) {
	                                                            Elements currentRowTds = tableRows.get(j).select("td");
	                                                            if (currentRowTds != null) {
	                                                                // Loai bo chu thich
	                                                                currentRowTds.get(1).select("sup").remove();
	                                                                currentRowTds.get(2).select("sup").remove();
	                                                                currentRowTds.get(3).select("sup").remove();
	                                                                founder = currentRowTds.get(1).text();
	                                                                hometown = currentRowTds.get(2).text();
	                                                                location = currentRowTds.get(3).text();
	                                                            }
	                                                            break;
	                                                        }
	                                                    }
	                                                }
	                                            }

	                                            // Reset lai
	                                            if (link != null) {
	                                                System.out.print("\nLink: " + link);
	                                                try {
	                                                    Document detailDoc = Jsoup.connect(link).timeout(120000).get();
//	                                                #mw-content-text > div.mw-parser-output > p:nth-child(2)
	                                                    Element detailDiv = detailDoc
	                                                            .selectFirst("#mw-content-text > div.mw-parser-output");

	                                                    if (detailDiv != null) {
	                                                        for (Element ele : detailDiv.children()) {
	                                                            if (ele.tagName().equals("p")) {
	                                                                if (ele.text() == "") continue;
	                                                                // Lay ra doan van dau tien
	                                                                ele.select("sup").remove();
	                                                                String firstPContent = ele.text();

	                                                                // Loc thong tin tu doan van ban dau tien
	                                                                // Lay mo ta ngan gon cua di tich
	                                                                // Loai bo doan trong ngoac dau tien cho chac
	                                                                int firstOpenParen = firstPContent.indexOf("(");
	                                                                int firstCloseParen = firstPContent.indexOf(")");
	                                                                if (firstOpenParen != -1 && firstCloseParen != -1) {
	                                                                    String firstParen = firstPContent.substring(firstOpenParen + 1, firstCloseParen);
	                                                                    if (firstParen.contains("là")) {
	                                                                        firstPContent = firstPContent.substring(firstCloseParen + 1);
	                                                                    }
	                                                                }

	                                                                boolean outLoop = true;
	                                                                while (outLoop) {
	                                                                    // kiem tra sau chu "la" la gi?
	                                                                    int start = firstPContent.indexOf("là"); // tra ve vi tri chu l cua "la" dau tien tim thay
	                                                                    if (start != -1 && start < firstPContent.length() - 3) {
	                                                                        // Neu la chu in hoa || hoac la con, lang? ... => bo TH chu la nay di :v
	                                                                        if (
	                                                                                Character.isUpperCase(firstPContent.charAt(start + 3)) ||
	                                                                                        firstPContent.charAt(start + 2) != ' '
	                                                                        ) {
	                                                                            // Con truong hop la mot
	                                                                            // Truong hop chu lam
	                                                                            if (firstPContent.charAt(start + 2) == 'm' && firstPContent.charAt(start + 3) == ' ') {
	                                                                                outLoop = false;
	                                                                            } else firstPContent = firstPContent.substring(start + 3);
	                                                                        } else outLoop = false;
	                                                                    } else outLoop = false;
	                                                                }

	                                                                System.out.print("\n" + firstPContent);

	                                                                p = Pattern.compile("(là|bao gồm|nơi)[^.]*([.]|$)", Pattern.CASE_INSENSITIVE);
	                                                                m = p.matcher(firstPContent);

	                                                                if (m.find()) {
	                                                                    String result = m.group(0);
	                                                                    overview = result.substring(0, result.length() - 1);
	                                                                }
	                                                                break;
	                                                            }
	                                                        }
	                                                    }
	                                                } catch (IOException err) {
	                                                    err.printStackTrace();
	                                                }

	                                                link = null;
	                                            }

	                                            System.out.println("\nEra name: " + currentEra);
	                                            System.out.println("Belongs to timestamp: " + currentTimeStamp);
	                                            System.out.println("Hometown: " + hometown);
	                                            System.out.println("Founder: " + founder);
	                                            System.out.println("Location: " + location);
	                                            System.out.println("Time: " + eraTime);
	                                            System.out.println("Overview: " + overview);
	                                            System.out.print("List of kings: [");
	                                            if (kingList.size() > 0) {
	                                                for (int t = 0; t < kingList.size(); ++t) {
	                                                    if (t < kingList.size() - 1) {
	                                                        System.out.print(kingList.get(t) + ", ");
	                                                    } else System.out.print(kingList.get(t) + "]\n");
	                                                }
	                                            } else {
	                                                System.out.print("]\n");
	                                            }
	                                            // Tao object
	                                            new Era(
	                                                currentEra,
	                                                currentTimeStamp,
	                                                hometown,
	                                                founder,
	                                                location,
	                                                eraTime,
	                                                overview,
	                                                kingList
	                                            );

	                                            // Reset lai overview
	                                            if (!overview.equals("Chưa rõ")) overview = "Chưa rõ";

	                                            // Reset lai cac mang chua vua
	                                            kingList.clear();
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }

	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    public static void crawlData() {
	        crawlFromWiki();
	    }
	
    }
