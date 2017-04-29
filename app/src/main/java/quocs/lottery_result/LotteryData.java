package quocs.lottery_result;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by tmquoc on 26/04/2017.
 */

public class LotteryData implements Serializable {
    public LotteryDataPerRegion[] lstChildren;
    public String[] lstValues;

    static public void buildData (String raw_data, LotteryData parent) throws JSONException {

        JSONObject root = new JSONObject(raw_data);
        LotteryDataPerRegion.buildData(root, parent);
    }

    public void print () {
        for (LotteryDataPerRegion data : lstChildren) {
            data.print();
        }
    }
}

class LotteryDataPerRegion {
    public String regionCode;
    public String[] lstValues;
    public LotteryDataPerDay[] lstChildren;

    private LotteryDataPerRegion (String regionCode, JSONObject input) throws JSONException {
        this.regionCode = regionCode;
        LotteryDataPerDay.buildData(input, this);
    }

    static public void buildData (JSONObject input, LotteryData parent) throws JSONException {

        String[] resultValues = new String[input.length()];
        LotteryDataPerRegion[] resultChildren = new LotteryDataPerRegion[input.length()];

        Iterator<String> keys = input.keys();
        String key;
        int i = 0;

        while  (keys.hasNext()) {
            key = keys.next();

            resultValues[i] = key;

            JSONObject ndata = input.getJSONObject(key);
            resultChildren[i] = new LotteryDataPerRegion(key, ndata);
            i++;
        }

        parent.lstValues = resultValues;
        parent.lstChildren = resultChildren;
    }

    public void print () {
        Log.d("m00n", regionCode);

        for (LotteryDataPerDay data : lstChildren) {
            data.print();
        }
    }

}

class LotteryDataPerDay {
    public String date;
    public String[] lstValues;
    public LotteryDataPerPrize[] lstChildren;

    private LotteryDataPerDay (String date, JSONObject input) throws  JSONException {
        this.date   = date;
        LotteryDataPerPrize.buildData(input, this);
    }

    static public void buildData (JSONObject input, LotteryDataPerRegion parent) throws JSONException{

        String[] resultValues = new String[input.length()];
        LotteryDataPerDay[] resultChildren = new LotteryDataPerDay[input.length()];

        Iterator<String> keys = input.keys();
        String key;
        int i = 0;

        while  (keys.hasNext()) {
            key = keys.next();

            resultValues[i] = key;
            JSONObject ndata = input.getJSONObject(key);
            resultChildren[i] = new LotteryDataPerDay(key, ndata);
            i++;
        }

        parent.lstValues = resultValues;
        parent.lstChildren = resultChildren;
    }

    public void print () {
        Log.d("m00n", date);

        for (LotteryDataPerPrize data : lstChildren) {
            data.print();
        }
    }
}

class LotteryDataPerPrize {
    public String prizeName;
    public String[] lstValues;

    private LotteryDataPerPrize (String prizeName, JSONArray input) throws  JSONException {
        this.prizeName   = prizeName;
        this.lstValues  = new String[input.length()];

        int i = 0;

        while (i < input.length()) {
            this.lstValues[i] = input.getString(i);
            i++;
        }
    }

    static public void buildData (JSONObject input, LotteryDataPerDay parent) throws JSONException{

        String[] resultValues = new String[input.length()];
        LotteryDataPerPrize[] resultChildren = new LotteryDataPerPrize[input.length()];

        Iterator<String> keys = input.keys();
        String key;
        int i = 0;

        while  (keys.hasNext()) {

            key = keys.next();

            resultValues[i] = key;
            JSONArray ndata = input.getJSONArray(key);
            resultChildren[i] = new LotteryDataPerPrize(key, ndata);
            i++;
        }

        parent.lstValues = resultValues;
        parent.lstChildren = resultChildren;
    }

    public void print () {
        Log.d("m00n", prizeName);

        for (String value : lstValues) {
            Log.d("m00n", value);
        }
    }
}