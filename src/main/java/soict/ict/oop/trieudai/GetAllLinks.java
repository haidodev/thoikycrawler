package soict.ict.oop.trieudai;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAllLinks {
    private final String baseUrl;
    public GetAllLinks(String url){
        this.baseUrl = url;
    }
    public List<String> getAllLinks_() throws IOException {
        List<String> allLinks = new ArrayList<>();
        Document currentPage = Jsoup.connect(baseUrl).get();
        Elements allAnchorTags = currentPage.select(".toccolours tbody tr a");
        for (Element anchorTag : allAnchorTags) {
            String url = anchorTag.absUrl("href");
            String pattern = "\\(\\d+-\\d+\\)";
            String anchorText = anchorTag.text();
            boolean isMatch = anchorText.matches(pattern);
            if (isMatch) continue;

            if (url.equals("")) continue;
            //System.out.println(anchorTag.attr("title")+ ": " + anchorTag.absUrl("href"));
            //System.out.println(anchorTag.text()+ ": " + anchorTag.absUrl("href"));
            allLinks.add(url);

        }
        return allLinks;
    }
    public List<String> getAllLinks() throws IOException {
        List<String> allLinks = new ArrayList<>();
        Document currentPage = Jsoup.connect(baseUrl).get();
        Element selectedRow = currentPage.select(".toccolours > tbody > tr").get(1);
        Elements allAnchorTags = selectedRow.select("tr a");
        for (Element anchorTag : allAnchorTags) {
            System.out.println(anchorTag.text());
            String url = anchorTag.absUrl("href");
            String pattern = "\\(\\d+ – \\d+\\)";
            String anchorText = anchorTag.text();
            boolean isMatch = anchorText.matches(pattern);
            if (isMatch) continue;

            if (url.equals("")) continue;
            //System.out.println(anchorTag.attr("title")+ ": " + anchorTag.absUrl("href"));
            //System.out.println(anchorTag.text()+ ": " + anchorTag.absUrl("href"));
            allLinks.add(url);

        }
        return allLinks;
    }
    public List<String> allTimePeriods() throws IOException {
        List<String> allPeriods = new ArrayList<>();
        String curURL = "https://vi.wikipedia.org/wiki/C%C3%A1c_cu%E1%BB%99c_chi%E1%BA%BFn_tranh_Vi%E1%BB%87t_Nam_tham_gia";

        Document currentPage = Jsoup.connect(curURL).get();
        Element tableOfContent = currentPage.select("ul.vector-toc-contents").get(0);
        Elements allLITags = tableOfContent.select("> li");
        for (Element anchorTag : allLITags) {
            String url = "";
            allPeriods.add(url);

        }
        return allPeriods;

    }
}
