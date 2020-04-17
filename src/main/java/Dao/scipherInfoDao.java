package Dao;

import Bean.commonweixin;
import Bean.scipherInfo;
import Utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class scipherInfoDao {
    public static void addList(List<scipherInfo> li) throws SQLException {
        scipherInfo sci;
        Object[] o;
        String sql="insert into scipherinfo values(?,?,?,?,?,?)";
        String sql1="select * from scipherinfo where licence=?";
        Iterator<scipherInfo> it = li.iterator();
        while(it.hasNext()){
            sci=it.next();
            o=new Object[]{
                    sci.getSequence(),
                    sci.getProductType(),
                    sci.getProductName(),
                    sci.getCompany(),
                    sci.getComfirmTime(),
                    sci.getLicence()
            };
            ResultSet resultSet = DBUtil.executeQuery(sql1, new Object[]{sci.getLicence()});
            boolean a = resultSet.next();
            if(!a){
                boolean b = DBUtil.executeUpdate(sql, o);
                if(!b){
                    System.out.println(sci.toString());
                }
            }


        }
    }
}
