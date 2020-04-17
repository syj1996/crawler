package Dao;

import Bean.commonweixin;
import Utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class commonweixinDao {
    public static void addList(List<commonweixin> li) throws SQLException {
        commonweixin co;
        Object[] o;
        String sql="insert into commonweixin values(?,?,?,?,?,?,?,?)";
        String sql1="select * from commonweixin where aid=?";
        Iterator<commonweixin> it = li.iterator();
        while(it.hasNext()){
            co=it.next();
            o=new Object[]{
                    co.getAid(),
                    co.getArticleLink(),
                    co.getCreate_time(),
                    co.getUpdate_time(),
                    co.getDigest(),
                    co.getOriginal(),
                    co.getCover(),
                    co.getTitle()
            };
            ResultSet resultSet = DBUtil.executeQuery(sql1, new Object[]{co.getAid()});
            boolean a = resultSet.next();
            if(!a){
                boolean b = DBUtil.executeUpdate(sql, o);
                if(!b){
                    System.out.println(co.toString());
                }
            }


        }
    }
}
