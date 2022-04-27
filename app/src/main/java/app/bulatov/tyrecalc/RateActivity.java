package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RateActivity extends AppCompatActivity {

    public final static String NEED_RATE_SHOW_KEY = "app.bulatov.tyrecalc.NEED_RATE_SHOW_KEY";
    public final static String IS_RATE_SHOWED_KEY = "app.bulatov.tyrecalc.IS_RATE_SHOWED_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        TextView text = findViewById(R.id.rate_textView);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        boolean isAppLiked = getIntent().getBooleanExtra("likeApp", true);

        if (isAppLiked) {
            text.setText(R.string.wont_rate);
            yesButton.setText(R.string.rate);
            noButton.setText(R.string.late);
        } else {
            text.setText(R.string.do_app_better);
            yesButton.setText(R.string.advice);
            noButton.setText(R.string.dont_ask);
        }

        Intent answerIntent = new Intent();

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAppLiked){
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW);
                    rateIntent.setData(Uri.parse("market://details?id=app.bulatov.tyrecalc"));
                    startActivity(rateIntent);
                } else {
                    Intent adviceIntent = new Intent(Intent.ACTION_VIEW);
                    adviceIntent.setData(Uri.parse("mailto:info@bulatov.app"));
                    startActivity(adviceIntent);
                }
                answerIntent.putExtra(NEED_RATE_SHOW_KEY, false);
                finishActivity(answerIntent);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAppLiked){
                    answerIntent.putExtra(NEED_RATE_SHOW_KEY, true);
                    finishActivity(answerIntent);
                } else {
                    answerIntent.putExtra(NEED_RATE_SHOW_KEY, false);
                    finishActivity(answerIntent);
                }
            }
        });
    }

    private void finishActivity(Intent answerIntent) {
        answerIntent.putExtra(IS_RATE_SHOWED_KEY, true);
        setResult(RESULT_OK, answerIntent);
        finish();
    }
}