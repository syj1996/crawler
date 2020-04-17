package Control;

import Bean.commonweixin;
import Dao.commonweixinDao;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class weiXin {
    //每次修改 的数据
    private static String fakeid="MzI5NTM4OTQ5Mg==";
    private static String token="915879529";
    private static String cookie="noticeLoginFlag=1; remember_acct=2768730013%40qq.com; pgv_pvi=2356468736; data_ticket=dxBZuOqRrbKMxOu2UBcTkkxQIJ8ZJfY1+M1eAX7wWUjcqHOPQmVUagpyUCMxOQJQ; mm_lang=zh_CN; bizuin=3577856462; ua_id=kRAgkPmtnbcqDYOBAAAAAKf1HlSEYGb5XjEeFfSCQDo=; xid=; slave_sid=X0hDYkhKZ3c5ZGJfTmJkQUVKa0p0WFFxaUlYYzNSakdYR2J6XzI3VDg1YzAwQXVocGM1RURoaHZMTnNBNzVvRFFUYnl0U0J3OGhST050UzhOaWJpZjFNb2lWMU5keGhWcTBzTlE5VlIyUENrUE1hd0RpSTljblVXYnZ3OWxFRFpJNldWaEIxVE8yMGtMbU9C; rand_info=CAESIKV/Q15z+l74gpSJ2AgCX8RTSTytIemy2mVUYVi9nYXB; data_bizuin=3577856462; slave_bizuin=3577856462; slave_user=gh_2e8cdc68a803; openid2ticket_ozsFB1cTp7YverjZfEeO1cgCzZYM=zycMBnu1y4VtANZFs1/RRoGUxCPUuEvoVJBuScdUKMQ=";
    private static String UserAgent="User-Agent\",\"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763";
    private static int pageData=45;
    private static PoolingHttpClientConnectionManager cm=new PoolingHttpClientConnectionManager();
    private static HttpGet httpGet=null;
    private static CloseableHttpClient httpClient=null;
    private static Document document=null;
    private static String html=null;
    private static Elements elements=null;
    private static String fileName=null;
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
    private static List<commonweixin> weiLi=new ArrayList<commonweixin>();
    public static void main(String[] args) throws URISyntaxException, InterruptedException, SQLException {
        cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        cm.setDefaultMaxPerRoute(10);
        String url="https://mp.weixin.qq.com/cgi-bin/appmsg";
        final URIBuilder uriBuilder=new URIBuilder(url);
        uriBuilder.setParameter("action","list_ex");
        uriBuilder.setParameter("ajax","1");
        uriBuilder.setParameter("count","5");
        uriBuilder.setParameter("f","json");
        uriBuilder.setParameter("fakeid",fakeid);
        uriBuilder.setParameter("lang","zh_CN");
        uriBuilder.setParameter("query","");
        //token与cookie 对应
        uriBuilder.setParameter("token",token);
        uriBuilder.setParameter("type","9");
        for(int i=0;i<pageData;i++){
            uriBuilder.setParameter("begin",String.valueOf(i*5));
            String httpHTML = getHttpHTML(uriBuilder.toString());
            System.out.println(uriBuilder.toString());
           // System.out.println(httpHTML);
            jsonObject=new JSONObject(httpHTML);
            jsonArray=jsonObject.getJSONArray("app_msg_list");
            int len=jsonArray.length();
            weiLi.clear();
            for(int j=0;j<len;j++){
                jsonObject=jsonArray.getJSONObject(j);
                weiLi.add(new commonweixin(jsonObject.getString("aid"),jsonObject.getString("link"),formatTime(String.valueOf(jsonObject.getLong("create_time"))),formatTime(String.valueOf(jsonObject.getLong("update_time"))),jsonObject.getString("digest"),String.valueOf((!jsonObject.has("is_original"))),jsonObject.getString("cover"),jsonObject.getString("title")));
                System.out.println(weiLi.get(j));
            }
            commonweixinDao.addList(weiLi);
            Thread.sleep(6000);
        }

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
    private static  String getHttpHTML(String url){
        httpClient = getCloseableHttpClient();
        httpGet=getHttpGet(url);
        CloseableHttpResponse response=null;
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
    public static String formatTime(String createTime) {

// 将微信传入的CreateTime转换成long类型,再乘以1000
        long msgCreateTime = Long.parseLong(createTime) * 1000L;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(msgCreateTime));
    }

}
