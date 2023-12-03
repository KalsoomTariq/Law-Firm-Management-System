package Lawyer;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String caseType;

    public CalendarActivity(ZonedDateTime date, String caseType) {
        this.date = date;
        this.caseType = caseType;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }


    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", caseType='" + caseType + '\'' +
                '}';
    }
}