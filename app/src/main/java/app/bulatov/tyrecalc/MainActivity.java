package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Wheel oldWheel = new Wheel();
    private ImageView oldWheel_imageView;
    private ImageView oldWheelDiam_imageView;
    private ImageView oldRimDiam_imageView;
    private ImageView oldWheelCircuit_imageView;
    private ImageView oldRimCircuit_imageView;
    private TextView oldDiam_textView;

    private Wheel newWheel = new Wheel();
    private ImageView newWheel_imageView;
    private ImageView newWheelDiam_imageView;
    private ImageView newRimDiam_imageView;
    private ImageView newWheelCircuit_imageView;
    private ImageView newRimCircuit_imageView;
    private TextView newDiam_textView;

    private TextView newSpeed60_textView;
    private TextView newSpeed90_textView;
    private TextView outLine_textView;
    private TextView inLine_textView;
    private TextView upLine_textView;
    private TextView downLine_textView;

    private TextView caution_textView;
    private TextView change1_textView;
    private TextView change2_textView;
    private TextView change3_textView;
    private TextView change4_textView;
    private TextView change5_textView;
    private TextView change6_textView;

    private static boolean isDisclaimerShowed;
    private static final String APP_PREFERENCES = "values";
    static boolean dontShowDisclaimer;
    private static final String APP_PREFERENCES_DONT_SHOW_DISCLAIMER = "dontShowDisclaimer";
    private int oldWidthSpinnerPosition;
    private static final String APP_PREFERENCES_OWSPOSITION = "OWSPosition";
    private int newWidthSpinnerPosition;
    private static final String APP_PREFERENCES_NWSPOSITION = "NWSPosition";
    private int oldHeightSpinnerPosition;
    private static final String APP_PREFERENCES_OHSPOSITION = "OHSPosition";
    private int newHeightSpinnerPosition;
    private static final String APP_PREFERENCES_NHSPOSITION = "NHSPosition";
    private int oldRimDiamSpinnerPosition;
    private static final String APP_PREFERENCES_ORSPOSITION = "ORSPosition";
    private int newRimDiamSpinnerPosition;
    private static final String APP_PREFERENCES_NRSPOSITION = "NRSPosition";
    private int oldJJSpinnerPosition;
    private static final String APP_PREFERENCES_OJSPOSITION = "OJSPosition";
    private int newJJSpinnerPosition;
    private static final String APP_PREFERENCES_NJSPOSITION = "NJSPosition";
    private int oldETSpinnerPosition;
    private static final String APP_PREFERENCES_OESPOSITION = "OESPosition";
    private int newETSpinnerPosition;
    private static final String APP_PREFERENCES_NESPOSITION = "NESPosition";
    private SharedPreferences values;


    final static private String[] WIDTH_VARS = {"135", "145", "155", "165", "175", "185", "195", "205", "215", "225", "235", "245", "255", "265", "275", "285", "295", "305", "315", "325", "335", "345", "355"};
    final static private String[] HEIGHT_VARS = {"25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85"};
    final static private String[] RIM_DIAMETERS_VARS = {"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
    final static private String[] JJ_VARS = {"4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5", "10", "10.5", "11", "11.5", "12", "12.5", "13", "13.5", "14"};
    final static private String[] ET_VARS = {"80", "79", "76", "75", "71", "70", "69", "68", "67", "66", "65", "64", "63", "62", "61", "60", "59", "58", "57", "56", "55", "54", "53", "52", "51", "50", "49", "48", "47", "46", "45", "44", "43", "42", "41", "40", "39", "38", "37", "36", "35", "34", "33", "32", "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21", "20", "19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0", "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10", "-11", "-12", "-13", "-14", "-15", "-16", "-17", "-18", "-19", "-20", "-21", "-22", "-23", "-24", "-25", "-26", "-27", "-28", "-29", "-30", "-32", "-33", "-34", "-35", "-36", "-38", "-40", "-44", "-45", "-46", "-48", "-50", "-55", "-65", "-72", "-76", "-88", "-98"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        dontShowDisclaimer = values.getBoolean(APP_PREFERENCES_DONT_SHOW_DISCLAIMER, false);
        oldWidthSpinnerPosition =   values.getInt(APP_PREFERENCES_OWSPOSITION, 8);
        newWidthSpinnerPosition =   values.getInt(APP_PREFERENCES_NWSPOSITION, 8);
        oldHeightSpinnerPosition =  values.getInt(APP_PREFERENCES_OHSPOSITION, 5);
        newHeightSpinnerPosition =  values.getInt(APP_PREFERENCES_NHSPOSITION, 5);
        oldRimDiamSpinnerPosition = values.getInt(APP_PREFERENCES_ORSPOSITION, 5);
        newRimDiamSpinnerPosition = values.getInt(APP_PREFERENCES_NRSPOSITION, 5);
        oldJJSpinnerPosition =      values.getInt(APP_PREFERENCES_OJSPOSITION, 6);
        newJJSpinnerPosition =      values.getInt(APP_PREFERENCES_NJSPOSITION, 6);
        oldETSpinnerPosition =      values.getInt(APP_PREFERENCES_OESPOSITION, 27);
        newETSpinnerPosition =      values.getInt(APP_PREFERENCES_NESPOSITION, 27);

        if(!isDisclaimerShowed && !dontShowDisclaimer){
            isDisclaimerShowed = true;

            Intent disclaimer = new Intent(MainActivity.this, DisclaimerActivity.class);
            startActivity(disclaimer);
        }

        oldWheel_imageView = findViewById(R.id.oldWheel_imageView);
        oldWheelDiam_imageView = findViewById(R.id.oldWheelDiam_imageView);
        oldRimDiam_imageView = findViewById(R.id.oldRimDiam_imageView);
        oldWheelCircuit_imageView = findViewById(R.id.oldWheelCircuit_imageView);
        oldRimCircuit_imageView = findViewById(R.id.oldRimCircuit_imageView);
        oldDiam_textView = findViewById(R.id.oldDiam_textView);

        newWheel_imageView = findViewById(R.id.newWheel_imageView);
        newWheelDiam_imageView = findViewById(R.id.newWheelDiam_imageView);
        newRimDiam_imageView = findViewById(R.id.newRimDiam_imageView);
        newWheelCircuit_imageView = findViewById(R.id.newWheelCircuit_imageView);
        newRimCircuit_imageView = findViewById(R.id.newRimCircuit_imageView);
        newDiam_textView = findViewById(R.id.newDiam_textView);

        newSpeed60_textView = findViewById(R.id.newSpeed60_textView);
        newSpeed90_textView = findViewById(R.id.newSpeed90_textView);
        outLine_textView = findViewById(R.id.outLine_textView);
        inLine_textView = findViewById(R.id.inLine_textView);
        upLine_textView = findViewById(R.id.upLine_textView);
        downLine_textView = findViewById(R.id.downLine_textView);

        caution_textView = findViewById(R.id.caution);
        change1_textView = findViewById(R.id.specification1);
        change2_textView = findViewById(R.id.specification2);
        change3_textView = findViewById(R.id.specification3);
        change4_textView = findViewById(R.id.specification4);
        change5_textView = findViewById(R.id.specification5);
        change6_textView = findViewById(R.id.specification6);

        // Спинеры ширины
        ArrayAdapter widthAdapter = new ArrayAdapter(this, R.layout.spinner_item, WIDTH_VARS);
        widthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView oldWidth_textView = findViewById(R.id.oldWidth_textView);
        Spinner oldWidthSpinner = findViewById(R.id.tyres_width_spinner1);
        oldWidthSpinner.setAdapter(widthAdapter);
        oldWidthSpinner.setSelection(oldWidthSpinnerPosition);
        oldWidthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                oldWidthSpinnerPosition = position;
                String chosenWidth = (String) parent.getItemAtPosition(position);
                oldWidth_textView.setText(chosenWidth);
                oldWheel.setWidth(chosenWidth);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView newWidth_textView = findViewById(R.id.newWidth_textView);
        Spinner newWidthSpinner = findViewById(R.id.tyres_width_spinner2);
        newWidthSpinner.setAdapter(widthAdapter);
        newWidthSpinner.setSelection(newWidthSpinnerPosition);
        newWidthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newWidthSpinnerPosition = position;
                String chosenWidth = (String) parent.getItemAtPosition(position);
                newWidth_textView.setText(chosenWidth);
                newWheel.setWidth(chosenWidth);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Спинеры профиля
        ArrayAdapter<String> heightAdapter = new ArrayAdapter(this, R.layout.spinner_item, HEIGHT_VARS);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView oldHeight_textView = findViewById(R.id.oldHeight_textView);
        Spinner oldHeightSpinner = findViewById(R.id.tyres_height_spinner1);
        oldHeightSpinner.setAdapter(heightAdapter);
        oldHeightSpinner.setSelection(oldHeightSpinnerPosition);
        oldHeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                oldHeightSpinnerPosition = position;
                String chosenHeight = (String) parent.getItemAtPosition(position);
                oldHeight_textView.setText(chosenHeight);
                oldWheel.setHeight(chosenHeight);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView newHeight_textView = findViewById(R.id.newHeight_textView);
        Spinner newHeightSpinner = findViewById(R.id.tyres_height_spinner2);
        newHeightSpinner.setAdapter(heightAdapter);
        newHeightSpinner.setSelection(newHeightSpinnerPosition);
        newHeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newHeightSpinnerPosition = position;
                String chosenHeight = (String) parent.getItemAtPosition(position);
                newHeight_textView.setText(chosenHeight);
                newWheel.setHeight(chosenHeight);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Спинеры диаметра диска
        ArrayAdapter<String> rimDiamAdapter = new ArrayAdapter(this, R.layout.spinner_item, RIM_DIAMETERS_VARS);
        rimDiamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView oldRimDiam_textView = findViewById(R.id.oldRimDiam_textView);
        Spinner oldRimDiamSpinner = findViewById(R.id.rimDiam_spinner1);
        oldRimDiamSpinner.setAdapter(rimDiamAdapter);
        oldRimDiamSpinner.setSelection(oldRimDiamSpinnerPosition);
        oldRimDiamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                oldRimDiamSpinnerPosition = position;
                String chosenRimDiam = (String) parent.getItemAtPosition(position);
                oldRimDiam_textView.setText(chosenRimDiam);
                oldWheel.setR(chosenRimDiam);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView newRimDiam_textView = findViewById(R.id.newRimDiam_textView);
        Spinner newRimDiamSpinner = findViewById(R.id.rimDiam_spinner2);
        newRimDiamSpinner.setAdapter(rimDiamAdapter);
        newRimDiamSpinner.setSelection(newRimDiamSpinnerPosition);
        newRimDiamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newRimDiamSpinnerPosition = position;
                String chosenRimDiam = (String) parent.getItemAtPosition(position);
                newRimDiam_textView.setText(chosenRimDiam);
                newWheel.setR(chosenRimDiam);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Спинеры ширины диска
        ArrayAdapter<String> jjAdapter = new ArrayAdapter(this, R.layout.spinner_item, JJ_VARS);
        jjAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView oldJJ_textView = findViewById(R.id.oldJJ_textView);
        Spinner oldJJSpinner = findViewById(R.id.JJ_spinner1);
        oldJJSpinner.setAdapter(jjAdapter);
        oldJJSpinner.setSelection(oldJJSpinnerPosition);
        oldJJSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                oldJJSpinnerPosition = position;
                String chosenJJ = (String) parent.getItemAtPosition(position);
                oldJJ_textView.setText(chosenJJ);
                oldWheel.setJJ(chosenJJ);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView newJJ_textView = findViewById(R.id.newJJ_textView);
        Spinner newJJSpinner = findViewById(R.id.JJ_spinner2);
        newJJSpinner.setAdapter(jjAdapter);
        newJJSpinner.setSelection(newJJSpinnerPosition);
        newJJSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newJJSpinnerPosition = position;
                String chosenJJ = (String) parent.getItemAtPosition(position);
                newJJ_textView.setText(chosenJJ);
                newWheel.setJJ(chosenJJ);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Спинеры выноса
        ArrayAdapter<String> etAdapter = new ArrayAdapter(this, R.layout.spinner_item, ET_VARS);
        etAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        TextView oldET_textView = findViewById(R.id.oldET_textView);
        Spinner oldETSpinner = findViewById(R.id.ET_spinner1);
        oldETSpinner.setAdapter(etAdapter);
        oldETSpinner.setSelection(oldETSpinnerPosition);
        oldETSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                oldETSpinnerPosition = position;
                String chosenET = (String) parent.getItemAtPosition(position);
                oldET_textView.setText(chosenET);
                oldWheel.setET(chosenET);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TextView newET_textView = findViewById(R.id.newET_textView);
        Spinner newETSpinner = findViewById(R.id.ET_spinner2);
        newETSpinner.setAdapter(etAdapter);
        newETSpinner.setSelection(newETSpinnerPosition);
        newETSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                newETSpinnerPosition = position;
                String chosenET = (String) parent.getItemAtPosition(position);
                newET_textView.setText(chosenET);
                newWheel.setET(chosenET);
                apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button rareButton = findViewById(R.id.rate_button);
        rareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=app.bulatov.tyrecalc"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        SharedPreferences.Editor editor = values.edit();
        editor.putBoolean(APP_PREFERENCES_DONT_SHOW_DISCLAIMER, dontShowDisclaimer);
        editor.putInt(APP_PREFERENCES_OWSPOSITION, oldWidthSpinnerPosition);
        editor.putInt(APP_PREFERENCES_NWSPOSITION, newWidthSpinnerPosition);
        editor.putInt(APP_PREFERENCES_OHSPOSITION, oldHeightSpinnerPosition);
        editor.putInt(APP_PREFERENCES_NHSPOSITION, newHeightSpinnerPosition);
        editor.putInt(APP_PREFERENCES_ORSPOSITION, oldRimDiamSpinnerPosition);
        editor.putInt(APP_PREFERENCES_NRSPOSITION, newRimDiamSpinnerPosition);
        editor.putInt(APP_PREFERENCES_OJSPOSITION, oldJJSpinnerPosition);
        editor.putInt(APP_PREFERENCES_NJSPOSITION, newJJSpinnerPosition);
        editor.putInt(APP_PREFERENCES_OESPOSITION, oldETSpinnerPosition);
        editor.putInt(APP_PREFERENCES_NESPOSITION, newETSpinnerPosition);
        editor.apply();
    }

    private void apply() {
        setWheelView(oldWheel, oldWheel_imageView);
        setWheelView(newWheel, newWheel_imageView);
        setShapes();
        setCautions();
        setSigns();
        setSpecification();
    }

    private void setWheelView(Wheel wheel, ImageView wheel_imageView) {
        int et = wheel.getET();
        int jj = wheel.getJJ();
        int height = wheel.getHeight();

        if (et > jj * 254 / 300) {
            if      (jj < 60)       wheel_imageView.setImageResource(R.drawable.wheel_image1_1);
            else if (jj > 90)       wheel_imageView.setImageResource(R.drawable.wheel_image1_4);
            else if (height < 50)   wheel_imageView.setImageResource(R.drawable.wheel_image1_3);
            else                    wheel_imageView.setImageResource(R.drawable.wheel_image1_2);
        } else if (et > 10) {
            if (jj < 60)            wheel_imageView.setImageResource(R.drawable.wheel_image2_1);
            else if (jj > 90)       wheel_imageView.setImageResource(R.drawable.wheel_image2_4);
            else if (height < 50)   wheel_imageView.setImageResource(R.drawable.wheel_image2_3);
            else                    wheel_imageView.setImageResource(R.drawable.wheel_image2_2);
        } else if (et > -10) {
            if (jj < 60)            wheel_imageView.setImageResource(R.drawable.wheel_image3_1);
            else if (jj > 90)       wheel_imageView.setImageResource(R.drawable.wheel_image3_4);
            else if (height < 50)   wheel_imageView.setImageResource(R.drawable.wheel_image3_3);
            else                    wheel_imageView.setImageResource(R.drawable.wheel_image3_2);
        } else {
            if (jj < 60)            wheel_imageView.setImageResource(R.drawable.wheel_image4_1);
            else if (jj > 90)       wheel_imageView.setImageResource(R.drawable.wheel_image4_4);
            else if (height < 50)   wheel_imageView.setImageResource(R.drawable.wheel_image4_3);
            else                    wheel_imageView.setImageResource(R.drawable.wheel_image4_2);
        }
    }
    private void setShapes() {

        float oldWheelDiam = oldWheel.getDiameterMM();
        float newWheelDiam = newWheel.getDiameterMM();
        float oldRimDiam = oldWheel.getRimDiameterMM();
        float newRimDiam = newWheel.getRimDiameterMM();
        int oldWidth = oldWheel.getWidth();
        int newWidth = newWheel.getWidth();

        //Получаем параметры изображений схем:
        //параметры кругов старого колеса и диска
        ViewGroup.LayoutParams oldWheelDiam_layoutParams = oldWheelDiam_imageView.getLayoutParams();
        ViewGroup.LayoutParams oldRimDiam_layoutParams = oldRimDiam_imageView.getLayoutParams();
        //параметры схем старого колеса и диска
        ViewGroup.MarginLayoutParams oldWheelCircuitParams = (ViewGroup.MarginLayoutParams) oldWheelCircuit_imageView.getLayoutParams();
        ViewGroup.MarginLayoutParams oldRimCircuitParams = (ViewGroup.MarginLayoutParams) oldRimCircuit_imageView.getLayoutParams();

        //параметры кругов нового колеса и диска
        ViewGroup.LayoutParams newWheelDiam_layoutParams = newWheelDiam_imageView.getLayoutParams();
        ViewGroup.LayoutParams newRimDiam_layoutParams = newRimDiam_imageView.getLayoutParams();
        //параметры схем нового колеса и диска
        ViewGroup.MarginLayoutParams newWheelCircuitParams = (ViewGroup.MarginLayoutParams) newWheelCircuit_imageView.getLayoutParams();
        ViewGroup.MarginLayoutParams newRimCircuitParams = (ViewGroup.MarginLayoutParams) newRimCircuit_imageView.getLayoutParams();

        final float scale = getResources().getDisplayMetrics().density;

        // получаем количество мм в одном dp: максимальный круг 190х190 dp, мы обозначим больший диаметр этим размером на схеме, а остальные рассчитаем от него.
        float mmInDp = Math.max(oldWheelDiam, newWheelDiam) / 180;

        // Изменяем размеры кругов и схем колес и дисков
        // ставим размер и расположение круга и схемы старого колеса
        oldWheelDiam_layoutParams.width = (int) (oldWheelDiam / mmInDp * scale);
        oldWheelDiam_layoutParams.height = (int) (oldWheelDiam / mmInDp * scale);
        oldWheelDiam_imageView.setLayoutParams(oldWheelDiam_layoutParams);
        oldWheelCircuitParams.width = (int) (oldWidth / mmInDp * scale);
        oldWheelCircuitParams.height = (int) (oldWheelDiam / mmInDp * scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            oldWheelCircuitParams.setMarginStart((int) ((oldWheel.getET() / mmInDp * scale)));
        }
        oldWheelCircuitParams.setMargins((int) ((oldWheel.getET() / mmInDp * scale)), 0, 0, 0);
        oldWheelCircuit_imageView.setLayoutParams(oldWheelCircuitParams);
        // ставим размер и расположение круга и схемы диска старого колеса
        oldRimDiam_layoutParams.width = (int) (oldRimDiam / mmInDp * scale);
        oldRimDiam_layoutParams.height = (int) (oldRimDiam / mmInDp * scale);
        oldRimDiam_imageView.setLayoutParams(oldRimDiam_layoutParams);
        oldRimCircuitParams.width = (int) (oldWidth / mmInDp * scale);
        oldRimCircuitParams.height = (int) (oldRimDiam / mmInDp * scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            oldRimCircuitParams.setMarginStart((int) ((oldWheel.getET() / mmInDp * scale)));
        }
        oldRimCircuitParams.setMargins((int) ((oldWheel.getET() / mmInDp * scale)), 0, 0, 0);
        oldRimCircuit_imageView.setLayoutParams(oldRimCircuitParams);
        // ставим размер и расположение круга и схемы нового колеса
        newWheelDiam_layoutParams.width = (int) (newWheelDiam / mmInDp * scale);
        newWheelDiam_layoutParams.height = (int) (newWheelDiam / mmInDp * scale);
        newWheelDiam_imageView.setLayoutParams(newWheelDiam_layoutParams);
        newWheelCircuitParams.width = (int) (newWidth / mmInDp * scale);
        newWheelCircuitParams.height = (int) (newWheelDiam / mmInDp * scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newWheelCircuitParams.setMarginStart((int) ((newWheel.getET() / mmInDp * scale)));
        }
        newWheelCircuitParams.setMargins((int) ((newWheel.getET() / mmInDp * scale)), 0, 0, 0);
        newWheelCircuit_imageView.setLayoutParams(newWheelCircuitParams);
        // ставим размер и расположение круга и схемы диска нового колеса
        newRimDiam_layoutParams.width = (int) (newRimDiam / mmInDp * scale);
        newRimDiam_layoutParams.height = (int) (newRimDiam / mmInDp * scale);
        newRimDiam_imageView.setLayoutParams(newRimDiam_layoutParams);
        newRimCircuitParams.width = (int) (newWidth / mmInDp * scale);
        newRimCircuitParams.height = (int) (newRimDiam / mmInDp * scale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            newRimCircuitParams.setMarginStart((int) ((newWheel.getET() / mmInDp * scale)));
        }
        newRimCircuitParams.setMargins((int) ((newWheel.getET() / mmInDp * scale)), 0, 0, 0);
        newRimCircuit_imageView.setLayoutParams(newRimCircuitParams);
    }
    private void setSigns() {
        oldDiam_textView.setText(String.valueOf(oldWheel.getDiameterMM()));
        newDiam_textView.setText(String.valueOf(newWheel.getDiameterMM()));
        newSpeed60_textView.setText(newWheel.getSpeed60(oldWheel.getDiameterMM()) + " км/ч");
        newSpeed90_textView.setText(newWheel.getSpeed90(oldWheel.getDiameterMM()) + " км/ч");

        float horizonLine = Math.round(newWheel.getDiameterMM()*10 - oldWheel.getDiameterMM()*10) / 20f;
        if ((horizonLine) > 0) {
            upLine_textView.setText("+" + horizonLine);
            downLine_textView.setText("+" + horizonLine);
        } else {
            upLine_textView.setText(String.valueOf(horizonLine));
            downLine_textView.setText(String.valueOf(horizonLine));
        }

        int outLine = (newWheel.getOutLine() - oldWheel.getOutLine());
        if ((outLine) > 0) {
            outLine_textView.setText("+" + outLine);
        } else {
            outLine_textView.setText(String.valueOf(outLine));
        }

        int inLine = (newWheel.getInLine() - oldWheel.getInLine());
        if ((inLine) > 0) {
            inLine_textView.setText("+" + inLine);
        } else {
            inLine_textView.setText(String.valueOf(inLine));
        }
    }
    private void setCautions() {
        String caution1 = "";
        String caution2 = "";
        String caution3 = "";

        switch (oldWheel.getJJ()) {
            case 40:  if (oldWheel.getWidth() > 155) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 4\" рекомендуеамая ширина шины до 155 мм.\n"; break;
            case 45:  if (oldWheel.getWidth() > 165) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 4.5\" рекомендуеамая ширина шины до 165 мм.\n"; break;
            case 50:  if (oldWheel.getWidth() < 145 || oldWheel.getWidth() > 175) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 5\" рекомендуеамая ширина шины от 145 мм до 175 мм.\n"; break;
            case 55:  if (oldWheel.getWidth() < 155 || oldWheel.getWidth() > 195) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 5,5\" рекомендуеамая ширина шины от 155 мм до 195 мм.\n"; break;
            case 60:  if (oldWheel.getWidth() < 165 || oldWheel.getWidth() > 205) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 6\" рекомендуеамая ширина шины от 165 мм до 205 мм.\n"; break;
            case 65:  if (oldWheel.getWidth() < 175 || oldWheel.getWidth() > 215) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 6,5\" рекомендуеамая ширина шины от 175 мм до 215 мм.\n"; break;
            case 70:  if (oldWheel.getWidth() < 185 || oldWheel.getWidth() > 225) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 7\" рекомендуеамая ширина шины от 185 мм до 225 мм.\n"; break;
            case 75:  if (oldWheel.getWidth() < 205 || oldWheel.getWidth() > 245) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 7,5\" рекомендуеамая ширина шины от 205 мм до 245 мм.\n"; break;
            case 80:  if (oldWheel.getWidth() < 215 || oldWheel.getWidth() > 255) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 8\" рекомендуеамая ширина шины от 215 мм до 255 мм.\n"; break;
            case 85:  if (oldWheel.getWidth() < 225 || oldWheel.getWidth() > 265) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 8,5\" рекомендуеамая ширина шины от 225 мм до 265 мм.\n"; break;
            case 90:  if (oldWheel.getWidth() < 235 || oldWheel.getWidth() > 275) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 9\" рекомендуеамая ширина шины от 235 мм до 275 мм.\n"; break;
            case 95:  if (oldWheel.getWidth() < 255 || oldWheel.getWidth() > 295) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 9,5\" рекомендуеамая ширина шины от 255 мм до 295 мм.\n"; break;
            case 100: if (oldWheel.getWidth() < 265 || oldWheel.getWidth() > 305) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 10\" рекомендуеамая ширина шины от 265 мм до 305 мм.\n"; break;
            case 105: if (oldWheel.getWidth() < 275 || oldWheel.getWidth() > 315) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 10,5\" рекомендуеамая ширина шины от 275 мм до 315 мм.\n"; break;
            case 110: if (oldWheel.getWidth() < 285 || oldWheel.getWidth() > 325) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 11\" рекомендуеамая ширина шины от 285 мм до 325 мм.\n"; break;
            case 115: if (oldWheel.getWidth() < 305 || oldWheel.getWidth() > 345) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 11,5\" рекомендуеамая ширина шины от 305 мм до 345 мм.\n"; break;
            case 120: if (oldWheel.getWidth() < 315) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 12\" рекомендуеамая ширина шины от 315 мм.\n"; break;
            case 125: if (oldWheel.getWidth() < 325) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 12,5\" рекомендуеамая ширина шины от 225 мм.\n"; break;
            case 130: if (oldWheel.getWidth() < 335) caution1 = "СТАНДАРТНОЕ КОЛЕСО: При ширине диска 13\" рекомендуеамая ширина шины от 235 мм.\n"; break;
            default: caution1 = "";
        }

        switch (newWheel.getJJ()) {
            case 40:  if (newWheel.getWidth() > 155) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 4\" рекомендуеамая ширина шины до 155 мм.\n"; break;
            case 45:  if (newWheel.getWidth() > 165) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 4.5\" рекомендуеамая ширина шины до 165 мм.\n"; break;
            case 50:  if (newWheel.getWidth() < 145 || newWheel.getWidth() > 175) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 5\" рекомендуеамая ширина шины от 145 мм до 175 мм.\n"; break;
            case 55:  if (newWheel.getWidth() < 155 || newWheel.getWidth() > 195) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 5,5\" рекомендуеамая ширина шины от 155 мм до 195 мм.\n"; break;
            case 60:  if (newWheel.getWidth() < 165 || newWheel.getWidth() > 205) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 6\" рекомендуеамая ширина шины от 165 мм до 205 мм.\n"; break;
            case 65:  if (newWheel.getWidth() < 175 || newWheel.getWidth() > 215) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 6,5\" рекомендуеамая ширина шины от 175 мм до 215 мм.\n"; break;
            case 70:  if (newWheel.getWidth() < 185 || newWheel.getWidth() > 225) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 7\" рекомендуеамая ширина шины от 185 мм до 225 мм.\n"; break;
            case 75:  if (newWheel.getWidth() < 205 || newWheel.getWidth() > 245) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 7,5\" рекомендуеамая ширина шины от 205 мм до 245 мм.\n"; break;
            case 80:  if (newWheel.getWidth() < 215 || newWheel.getWidth() > 255) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 8\" рекомендуеамая ширина шины от 215 мм до 255 мм.\n"; break;
            case 85:  if (newWheel.getWidth() < 225 || newWheel.getWidth() > 265) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 8,5\" рекомендуеамая ширина шины от 225 мм до 265 мм.\n"; break;
            case 90:  if (newWheel.getWidth() < 235 || newWheel.getWidth() > 275) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 9\" рекомендуеамая ширина шины от 235 мм до 275 мм.\n"; break;
            case 95:  if (newWheel.getWidth() < 255 || newWheel.getWidth() > 295) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 9,5\" рекомендуеамая ширина шины от 255 мм до 295 мм.\n"; break;
            case 100: if (newWheel.getWidth() < 265 || newWheel.getWidth() > 305) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 10\" рекомендуеамая ширина шины от 265 мм до 305 мм.\n"; break;
            case 105: if (newWheel.getWidth() < 275 || newWheel.getWidth() > 315) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 10,5\" рекомендуеамая ширина шины от 275 мм до 315 мм.\n"; break;
            case 110: if (newWheel.getWidth() < 285 || newWheel.getWidth() > 325) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 11\" рекомендуеамая ширина шины от 285 мм до 325 мм.\n"; break;
            case 115: if (newWheel.getWidth() < 305 || newWheel.getWidth() > 345) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 11,5\" рекомендуеамая ширина шины от 305 мм до 345 мм.\n"; break;
            case 120: if (newWheel.getWidth() < 315) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 12\" рекомендуеамая ширина шины от 315 мм до 345 мм.\n"; break;
            case 125: if (newWheel.getWidth() < 325) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 12,5\" рекомендуеамая ширина шины от 225 мм.\n"; break;
            case 130: if (newWheel.getWidth() < 335) caution2 = "НОВОЕ КОЛЕСО: При ширине диска 13\" рекомендуеамая ширина шины от 235 мм.\n"; break;
            default: caution2 = "";
        }

        float changePercent = Math.round((newWheel.getDiameterMM()-oldWheel.getDiameterMM()) / ((oldWheel.getDiameterMM())/100f) * 10) / 10f;
        if (changePercent > 3) caution3 = "Внешний диаметр колеса увеличится на " + changePercent + "%. Рекомендуемая разница не более 3%.";
        else if (changePercent < -3) caution3 = "Внешний диаметр колеса уменьшится на " + Math.abs(changePercent) + "%. Рекомендуемая разница не более 3%.";
        else caution3 = "";

        caution_textView.setText(caution1 + caution2 + caution3);
    }
    private void setSpecification() {

        float changeDiameter = Math.round(newWheel.getDiameterMM()*10 - oldWheel.getDiameterMM()*10) / 10f;
        if (changeDiameter > 0) change1_textView.setText("- Диаметр колеса увеличится на " + changeDiameter + " мм.");
        else if (changeDiameter < 0) change1_textView.setText("- Диаметр колеса уменьшится на " + Math.abs(changeDiameter) + " мм.");
        else change1_textView.setText("- Диаметр колеса не изменится.");

        float clearanceChange = Math.round(newWheel.getDiameterMM()*10 - oldWheel.getDiameterMM()*10) / 20f;
        if (clearanceChange > 0) change2_textView.setText("- Клиренс автомобиля увеличится на " + clearanceChange + " мм.");
        else if (clearanceChange < 0) change2_textView.setText("- Клиренс автомобиля уменьшится на " + Math.abs(clearanceChange) + " мм.");
        else change2_textView.setText("- Клиренс автомобиля не изменится.");

        int outLineChange = newWheel.getOutLine() - oldWheel.getOutLine();
        if (outLineChange > 0) change3_textView.setText("- Внешняя плоскость колеса сместится наружу на " + outLineChange + " мм.");
        else if (outLineChange < 0) change3_textView.setText("- Внешняя плоскость колеса сместится внутрь на " + Math.abs(outLineChange) + " мм.");
        else change3_textView.setText("- Внешняя плоскость колеса не сместится.");

        int inLineChange = newWheel.getInLine() - oldWheel.getInLine();
        if (inLineChange > 0) change4_textView.setText("- Внутренняя плоскость колеса сместится ближе к стойке на " + inLineChange + " мм.");
        else if (inLineChange < 0) change4_textView.setText("- Внутренняя плоскость колеса сместится дальшее от стойки на " + Math.abs(inLineChange) + " мм.");
        else change4_textView.setText("- Внутренняя плоскость колеса не сместится.");

        change5_textView.setText("- При показаниях спидометра 60 км/ч реальная скорость составит " + newWheel.getSpeed60(oldWheel.getDiameterMM()) + " км/ч.");
        change6_textView.setText("- При показаниях спидометра 90 км/ч реальная скорость составит " + newWheel.getSpeed90(oldWheel.getDiameterMM()) + " км/ч.");
    }
    static void saveValues(){

    }
}
