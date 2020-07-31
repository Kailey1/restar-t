package cooldudes.restart.model;

import java.util.Date;

public class AppUser {

    private String uId, contactEmail, contactSms;
    private int startAmt, dailyLimit;
    private long startTime, streakStart;

    public AppUser(){}

    public AppUser(String id){
        this.uId = id;
        this.startTime = System.currentTimeMillis();
        this.streakStart = System.currentTimeMillis();
    }

    // finds how long ago the user joined
    public int secAgo(){
        Long diff = new Date().getTime() - startTime;
        int secDiff = (int) (diff / 1000);
        return secDiff;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactSms() {
        return contactSms;
    }

    public void setContactSms(String contactSms) {
        this.contactSms = contactSms;
    }

    public int getStartAmt() {
        return startAmt;
    }

    public void setStartAmt(int startAmt) {
        this.startAmt = startAmt;
    }

    public int getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(int dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public long getStreakStart() {
        return streakStart;
    }

    public void setStreakStart(long streakStart) {
        this.streakStart = streakStart;
    }
}
