package util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author yuanyuan
 * #create 2020-02-01-10:59
 */
public class HtmlClientUtils {
    /**
     * 状态码
     */
    private class Code {
        /**
         * 响应成功
         */
        static final int SUCCESS_CODE = 200;
    }

    /**
     * 请求方法
     */
    private enum Method {
        /**
         * get请求
         */
        GET,
        /**
         * post请求
         */
        POST
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlClientUtils.class);

    /**
     * 连接池
     */
    private static final PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

    /*工具包初始化*/
    static {

    }

    public static CloseableHttpResponse doGet(HttpGet get) throws IOException {
        return doGet(get.getURI().getRawPath(),get);
    }

    public static CloseableHttpResponse doPost(HttpPost post) throws IOException {
        return doPost(post.getURI().getRawPath(),post);
    }

    public static CloseableHttpResponse doGet(String url, HttpGet get) throws IOException {
        return _request(url, get);
    }

    public static CloseableHttpResponse doPost(String url, HttpPost post) throws IOException {
        return _request(url,post);
    }

    public static Object doGet(String url, List<NameValuePair> parameters) {
        return _request(Method.GET,url,parameters);
    }

    public static Object doPost(String url, List<NameValuePair> parameters) {
        return _request(Method.POST,url,parameters);
    }

    private static Object _request(Method method, String url, List<NameValuePair> parameters) {
        //1、获取客户端
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(pool).build();
        //2、判断方法
        HttpRequestBase request = null;
        if (method == Method.GET) {
            try {
                //2.1、参数处理
                URIBuilder uri = new URIBuilder(url);
                uri.addParameters(parameters);
                request = new HttpGet(uri.build());
            } catch (URISyntaxException e) {
                LOGGER.error("url错误", e);
                return null;
            }
        } else if (method == Method.POST) {
            //2.1、参数处理
            StringEntity entity;
            try {
                entity = new UrlEncodedFormEntity(parameters, "UTF-8");
                request = new HttpPost(url);
                ((HttpPost) request).setEntity(entity);
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("post参数错误", e);
            } catch (Exception e) {
                LOGGER.error("未知错误", e);
                return null;
            }
        }
        //3、请求
        try {
            final CloseableHttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == Code.SUCCESS_CODE) {
                //4、解析请求结果
                final String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                return Jsoup.parse(content);
            } else {
                LOGGER.info("请求失败");
                return null;
            }
        } catch (IOException e) {
            LOGGER.error("请求失败", e);
            return null;
        }
    }

    private static CloseableHttpResponse _request(String url, HttpRequestBase get) throws IOException {
        CloseableHttpClient client=null;
        try {
            client= HttpClients.custom().setConnectionManager(pool).build();
            get.setURI(URI.create(url));
            return client.execute(get);
        } finally {
            if(client!=null) {
                client.close();
            }
        }
    }
}
