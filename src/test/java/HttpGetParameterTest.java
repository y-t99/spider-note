import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author yuanyuan
 * #create 2020-02-01-12:17
 */
public class HttpGetParameterTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpGetParameterTest.class);

    private static final int SUCCESS_CODE=200;
    @Test
    public void test() throws URISyntaxException {
        //1、创建客户端连接
        CloseableHttpClient client = HttpClients.createDefault();
        //1-2 设置参数
        URIBuilder uri=new URIBuilder("http://www.people.com.cn/");
        uri.addParameter("","");
        //2、创建get方法，设置访问路径
        HttpGet getMethod=new HttpGet(uri.build());
        //3、发起请求
        try(CloseableHttpResponse response = client.execute(getMethod)) {
            //4、解析响应
            if (response.getStatusLine().getStatusCode()==SUCCESS_CODE){
                //4.1、响应体
                final String entity = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(entity.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
