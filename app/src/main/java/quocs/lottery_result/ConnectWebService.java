package quocs.lottery_result;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by tmquoc on 27/04/2017.
 */

public class ConnectWebService extends AsyncTask<Void, String, Boolean> {

    private Act_Loading actLoadingGUI;
    private final int MINIMUM_STAGE_TIME = 1500;
    private String raw_input;

    public ConnectWebService(Act_Loading actLoadingGUI) {
        this.actLoadingGUI = actLoadingGUI;
    }

    @Override
    protected Boolean doInBackground(Void ... Params) {

        long start_time = 0;
        try {
            start_time = System.currentTimeMillis();
            publishProgress("Connecting to server...");

            HttpURLConnection connection = (HttpURLConnection) (new URL("http://thanhhungqb.tk:8080/kqxsmn")).openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();

            if (System.currentTimeMillis() - start_time < MINIMUM_STAGE_TIME) {
                SystemClock.sleep(start_time + MINIMUM_STAGE_TIME - System.currentTimeMillis());
            }

            start_time = System.currentTimeMillis();
            publishProgress("Fetching data from server...");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
//                builder.append(System.lineSeparator());
            }

            raw_input =  builder.toString();

            if (System.currentTimeMillis() - start_time < MINIMUM_STAGE_TIME) {
                SystemClock.sleep(start_time + MINIMUM_STAGE_TIME - System.currentTimeMillis());
            }

            return Boolean.TRUE;

        }
        catch (Exception e) {

            if (System.currentTimeMillis() - start_time < MINIMUM_STAGE_TIME) {
                SystemClock.sleep(start_time + MINIMUM_STAGE_TIME - System.currentTimeMillis());
            }

            return Boolean.FALSE;
//            Log.e("m00n", e.getMessage(), e);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        TextView textView = (TextView) actLoadingGUI.findViewById(R.id.tvStatus);
        textView.setText(values[0]);
//        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
        if (aBoolean == Boolean.FALSE) {
            TextView textView = (TextView) actLoadingGUI.findViewById(R.id.tvStatus);
            textView.setText("Can't connect to server\nPlease check your Internet connection");

            Button button = (Button) actLoadingGUI.findViewById(R.id.buttonRetry);
            button.setVisibility(View.VISIBLE);
        }
     else {
            Intent intent = new Intent(actLoadingGUI, Act_Content.class);
            intent.putExtra("data", raw_input);
            actLoadingGUI.startActivity(intent);
        }

    }
}
