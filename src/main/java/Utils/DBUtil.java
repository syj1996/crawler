package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DBUtil {
    private static  String url="jdbc:mysql://localhost:3306/crawler?characterEncoding=utf8&serverTimezone=UTC";
    private static final String user="root";
    private static final String password="123456";
    private static Connection conn=null;
    private static PreparedStatement ps=null;
    private static ResultSet rs=null;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //init();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection createConn(){
        try {
            return DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ResultSet executeQuery(String sql, Object[] params){
        conn=createConn();
        try {
            assert conn != null;
            ps=conn.prepareStatement(sql);
            prepare(ps,params);
            rs=ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public static boolean executeUpdate(String sql, Object[] params){
        conn=createConn();
        int i=0;
        try {
            ps=conn.prepareStatement(sql);
            prepare(ps,params);
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return i > 0;
    }

    public static int getCount(String sql, Object[] params){
        conn=createConn();
        int i=0;
        try {
            rs=executeQuery(sql,params);
            if(rs.next()){
                i=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return i;
    }
    private static void prepare(PreparedStatement ps, Object[] params) throws SQLException {
        if(params!=null){
            for(int i=0;i<params.length;i++){
                ps.setObject(i+1,params[i]);
            }
        }
    }
    public static void closeAll(){
        try {
            if(rs!=null)
                rs.close();
            if(ps!=null)
                ps.close();
            if(conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<HashMap<String, String>> getHashmap(String sql, Object[] params){
        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
        ResultSet rs = executeQuery(sql, params);
        try {
            rs.last();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()){
                HashMap<String, String> hashMap = new HashMap<String,String>();
                for(int i=1;i<=columnCount;i++){
                    hashMap.put(rsmd.getColumnLabel(i),rs.getString(i));
                }
                list.add(hashMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll();
        }
        return list;
    }

}
