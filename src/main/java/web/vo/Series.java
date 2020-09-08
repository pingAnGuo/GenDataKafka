package web.vo;

public class Series {
    public String x_time;
    public int y_count;

    public Series(String x_time, int y_count) {
        this.x_time = x_time;
        this.y_count = y_count;
    }

    public String getx_time() {
        return x_time;
    }

    public void setx_time(String x_time) {
        this.x_time = x_time;
    }

    public int gety_count() {
        return y_count;
    }

    public void sety_count(int y_count) {
        this.y_count = y_count;
    }
}
