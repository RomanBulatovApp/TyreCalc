package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class DisclaimerActivity extends AppCompatActivity {

    public final static String DISCLAIMER_KEY = "app.bulatov.tyrecalc.DISCLAIMER_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        CheckBox dontShow = findViewById(R.id.dontShow_checkBox);
        Button ok_button = findViewById(R.id.ok_button);

        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent answerIntent = new Intent();
                answerIntent.putExtra(DISCLAIMER_KEY, dontShow.isChecked());
                setResult(RESULT_OK, answerIntent);
                finish();
            }
        });
    }
}