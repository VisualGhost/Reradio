package com.reradio.networking.data;

import com.google.gson.annotations.SerializedName;

public class TuneIn{

    @SerializedName("base-m3u")
    private String mBaseMp3;

    @SerializedName("base")
    private String mBase;

    @SerializedName("base-xspf")
    private String mBaseXspf;

    public String getBaseMp3() {
        return mBaseMp3;
    }

    public void setBaseMp3(String baseMp3) {
        mBaseMp3 = baseMp3;
    }

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        mBase = base;
    }

    public String getBaseXspf() {
        return mBaseXspf;
    }

    public void setBaseXspf(String baseXspf) {
        mBaseXspf = baseXspf;
    }
}
