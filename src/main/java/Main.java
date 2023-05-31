import soict.ict.oop.trieudai.GetAllLinks;
import soict.ict.oop.trieudai.GetInfoFromLink;
import soict.ict.oop.trieudai.ThoiKy;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        String srcUrl = "https://vi.wikipedia.org/wiki/Vi%E1%BB%87t_Nam_th%E1%BB%9Di_ti%E1%BB%81n_s%E1%BB%AD";
        GetAllLinks gal = new GetAllLinks(srcUrl);
        List<String> allLinks = gal.getAllLinks();
        HashSet<String> removeDuplicate = new HashSet<>();
        for (String url : allLinks){
            GetInfoFromLink curLink = new GetInfoFromLink(url);
            ThoiKy curTK = curLink.generateThoiKy();
            if (curTK == null || removeDuplicate.contains(curTK.getCode())) continue;
            removeDuplicate.add(curTK.getCode());
            System.out.println(curTK);


        }
    }
}
