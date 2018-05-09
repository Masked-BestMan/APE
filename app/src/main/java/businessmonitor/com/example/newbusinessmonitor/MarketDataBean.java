package businessmonitor.com.example.newbusinessmonitor;

public class MarketDataBean extends AbstractDataBean {
    private int mark_id,security_num,visitors_flowrate;
    private float rent,admission_rate,management_fee,transfer_fee,table_rate,
            salary_avg,education_level,employee_average,registered_num,ecommerce_change,customized_avg;
    private StringBuilder sb;
    public MarketDataBean(){
        sb=new StringBuilder();
    }

    public int getMark_id() {
        return mark_id;
    }

    public void setMark_id(int mark_id) {
        this.mark_id = mark_id;
    }

    public int getSecurity_num() {
        return security_num;
    }

    public void setSecurity_num(int security_num) {
        this.security_num = security_num;
    }

    public int getVisitors_flowrate() {
        return visitors_flowrate;
    }

    public void setVisitors_flowrate(int visitors_flowrate) {
        this.visitors_flowrate = visitors_flowrate;
    }

    public float getRent() {
        return rent;
    }

    public void setRent(float rent) {
        this.rent = rent;
    }

    public float getAdmission_rate() {
        return admission_rate;
    }

    public void setAdmission_rate(float admission_rate) {
        this.admission_rate = admission_rate;
    }

    public float getManagement_fee() {
        return management_fee;
    }

    public void setManagement_fee(float management_fee) {
        this.management_fee = management_fee;
    }

    public float getTransfer_fee() {
        return transfer_fee;
    }

    public void setTransfer_fee(float transfer_fee) {
        this.transfer_fee = transfer_fee;
    }

    public float getTable_rate() {
        return table_rate;
    }

    public void setTable_rate(float table_rate) {
        this.table_rate = table_rate;
    }

    public float getSalary_avg() {
        return salary_avg;
    }

    public void setSalary_avg(float salary_avg) {
        this.salary_avg = salary_avg;
    }

    public float getEducation_level() {
        return education_level;
    }

    public void setEducation_level(float education_level) {
        this.education_level = education_level;
    }

    public float getEmployee_average() {
        return employee_average;
    }

    public void setEmployee_average(float employee_average) {
        this.employee_average = employee_average;
    }

    public float getRegistered_num() {
        return registered_num;
    }

    public void setRegistered_num(float registered_num) {
        this.registered_num = registered_num;
    }

    public float getEcommerce_change() {
        return ecommerce_change;
    }

    public void setEcommerce_change(float ecommerce_change) {
        this.ecommerce_change = ecommerce_change;
    }

    public float getCustomized_avg() {
        return customized_avg;
    }

    public void setCustomized_avg(float customized_avg) {
        this.customized_avg = customized_avg;
    }


    @Override
    public String toString() {
        sb.append(getMark_id()).append(" ")
                .append(getTime()).append(" ")
                .append(getMonth_index()).append(" ")
                .append(getRent()).append(" ")
                .append(getAdmission_rate()).append(" ")
                .append(getManagement_fee()).append(" ")
                .append(getTransfer_fee()).append(" ")
                .append(getSecurity_num()).append(" ")
                .append(getVisitors_flowrate()).append(" ")
                .append(getTable_rate()).append(" ")
                .append(getSalary_avg()).append(" ")
                .append(getEducation_level()).append(" ")
                .append(getEmployee_average()).append(" ")
                .append(getRegistered_num()).append(" ")
                .append(getEcommerce_change()).append(" ")
                .append(getCustomized_avg());
        return sb.toString();
    }

    @Override
    public int getObjectSize() {
        return toString().getBytes().length;
    }
}
