package soict.ict.oop.trieudai;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.StringUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetInfoFromLink {
    private final String currentUrl;
    public GetInfoFromLink(String url) {
        currentUrl = url;


    }
    public ThoiKy generateThoiKy() throws IOException {
        Document currentPage = Jsoup.connect(currentUrl).get();
        if (!isValidLink(currentPage)) {
            return null;
        }
        String title = getPageTitle(currentPage);
        String code = getThoiKyCode(title);
        String description = getDescription(currentPage);
        String timeInterval = getYear(currentPage);
        return new ThoiKy(title, code, description, timeInterval);
    }
    public String getPageTitle(Document doc){
        String[] splitedString = doc.title().split("–");
        StringBuilder newTitle = new StringBuilder();
        for (int i = 0; i < splitedString.length - 1; ++i){
            newTitle.append(splitedString[i]);
            if (i < splitedString.length - 2) newTitle.append("–");
        }
        return newTitle.toString().strip();
    }
    public String getDescription(Document doc){
        Elements allText = doc.select("div.mw-parser-output");
        Elements allPTags = allText.select("> p");
        String description;
        for (Element pTag : allPTags){
            if (pTag.text().equals("")) continue;

            if (pTag.text().length() < 100) continue;
            //if (pTag.attr("class").equals("note")) continue;
            return pTag.text();
        }
        return "No Description";
    }
    public String getYear(Document doc){
        Element infobox = doc.select(".infobox").get(0);
        Elements allRows = infobox.select("tr");
        for (Element row : allRows){
            String text = row.text();
            text = text.replaceAll("–", "-");
            String pattern = "\\d+( TCN)?-\\d+( TCN)?";
            Pattern regexPattern = Pattern.compile(pattern);
            Matcher matcher = regexPattern.matcher(text);
            if (matcher.find()) {
                return matcher.group();
            }

        }
        return null;
    }
    public String getThoiKyCode(String title){
        String code = StringUtils.removeAccent(title);
        code = code.toLowerCase();
        code = code.replaceAll("\\s", "-");
        return code;
    }
    public JSONObject getInfoFromInfobox(Document doc){
        Elements newsHeadlines = doc.select("tbody tr");
        JSONObject json = new JSONObject();
        for (Element headline : newsHeadlines) {
            if (headline.children().size() != 2) continue;
            String attributeName = "";
            boolean isAttributeValue = false;
            for (Element elm : headline.children()){
                String content = elm.text();//.replaceAll("[^A-Za-z0-9]", "");
                if (isAttributeValue){
                    json.put(attributeName, content);
                }
                attributeName = content;
                isAttributeValue = true;

            }
            //System.out.println();
        }
        return json;
    }
    public boolean isValidLink(Document currentPage){
        Elements infobox = currentPage.select(".infobox");
        return infobox.size() > 0;
    }
    @Override
    public String toString() {
        Document currentPage = null;
        try {
            currentPage = Jsoup.connect(currentUrl).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String scrape = "";

        Elements infobox = currentPage.select(".infobox");
        if (infobox.size() > 0) scrape += getYear(currentPage);
        else scrape += "Not found";
        return scrape;
    }
}
