import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author yuanyuan
 * #create 2020-02-01-12:17
 */
public class HttpPostTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostTest.class);

    private static final int SUCCESS_CODE=200;
    @Test
    public void test(){
        //1、创建客户端连接
        CloseableHttpClient client = HttpClients.createDefault();
        //2、创建post方法，设置访问路径
        HttpPost postMethod=new HttpPost("http://www.people.com.cn/");
        //3、发起请求
        try(CloseableHttpResponse response = client.execute(postMethod)) {
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
