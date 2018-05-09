package UtilBean;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;

import businessmonitor.com.example.newbusinessmonitor.AbstractDataBean;
import businessmonitor.com.example.newbusinessmonitor.MarketDataBean;
import businessmonitor.com.example.newbusinessmonitor.MarketIndexBean;
import businessmonitor.com.example.newbusinessmonitor.SyntheticIndexBean;
import businessmonitor.com.example.newbusinessmonitor.UserInfoBean;

/**
 * 网络请求工具类，待添加检查权限功能
 */

public class DBUtil {
    public final static String TABLE1 = "market_index";   //指数表
    public final static String TABLE2 = "market_data";    //数据表
    public final static String TABLE3 = "synthetic_index";  //合成指数表
    public final static String TABLE4 = "user";   //用户表
    public final static String TABLE5 = "market";   //商会表
    public final static String TABLE6 = "market_management";    //商会管理表
    private static DBUtil dbUtil = null;
    private OnReceiveResultListener chartResultListener;
    private LinkedList<AsyncTask> list = new LinkedList<>();

    private DBUtil() { }

    public static DBUtil getHttpUtilInstance() {
        if (dbUtil == null)
            dbUtil = new DBUtil();
        return dbUtil;
    }

    public void checkAccount(String account,String password,OnReceiveResultListener checkResultListener){
        this.chartResultListener = checkResultListener;
        String sql="select * from "+TABLE4+" where user_name='"+account+"' and password='"+password+"'";
        HttpTask task = new HttpTask();
        task.execute(sql);
        list.add(task);
    }

    public void getChartData(String table, String sql, OnReceiveResultListener onReceiveResultListener) {
        this.chartResultListener = onReceiveResultListener;
        HttpTask task = new HttpTask();
        task.execute(table, sql);
        list.add(task);
    }

    public interface OnReceiveResultListener {
        void onReceived(ArrayList<AbstractDataBean> response);

        void onError();
    }

    private static class HttpTask extends AsyncTask<String, Void, ArrayList<AbstractDataBean>> {

        @Override
        protected ArrayList<AbstractDataBean> doInBackground(String... strings) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://120.77.252.208:3306/detecting_system", "aep", "aep");
                Log.d("DBUtil","连接成功");
                if (strings.length==1)
                    return queryUserData(connection,strings[0]);
                else
                    return queryChartData(connection, strings[0], strings[1]);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<AbstractDataBean> result) {
            if (dbUtil.chartResultListener != null)
                if (result != null)
                    dbUtil.chartResultListener.onReceived(result);
                else
                    dbUtil.chartResultListener.onError();
        }

        private ArrayList<AbstractDataBean> queryUserData(Connection connection,String sql){
            ArrayList<AbstractDataBean> as=new ArrayList<>();   //该列表记录了该用户可以查看哪些商会
            Statement stmt = null;
            ResultSet rs = null;
            int user_id;String user_name;boolean vip;
            try {
                stmt=connection.createStatement();
                rs=stmt.executeQuery(sql);
                if (rs.next()){
                    user_id=rs.getInt("user_id");
                    user_name=rs.getString("user_name");
                    vip=rs.getBoolean("vip");
                }else {
                    return as;    //不存在该用户,此时as的大小为0
                }
                rs=stmt.executeQuery("select "+TABLE5+".market_name,"+TABLE6+".* from "+TABLE5+
                        ","+TABLE6+" where "+TABLE5+".market_id="+TABLE6+".market_id and "+TABLE6+
                        ".user_id="+user_id);
                while (rs.next()){
                    UserInfoBean userInfoBean=new UserInfoBean();
                    userInfoBean.setUser_id(user_id);
                    userInfoBean.setUser_name(user_name);
                    userInfoBean.setMarket_id(rs.getInt("market_id"));
                    userInfoBean.setMarket_name(rs.getString("market_name"));
                    userInfoBean.setVip(vip);
                    boolean[] strings=new boolean[5];
                    strings[0]=rs.getBoolean("innovation_permission");
                    strings[1]=rs.getBoolean("environmental_permission");
                    strings[2]=rs.getBoolean("fastBatch_permission");
                    strings[3]=rs.getBoolean("logistics_permission");
                    strings[4]=rs.getBoolean("leasehold_permission");
                    userInfoBean.setPermissions(strings);
                    as.add(userInfoBean);
                }
                return as;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }finally {
                try {
                    if (rs != null)
                        rs.close();
                    if (stmt != null)
                        stmt.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private ArrayList<AbstractDataBean> queryChartData(Connection connection, String table, String sql) {
            ArrayList<AbstractDataBean> as=new ArrayList<>();   //记录12个月
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.createStatement();
                rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (isCancelled()) break;
                    if (table.equals(TABLE1)) {
                        MarketIndexBean marketIndexBean = new MarketIndexBean();
                        marketIndexBean.setMark_id(rs.getInt("market_id"));
                        marketIndexBean.setTime(rs.getString("time"));
                        marketIndexBean.setMonth_index(rs.getString("month_index"));
                        marketIndexBean.setInnovation_index(rs.getFloat("innovation_index"));
                        marketIndexBean.setEnvironmental_index(rs.getFloat("environmental_index"));
                        marketIndexBean.setFastBatch_index(rs.getFloat("fastBatch_index"));
                        marketIndexBean.setLogistics_index(rs.getFloat("logistics_index"));
                        marketIndexBean.setLeasehold_index(rs.getFloat("leasehold_index"));
                        marketIndexBean.setHuman_index(rs.getFloat("human_index"));
                        as.add(marketIndexBean);
                    } else if(table.equals(TABLE2)){
                        MarketDataBean marketDataBean = new MarketDataBean();
                        marketDataBean.setMark_id(rs.getInt("market_id"));
                        marketDataBean.setTime(rs.getString("time"));
                        marketDataBean.setMonth_index(rs.getString("month"));
                        marketDataBean.setRent(rs.getFloat("rent"));
                        marketDataBean.setAdmission_rate(rs.getFloat("admission_rate"));
                        marketDataBean.setManagement_fee(rs.getFloat("management_fee"));
                        marketDataBean.setTransfer_fee(rs.getFloat("transfer_fee"));
                        marketDataBean.setSecurity_num(rs.getInt("security_num"));
                        marketDataBean.setVisitors_flowrate(rs.getInt("visitors_flowrate"));
                        marketDataBean.setTable_rate(rs.getInt("table_rate"));
                        marketDataBean.setSalary_avg(rs.getFloat("salary_avg"));
                        marketDataBean.setEducation_level(rs.getFloat("education_level"));
                        marketDataBean.setEmployee_average(rs.getFloat("employee_average"));
                        marketDataBean.setRegistered_num(rs.getFloat("registered_num"));
                        marketDataBean.setEcommerce_change(rs.getFloat("ecommerce_change"));
                        marketDataBean.setCustomized_avg(rs.getFloat("customized_avg"));
                        Log.d("TAble",marketDataBean.toString());
                        as.add(marketDataBean);
                    }else if (table.equals(TABLE3)){
                        SyntheticIndexBean syntheticIndexBean=new SyntheticIndexBean();
                        syntheticIndexBean.setMarket_id(rs.getInt("market_id"));
                        syntheticIndexBean.setMonth_index(rs.getString("month_index"));
                        syntheticIndexBean.setAntecedent_index(rs.getFloat("antecedent_index"));
                        syntheticIndexBean.setUnanimous_index(rs.getFloat("unanimous_index"));
                        syntheticIndexBean.setLag_index(rs.getFloat("lag_index"));
                        as.add(syntheticIndexBean);
                    }
                }
                return as;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (rs != null)
                        rs.close();
                    if (stmt != null)
                        stmt.close();
                    if (connection != null)
                        connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 停止网络请求.
     */
    public void removeTask() {
        if (list.size() > 0) {
            if (list.get(0).getStatus()== AsyncTask.Status.RUNNING)
            list.get(0).cancel(true);
            list.remove();
        }
    }
}

