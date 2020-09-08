package web.vo;

public class Alarm {
    private String fanNo;
    private String warnTime;
    private int warnCount;

    public Alarm(String fanNo, String warnTime, int warnCount) {
        this.fanNo = fanNo;
        this.warnTime = warnTime;
        this.warnCount = warnCount;
    }

    public String getFanNo() {
        return fanNo;
    }

    public void setFanNo(String fanNo) {
        this.fanNo = fanNo;
    }

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(String warnTime) {
        this.warnTime = warnTime;
    }

    public int getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(int warnCount) {
        this.warnCount = warnCount;
    }
}
