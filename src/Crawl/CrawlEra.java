package Crawl;

import Model.Era;
import Crawl.Crawl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CrawlEra extends Crawl {

    public static void CrawlFromWeb(){
        try{
            Document doc = Jsoup.connect("https://vi.wikipedia.org/wiki/Vua_Việt_Nam").timeout(120000).get();
            Element contenDiv = doc.selectFirst("div[class=mw-parser-output]");
            Elements contenDivChild = ((Element) contenDiv).children();

            Element wikiTable = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(91)");
            Elements tableRows = wikiTable.select("tr");
            System.out.println("Number of table row: " + tableRows.size());


            for(int i = 0; i < contenDivChild.size(); i++){
                Element e = contenDivChild.get(i);
                if(e.tagName().equals("h2")){
                    Element titleSpan = e.selectFirst("span[class=mw-headline]");

                    if(titleSpan != null){
                        String titleSpanContent = titleSpan.text();

                        Pattern p = Pattern.compile("(Thời|Bắc).*", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(titleSpanContent);

                        if(m.find()){
                            String currentTimeStamp = titleSpanContent;
                            String currentEra = ""; // Trieu dai dang xet
                            String founder = "Chưa rõ"; // Nguoi sang lap - vi vua dau tien?
                            String location = "Chưa rõ"; // Dia chi kinh do - neu co
                            String hometown = "Chưa rõ"; // Que huong - neu co
                            String eraTime = "Chưa rõ"; // Thoi gian cua trieu dai
                            String overview = "Chưa rõ"; // Mo ta cua trieu dai
                            String link = null;
                            ArrayList<String> kingList = new ArrayList<>();

                            while (!contenDivChild.get(i + 1).tagName().equals("h2")) {
                                i++;
                                Element currentElement = contenDivChild.get(i);
                                if(currentElement.tagName().equals("h3")){
                                    Element eraNameElement = currentElement.selectFirst("span[class=mw-headline]");
                                    if(eraNameElement != null){
                                        Element aTag = eraNameElement.selectFirst("a");
                                        if(aTag != null){
                                            link ="http://vi.wikipeida.org" + aTag.attr("href");
                                        }
                                        String eraNameEleContent = eraNameElement.text();
                                        int countParen = (int) eraNameEleContent.chars().filter(c -> c == '(').count();

                                        if(countParen == 0){
                                            currentEra = eraNameEleContent;
                                        }
                                        if(countParen == 1){
                                            int startParen = eraNameEleContent.indexOf("(");
                                            int endParen = eraNameEleContent.indexOf(")");
                                            currentEra = eraNameEleContent.substring(0,startParen - 1).trim();
                                            eraTime = eraNameEleContent.substring(startParen + 1, endParen -1).trim();

                                        }

                                        if(countParen == 2){
                                            int startParenFirst = eraNameEleContent.indexOf("(");
                                            int endParenFirst = eraNameEleContent.indexOf(")");
                                            int startParenSe = eraNameEleContent.lastIndexOf("(");
                                            int endParense = eraNameEleContent.lastIndexOf(")");

                                            currentEra = eraNameEleContent.substring(0, startParenFirst -1);
                                            String FirstParenContent = eraNameEleContent.substring(startParenFirst, endParenFirst -1);
                                            String SeParenContent = eraNameEleContent.substring(startParenSe + 1, endParense -1);
                                            String[] FirstParenSlip = FirstParenContent.split("–");
                                            String[] SeParenSlip = SeParenContent.split("–");
                                            if(FirstParenSlip.length < 2){
                                                FirstParenSlip = FirstParenContent.split("-");
                                            }
                                            if(SeParenSlip.length < 2){
                                                SeParenSlip = SeParenContent.split("-");
                                            }
                                            eraTime = FirstParenSlip[0].trim() + "-" + SeParenSlip[2];

                                        }
                                    }
                                } else if (currentElement.tagName().equals("table")) {
                                    if(currentElement.attr("cellpadding").equals("2")||
                                            (currentElement.hasClass("wikitable")
                                                    &&!currentElement.hasAttr("cellpadding"))){
                                        Elements rows = doc.select("tbody tr");
                                        for(Element row : rows){
                                            Element EraName =row.select("td:eq(1)").first();
                                            Element TimeRow = row.select("td:eq(2)").first();
                                            String TimeString = TimeRow.text();

                                            int countParen = (int) TimeString.chars().filter(c -> c == '(').count();

                                            if(countParen == 0){
                                            }
                                            if(countParen == 1){

                                                int StartParen = TimeString.indexOf("(");
                                                int EndParen = TimeString.indexOf(")");
                                                eraTime = TimeString.substring(StartParen + 1, EndParen -1).trim();
                                            }
                                            if(countParen == 2){
                                                int StartParenFirst = TimeString.indexOf("(");
                                                int EndParenFirst = TimeString.indexOf(")");
                                                int StartParenSe = TimeString.lastIndexOf("(");
                                                int EndParenSe = TimeString.lastIndexOf(")");
                                                String FirstParen = TimeString.substring(StartParenFirst, EndParenFirst -1);
                                                String SeconParen = TimeString.substring(StartParenSe, EndParenSe -1 );

                                                String[] FirstParenT = FirstParen.split("–");
                                                String[] SeconParenT = SeconParen.split("–");
                                                if(FirstParenT.length < 2){
                                                    FirstParenT = FirstParen.split("-");
                                                }
                                                if(SeconParenT.length < 2){
                                                    SeconParenT = SeconParen.split("-");
                                                }
                                                eraTime = FirstParenT[0].trim() + "-" + SeconParenT[2];
                                            }
                                        }
                                    }

                                            else {

                                                Elements tableDatas = currentElement.select("tbody > tr > td:nth-child(2)");
                                                if (tableDatas != null) {
                                                    for (Element td : tableDatas) {
                                                        td.select("sup").remove();
                                                        Element tdATag = td.selectFirst("a");
                                                        if (tdATag != null) {
                                                            String kingName = tdATag.text();
//
                                                            kingList.add(kingName);
                                                        }
                                                    }
                                                    for(int j = 1; j < tableRows.size(); ++j) {
                                                        Element firstTd = tableRows.get(j).selectFirst("td");
                                                        if (firstTd != null) {
                                                            String eraValue = firstTd.text();
                                                            if (!currentEra.equals("")) {
                                                                if (eraValue.contains(currentEra)) {
                                                                    Elements currentRowTds = tableRows.get(j).select("td");
                                                                    if (currentRowTds != null) {
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
                                                        if(link != null){
                                                            System.out.print("\nLink: " + link);
                                                            try {
                                                                Document detailDoc = Jsoup.connect(link).timeout(120000).get();

                                                                Element detaiDiv = detailDoc.selectFirst("#mw-content-text > div.mw-parser-output");

                                                                if (detaiDiv != null) {
                                                                    for (Element ele : detaiDiv.children()) {
                                                                        if (ele.tagName().equals("p")) {
                                                                            if (ele.text() == "") continue;

                                                                            ele.select("sup").remove();
                                                                            String firstPcontent = ele.text();

                                                                            int firstOpenParen = firstPcontent.indexOf("(");
                                                                            int firstCloseParen = firstPcontent.indexOf(")");
                                                                            if (firstOpenParen != -1 && firstCloseParen != -1) {
                                                                                String firstParen = firstPcontent.substring(firstCloseParen + 1, firstCloseParen);
                                                                                if (firstParen.contains("là")) {
                                                                                    firstPcontent = firstPcontent.substring(firstCloseParen + 1);
                                                                                }
                                                                            }

                                                                            boolean outLoop = true;
                                                                            while (outLoop) {
                                                                                int start = firstPcontent.indexOf("là"); // tra ve vi tri chu l cua "la" dau tien tim thay
                                                                                if (start != -1 && start < firstPcontent.length() - 3) {
                                                                                    // Neu la chu in hoa || hoac la con, lang? ... => bo TH chu la nay di :v
                                                                                    if (
                                                                                            Character.isUpperCase(firstPcontent.charAt(start + 3)) ||
                                                                                                    firstPcontent.charAt(start + 2) != ' '
                                                                                    ) {
                                                                                        // Con truong hop la mot
                                                                                        // Truong hop chu lam
                                                                                        if (firstPcontent.charAt(start + 2) == 'm' && firstPcontent.charAt(start + 3) == ' ') {
                                                                                            outLoop = false;
                                                                                        } else
                                                                                            firstPcontent = firstPcontent.substring(start + 3);
                                                                                    } else outLoop = false;
                                                                                } else outLoop = false;
                                                                            }
                                                                            System.out.print("\n" + firstPcontent);

                                                                            p = Pattern.compile("(là|bao gồm|nơi[^.]*([.]|$))", Pattern.CASE_INSENSITIVE);
                                                                            m = p.matcher(firstPcontent);

                                                                            if (m.find()) {
                                                                                String result = m.group(0);
                                                                                overview = result.substring(0, result.length() - 1);
                                                                            }
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                            } catch (IOException err) {
                                                                    err.printStackTrace();}
                                                                link = null;

                                                            }
                                                            System.out.println("Era name: " + currentEra);
                                                            System.out.println("Belongs to timestamp: " + currentTimeStamp);
                                                            System.out.println("Hometown: " + hometown);
                                                            System.out.println("Founder: " + founder);
                                                            System.out.println("Location: " +location);
                                                            System.out.println("Time: " + eraTime);
                                                            System.out.println("Overview: " + overview);
                                                            System.out.println("List of kings: [");
                                                            if(kingList.size() > 0){
                                                                for(int t = 0; t < kingList.size(); t++){
                                                                    if(t < kingList.size() - 1 ){
                                                                        System.out.print(kingList.get(t) + ", ");
                                                                    } else System.out.print(kingList.get(t) + "]\n");
                                                                }
                                                            } else {
                                                                System.out.print("]\n");
                                                            }
                                                            new Era(
                                                                    currentEra,
                                                                    currentTimeStamp,
                                                                    hometown,
                                                                    founder,
                                                                    location,
                                                                    eraTime,
                                                                    overview,
                                                                    kingList);

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
    public static void crawlData() {CrawlFromWeb();}
}