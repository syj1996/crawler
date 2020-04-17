package Control;

import Bean.commonweixin;
import Utils.DBUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loadFile {
    private static String create_time;
    private static String articleLink;
    private static String title;
    public static void main(String[] args) throws SQLException, IOException {
        String sql="select * from commonweixin";
        ResultSet rs= DBUtil.executeQuery(sql, new Object[]{});
        while (rs.next()){
            create_time=rs.getString("create_time");
            articleLink=rs.getString("articleLink");
            title=rs.getString("title");
            FileUtils.copyURLToFile(new URL(articleLink), new File("D:\\微信公从号爬取\\商密君\\" + create_time + "\\title.html"));
        }
    }

}
