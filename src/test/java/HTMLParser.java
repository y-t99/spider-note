import javafx.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author yuanyuan
 * #create 2020-02-18-15:27
 */
public class HTMLParser {
    private static Map<String, List<String>> map =new HashMap<>(7);
    private static List<Pair<String,String>> postParameter=new ArrayList<>(35);
    public static void main(String[] args) throws IOException {
        String flieNmae=HTMLParser.class.getClassLoader().getResource("class_schedule.txt").getPath();
        File file=new File(flieNmae);
        Document document= Jsoup.parse(file,"utf-8");
        _getClassSchdule(document);
        System.out.println(map);
    }

    private static void _getPostParameter(Document document) {
        final Elements elements = document.select("table#kbtable input");
        for (Element element:
             elements) {
            String name=element.attr("name").trim();
            String value=element.attr("value").trim();
            postParameter.add(new Pair<>(name,value));
        }
    }

    private static void _getClassSchdule(Document document) {
        final Elements table = document.select("table#kbtable > tbody >tr");
        final Iterator<Element> trs = table.iterator();
        trs.next();
        while(trs.hasNext()){
            final Element tr =trs.next();
            final String th = tr.getElementsByTag("th").text().trim();
            if ("".equals(th) || "M".equals(th)){
                continue;
            }
            if("备注:".equals(th)){
                map.put("备注", Collections.singletonList(tr.getElementsByTag("td").text()));
                continue;
            }
            final Elements tds = tr.select("td div.kbcontent");
            final Iterator<Element> iterator = tds.iterator();
            List<String> list=new ArrayList<>(7);
            map.put(th,list);
            while(iterator.hasNext()){
                final Element td = iterator.next();
                list.add(td.text());
            }
        }
    }
}
