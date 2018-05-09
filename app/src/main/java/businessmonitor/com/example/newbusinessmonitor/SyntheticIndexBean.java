package businessmonitor.com.example.newbusinessmonitor;

public class SyntheticIndexBean extends AbstractDataBean{
    private StringBuilder sb;
    private int market_id;
    private String month_index;
    private float antecedent_index, unanimous_index,lag_index;

    public SyntheticIndexBean(){
        sb=new StringBuilder();
    }

    public int getMarket_id() {
        return market_id;
    }

    public void setMarket_id(int market_id) {
        this.market_id = market_id;
    }

    public String getMonth_index() {
        return month_index;
    }

    public void setMonth_index(String month_index) {
        this.month_index = month_index;
    }

    public float getAntecedent_index() {
        return antecedent_index;
    }

    public void setAntecedent_index(float antecedent_index) {
        this.antecedent_index = antecedent_index;
    }

    public float getUnanimous_index() {
        return unanimous_index;
    }

    public void setUnanimous_index(float unanimous_index) {
        this.unanimous_index = unanimous_index;
    }

    public float getLag_index() {
        return lag_index;
    }

    public void setLag_index(float lag_index) {
        this.lag_index = lag_index;
    }

    @Override
    public String toString() {
        sb.append(getMarket_id()).append(" ")
                .append(getMonth_index()).append(" ")
                .append(getAntecedent_index()).append(" ")
                .append(getUnanimous_index()).append(" ")
                .append(getLag_index());
        return sb.toString();
    }

    @Override
    public int getObjectSize() {
        return toString().getBytes().length;
    }
}
