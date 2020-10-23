package android.example.com.earthquack;

public class EarthQuackData {
    private double mMagnitude;
    private String mLocation;
    private long mTime;
    private String mUrlAddress;

    public EarthQuackData(double pMag, String pLoc, long pTime, String pUrl) {
        mMagnitude = pMag;
        mLocation = pLoc;
        mTime = pTime;
        mUrlAddress = pUrl;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTime() {
        return mTime;
    }

    public String getUrlAddress() {
        return mUrlAddress;
    }
}


