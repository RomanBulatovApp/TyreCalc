package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RateActivity extends AppCompatActivity {

    public final static String DONT_SHOW = "app.bulatov.tyrecalc.DONT_SHOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        TextView text = findViewById(R.id.rate_textView);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        boolean isAppLiked = getIntent().getBooleanExtra("likeApp", true); ///< ответ на вопрос, понравилось ли приложение

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
                answerIntent.putExtra(DONT_SHOW, true);
                setResult(RESULT_OK, answerIntent);
                finish();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isAppLiked){
                    answerIntent.putExtra(DONT_SHOW, true);
                    setResult(RESULT_OK, answerIntent);
                } else {
                    answerIntent.putExtra(DONT_SHOW, false);
                    setResult(RESULT_OK, answerIntent);
                }
                finish();
            }
        });
    }
}