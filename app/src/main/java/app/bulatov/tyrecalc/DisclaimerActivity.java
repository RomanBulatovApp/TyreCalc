package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class DisclaimerActivity extends AppCompatActivity {

    public final static String CHECKBOX = "app.bulatov.tyrecalc.CHECKBOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        System.out.println("DISCLAIMER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        CheckBox dontShow = findViewById(R.id.show_checkBox);
        Button ok = findViewById(R.id.ok_button);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent answerIntent = new Intent();
                answerIntent.putExtra(CHECKBOX, dontShow.isChecked());
                setResult(RESULT_OK, answerIntent);
                finish();
            }
        });
    }
}