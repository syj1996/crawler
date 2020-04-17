package Control;

import Bean.commonweixin;
import Bean.scipherInfo;
import Dao.commonweixinDao;
import Dao.scipherInfoDao;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.naming.Name;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class businessScipher {
    //每次修改 的数据
    private static String cookie="JSESSIONID=0F326BBDCC4CEF6208A2E88FE600AB30; yfx_c_g_u_id_10007895=_ck20041712371710731736294197072; yfx_f_l_v_t_10007895=f_t_1587098237067__r_t_1587098237067__v_t_1587104376242__r_c_0";
    private static String UserAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763";
    private static PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();
    private static HttpPost httpPost=null;
    private static HttpGet httpGet=null;
    private static CloseableHttpClient httpClient=null;
    private static Document document=null;
    private static CloseableHttpResponse response=null;
    private static String html=null;
    private static Elements elements=null;
    private static Element element=null;
    private static String fileName=null;
    private static String url=null;
    private static final List<String> headerLi=new ArrayList<String>();
    static {
        headerLi.add("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
        headerLi.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36");
        headerLi.add("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:30.0) Gecko/20100101 Firefox/30.0");
        headerLi.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/537.75.14");
        headerLi.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Win64; x64; Trident/6.0)");
        headerLi.add("Mozilla/5.0 (Windows; U; Windows NT 5.1; it; rv:1.8.1.11) Gecko/20071127 Firefox/2.0.0.11");
        headerLi.add("Opera/9.25 (Windows NT 5.1; U; en)");
        headerLi.add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
        headerLi.add("Mozilla/5.0 (compatible; Konqueror/3.5; Linux) KHTML/3.5.5 (like Gecko) (Kubuntu)");
        headerLi.add("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.0.12) Gecko/20070731 Ubuntu/dapper-security Firefox/1.5.0.12");
        headerLi.add("Lynx/2.8.5rel.1 libwww-FM/2.14 SSL-MM/1.4.1 GNUTLS/1.2.9");
        headerLi.add("Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.7 (KHTML, like Gecko) Ubuntu/11.04 Chromium/16.0.912.77 Chrome/16.0.912.77 Safari/535.7");
        headerLi.add("Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:10.0) Gecko/20100101 Firefox/10.0 ");

    }
    private static int sizeLi=headerLi.size();
    private static JSONArray jsonArray;
    private static JSONObject jsonObject;
    private static List<scipherInfo> scipherLi=new ArrayList<scipherInfo>();
    public static void main(String[] args) throws URISyntaxException, InterruptedException, SQLException {
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        url="http://www.oscca.gov.cn/app-zxfw/cpxx/symmcp2.jsp";
        URIBuilder uriBuilder=new URIBuilder(url);
        for (int i = 1; i <89 ; i++) {
            uriBuilder.setParameter("curentpage",String.valueOf(i));
            html=getHttpGetHTML(uriBuilder.toString());
            doDB(html);
            System.out.println(uriBuilder);
            Thread.sleep(3000);
        }
      }

    private static void doDB(String content) throws SQLException {
        document= Jsoup.parse(content);
        elements = document.select(".main>.main_content>.BlueTable>table>tbody>tr");
        int size = elements.size();
        scipherLi.clear();
        for (int i = 1; i <size ; i++) {
            element = elements.get(i);
            int len = element.childNodeSize();
            scipherLi.add(new scipherInfo(element.child(0).text(),element.child(1).text(),element.child(2).text(),element.child(3).text(),element.child(4).text(),element.child(5).text()));
        }
        scipherInfoDao.addList(scipherLi);
        System.out.println(scipherLi.toString());
    }
    private static void loadFile(String url,String fileName) throws IOException {
        FileUtils.copyURLToFile(new URL(url),new File(fileName));
    }
    private static RequestConfig getRequestConfig() {
        RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(1000) // 创建连接的最长时间，毫秒
                .setConnectionRequestTimeout(500) // 获取连接的最长时间，毫秒
                .setSocketTimeout(1000 * 10) // 数据传输的最长时间，毫秒
                .build();
        return config;
    }
    private static HttpPost getHttpPost(String url){
        HttpPost httpPost=new HttpPost (url);
        httpPost.setHeader("User-Agent",UserAgent);
        httpPost.setHeader("cookie",cookie);
        httpPost.setConfig(getRequestConfig());
        return httpPost;
    }
    private static HttpGet getHttpGet(String url){
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("User-Agent",UserAgent);
        httpGet.setHeader("cookie",cookie);
        httpGet.setConfig(getRequestConfig());
        return httpGet;
    }
    private static CloseableHttpClient getCloseableHttpClient(){
        return HttpClients.custom().setConnectionManager(cm).build();

    }
    private static  String getHttpGetHTML(String url){
        httpClient = getCloseableHttpClient();
        httpGet=getHttpGet(url);
        try {
            response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode()==200){
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                return html;
            }
            else return null;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    private static  String getHttpPostHTML(String url, List<NameValuePair> params){
        try {
            httpClient = getCloseableHttpClient();
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf-8");
            httpPost=getHttpPost(url);
            httpPost.setEntity(formEntity);

            System.out.println(formEntity.toString());
           response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                html = EntityUtils.toString(response.getEntity(), "utf-8");
                return html;
            }
            else return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;

    }
    public static String formatTime(String createTime) {

// 将微信传入的CreateTime转换成long类型,再乘以1000
        long msgCreateTime = Long.parseLong(createTime) * 1000L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(msgCreateTime));
    }

}
