package quocs.lottery_result;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Act_Content extends AppCompatActivity
        implements Frg_RegionList.OnFragmentInteractionListener,
        Frg_ResultTable.OnFragmentInteractionListener {

    public LotteryData lotteryData;
    private boolean fragmentState;
    Frg_RegionList frgRegionList;
    Frg_ResultTable frgResultTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_content);

        try {
            Intent intent = getIntent();
            String raw_input = intent.getStringExtra("data");

            lotteryData = new LotteryData();
            LotteryData.buildData(raw_input, lotteryData);

//            String raw_input = readTextFromFile("test.json");
//            LotteryData lotteryData = new LotteryData();
//            LotteryData.buildData(raw_input, lotteryData);

            frgRegionList = Frg_RegionList.newInstance(lotteryData);
            getSupportFragmentManager().beginTransaction().add(R.id.layoutContent, frgRegionList, "TEST").commit();
            fragmentState = true;

        }
        catch  (Exception e) {
            Log.e("m00n", e.getMessage(), e);
        }
    }

    public String readTextFromFile (String fileNameInAssets) throws IOException {

        InputStream inputStream = getAssets().open(fileNameInAssets);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
//                builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setRegionSelected (LotteryDataPerRegion lotteryDataPerRegion) {

        frgResultTable = Frg_ResultTable.newInstance();
        frgResultTable.updateView(lotteryDataPerRegion);

        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContent, frgResultTable).commit();
        fragmentState = false;
    }

    @Override
    public void onBackPressed() {
        if (!fragmentState) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutContent, frgRegionList).commit();
        }

    }

}

