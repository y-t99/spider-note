import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author yuanyuan
 * #create 2020-02-01-19:07
 */
public class JSoupTest {
    @Test
    public void test() throws IOException {
        String flieNmae=JSoupTest.class.getClassLoader().getResource("class_schedule.txt").getPath();
        File file=new File(flieNmae);
        Document document=Jsoup.parse(file,"utf-8");
        final Element form = document.getElementById("Form1");
        final Element table = form.getElementById("kbtable");
        final Elements classes = table.getElementsByClass("kbcontent");
        for (Element element:
             classes) {
            final String text = element.text();
            if ("".equals(text.trim())){
                continue;
            }
            System.out.println(text);
        }
    }
}
