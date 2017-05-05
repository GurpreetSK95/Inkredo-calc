package me.gurpreetsk.emicalulator.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gurpreet on 05/05/17.
 */

public class Emi implements Parcelable {

    private String principal;
    private String duration;
    private String emi;
    private String amount;

    public Emi(String principal, String duration, String emi, String amount) {
        this.principal = principal;
        this.duration = duration;
        this.emi = emi;
        this.amount = amount;
    }

    public Emi(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.principal = data[0];
        this.duration = data[1];
        this.emi = data[2];
        this.amount = data[3];
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                this.principal,
                this.duration,
                this.emi,
                this.amount
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Emi createFromParcel(Parcel in) {
            return new Emi(in);
        }

        public Emi[] newArray(int size) {
            return new Emi[size];
        }
    };

}
