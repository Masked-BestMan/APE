package businessmonitor.com.example.newbusinessmonitor;

/**
 * Created by Administrator on 2018/5/8.
 */

public class MarketBean  {
    private int market_id;
    private String market_name;
    private String city;
    private int ranking;

    public MarketBean(int market_id,String market_name){
        this.market_id = market_id;
        this.market_name = market_name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
}
