package businessmonitor.com.example.newbusinessmonitor;

public class MarketIndexBean extends AbstractDataBean {
    private StringBuilder sb;

    private int mark_id;
    private float Innovation_index,Environmental_index,FastBatch_index,
            Logistics_index,Leasehold_index,Human_index;
    public MarketIndexBean(){
        sb=new StringBuilder();
    }

    public int getMark_id() {
        return mark_id;
    }

    public void setMark_id(int mark_id) {
        this.mark_id = mark_id;
    }

    public float getInnovation_index() {
        return Innovation_index;
    }

    public void setInnovation_index(float innovation_index) {
        Innovation_index = innovation_index;
    }

    public float getEnvironmental_index() {
        return Environmental_index;
    }

    public void setEnvironmental_index(float environmental_index) {
        Environmental_index = environmental_index;
    }

    public float getFastBatch_index() {
        return FastBatch_index;
    }

    public void setFastBatch_index(float fastBatch_index) {
        FastBatch_index = fastBatch_index;
    }

    public float getLogistics_index() {
        return Logistics_index;
    }

    public void setLogistics_index(float logistics_index) {
        Logistics_index = logistics_index;
    }

    public float getLeasehold_index() {
        return Leasehold_index;
    }

    public void setLeasehold_index(float leasehold_index) {
        Leasehold_index = leasehold_index;
    }

    public float getHuman_index() {
        return Human_index;
    }

    public void setHuman_index(float human_index) {
        Human_index = human_index;
    }


    @Override
    public String toString() {
        sb.append(getMark_id()).append(" ")
                .append(getTime()).append(" ")
                .append(getMonth_index()).append(" ")
                .append(getInnovation_index()).append(" ")
                .append(getEnvironmental_index()).append(" ")
                .append(getFastBatch_index()).append(" ")
                .append(getLogistics_index()).append(" ")
                .append(getLeasehold_index()).append(" ")
                .append(getHuman_index()).append(" ");
        return sb.toString();
    }

    @Override
    public int getObjectSize() {
        return toString().getBytes().length;
    }
}
