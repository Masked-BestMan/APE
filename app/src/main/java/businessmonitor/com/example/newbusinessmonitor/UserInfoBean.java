package businessmonitor.com.example.newbusinessmonitor;

import java.util.Arrays;

public class UserInfoBean extends AbstractDataBean {
    private int user_id;
    private String user_name;
    private int market_id;
    private String market_name;    //用户所在商会
    private boolean vip;
    private boolean[] permissions;  //权限组
    private StringBuilder sb;

    public UserInfoBean() {
        sb = new StringBuilder();
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public int getMarket_id() {
        return market_id;
    }

    public void setMarket_id(int market_id) {
        this.market_id = market_id;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean[] getPermissions() {
        return permissions;
    }

    public void setPermissions(boolean[] permissions) {
        this.permissions = permissions;
    }


    @Override
    public String toString() {
        sb.append(getUser_id()).append(" ")
                .append(getUser_name()).append(" ")
                .append(getMarket_id()).append(" ")
                .append(getMarket_name()).append(" ")
                .append(isVip()).append(" ")
                .append(Arrays.toString(getPermissions()));
        return sb.toString();
    }

    @Override
    public int getObjectSize() {
        return toString().getBytes().length;
    }
}
