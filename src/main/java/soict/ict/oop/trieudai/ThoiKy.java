package soict.ict.oop.trieudai;

import org.jsoup.select.Elements;

import java.io.IOException;

public class ThoiKy {
    private final String title, description, code, timeInterval;
    ThoiKy(String title, String description, String code, String timeInterval){
        this.title = title;
        this.description = description;
        this.code = code;
        this.timeInterval = timeInterval;
    }
    public String getCode(){
        return this.code;
    }
    @Override
    public String toString() {

        String scrape = "";
        scrape += title;
        scrape += '\n';
        scrape += code;
        scrape += '\n';
        scrape += description;
        scrape += '\n';
        scrape += timeInterval;
        scrape += '\n';
        return scrape;
    }

}
