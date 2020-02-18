import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-01-12:17
 */
public class HttpPostParameterTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostParameterTest.class);

    private static final int SUCCESS_CODE=200;
    @Test
    public void test() throws URISyntaxException, UnsupportedEncodingException {
        //1、创建客户端连接
        CloseableHttpClient client = HttpClients.createDefault();
        //2、创建post方法，设置访问路径
        HttpPost postMethod=new HttpPost("");
        //2-3表单数据
        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("",""));
        StringEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        postMethod.setEntity(entity);
        //3、发起请求
        try(CloseableHttpResponse response = client.execute(postMethod)) {
            //4、解析响应
            if (response.getStatusLine().getStatusCode()==SUCCESS_CODE){
                //4.1、响应体
                final String entityContent = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(entityContent.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
