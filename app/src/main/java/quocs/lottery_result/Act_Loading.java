package quocs.lottery_result;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Act_Loading extends AppCompatActivity {

    Act_Loading self_refence = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Button button = (Button) findViewById(R.id.buttonRetry);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.INVISIBLE);
                new ConnectWebService(self_refence).execute();
            }
        });

        new ConnectWebService(self_refence).execute();
    }
}
