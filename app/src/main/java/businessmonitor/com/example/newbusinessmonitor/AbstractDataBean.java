package businessmonitor.com.example.newbusinessmonitor;

import java.io.Serializable;

public abstract class AbstractDataBean implements Serializable{
    protected String time,month_index;

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getMonth_index() {
        return month_index;
    }

    public void setMonth_index(String month_index) {
        this.month_index = month_index;
    }

    public abstract int getObjectSize();
}
