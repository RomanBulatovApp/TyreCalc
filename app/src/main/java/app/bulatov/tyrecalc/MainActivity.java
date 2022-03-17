package app.bulatov.tyrecalc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final Wheel oldWheel = new Wheel();
    private final Wheel newWheel = new Wheel();
    static final private int DISCLAIMER_REQUEST_CODE = 1;
    static final private int RATE_REQUEST_CODE = 2;
    static Date today = new Date();

    private final String[] WIDTH_VARS = {"135", "145", "155", "165", "175", "185", "195", "205", "215", "225", "235", "245",
            "255", "265", "275", "285", "295", "305", "315", "325", "335", "345", "355"};
    private final String[] HEIGHT_VARS = {"25", "30", "35", "40", "45", "50", "55", "60", "65", "70", "75", "80", "85"};
    private final String[] RIM_DIAMETERS_VARS = {"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
    private final String[] JJ_VARS = {"4", "4.5", "5", "5.5", "6", "6.5", "7", "7.5", "8", "8.5", "9", "9.5",
            "10", "10.5", "11", "11.5", "12", "12.5", "13", "13.5", "14"};
    private final String[] ET_VARS = {"80", "79", "76", "75", "71", "70", "69", "68", "67", "66", "65", "64", "63", "62", "61",
            "60", "59", "58", "57", "56", "55", "54", "53", "52", "51", "50", "49", "48", "47", "46", "45", "44", "43", "42", "41",
            "40", "39", "38", "37", "36", "35", "34", "33", "32", "31", "30", "29", "28", "27", "26", "25", "24", "23", "22", "21",
            "20", "19", "18", "17", "16", "15", "14", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0",
            "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10", "-11", "-12", "-13", "-14", "-15", "-16", "-17", "-18", "-19",
            "-20", "-21", "-22", "-23", "-24", "-25", "-26", "-27", "-28", "-29", "-30", "-32", "-33", "-34", "-35", "-36", "-38",
            "-40", "-44", "-45", "-46", "-48", "-50", "-55", "-65", "-72", "-76", "-88", "-98"};

    private int showDate;
    private final String APP_PREFERENCES_DATE = "date";
    private boolean dontShowDisclaimer;
    private final String APP_PREFERENCES_DONT_SHOW_DISCLAIMER = "dontShowDisclaimer";
    private boolean dontShowRate;
    private final String APP_PREFERENCES_DONT_SHOW_RATE = "dontShowRate";
    private int oldWidthSpinnerPosition;
    private final String APP_PREFERENCES_OWSPOSITION = "OWSPosition";
    private int newWidthSpinnerPosition;
    private final String APP_PREFERENCES_NWSPOSITION = "NWSPosition";
    private int oldHeightSpinnerPosition;
    private final String APP_PREFERENCES_OHSPOSITION = "OHSPosition";
    private int newHeightSpinnerPosition;
    private final String APP_PREFERENCES_NHSPOSITION = "NHSPosition";
    private int oldRimDiamSpinnerPosition;
    private final String APP_PREFERENCES_ORSPOSITION = "ORSPosition";
    private int newRimDiamSpinnerPosition;
    private final String APP_PREFERENCES_NRSPOSITION = "NRSPosition";
    private int oldJJSpinnerPosition;
    private final String APP_PREFERENCES_OJSPOSITION = "OJSPosition";
    private int newJJSpinnerPosition;
    private final String APP_PREFERENCES_NJSPOSITION = "NJSPosition";
    private int oldETSpinnerPosition;
    private final String APP_PREFERENCES_OESPOSITION = "OESPosition";
    private int newETSpinnerPosition;
    private final String APP_PREFERENCES_NESPOSITION = "NESPosition";
    private SharedPreferences values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSavedParam();

        // Дисклеймер
        if (!dontShowDisclaimer && showDate != today.getMonth()*100 + today.getDate()) {
            showDate = today.getMonth()*100 + today.getDate();
            Intent disclaimer = new Intent(this, DisclaimerActivity.class);
            startActivityForResult(disclaimer, DISCLAIMER_REQUEST_CODE);
        }

        // Оценить приложение
        if (!dontShowRate) {
            Intent rateIntent = new Intent(this, RateActivity.class);

            Button rateYesButton = findViewById(R.id.rateYes_button);
            rateYesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateIntent.putExtra("likeApp", true);
                    startActivityForResult(rateIntent, RATE_REQUEST_CODE);
                }
            });

            Button rateNoButton = findViewById(R.id.rateNo_button);
            rateNoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateIntent.putExtra("likeApp", false);
                    startActivityForResult(rateIntent, RATE_REQUEST_CODE);
                }
            });
        } else {
            LinearLayout rateLayout = findViewById(R.id.rate_layout);
            ViewGroup.LayoutParams rate_layoutParams = rateLayout.getLayoutParams();
            rate_layoutParams.height = (int) (0);
            rateLayout.setLayoutParams(rate_layoutParams);
        }

        // Спинеры ширины
        ArrayAdapter<String> widthAdapter = new ArrayAdapter(this, R.layout.spinner_item, WIDTH_VARS);
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
    }

    /**
     *Принимает и обрабатывает данные от активностей дисклеймера и оценки приложения
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // устанавливет отемтку о непоказывании дисклеймера
        if (requestCode == DISCLAIMER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                dontShowDisclaimer = data.getBooleanExtra(DisclaimerActivity.CHECKBOX, false);
            }
        }

        // устанавливает отметку о непоказывании предложения оценить приложение, сворачивает предложение в текущей активности
        if (requestCode == RATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                dontShowRate = data.getBooleanExtra(RateActivity.DONT_SHOW, false);
                LinearLayout rateLayout = findViewById(R.id.rate_layout);
                ViewGroup.LayoutParams rate_layoutParams = rateLayout.getLayoutParams();
                rate_layoutParams.height = (int) (0);
                rateLayout.setLayoutParams(rate_layoutParams);
            }
        }
    }

    /**
     * сохраняет данные в память
     */
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = values.edit();
        editor.putInt(APP_PREFERENCES_DATE, showDate);
        editor.putBoolean(APP_PREFERENCES_DONT_SHOW_DISCLAIMER, dontShowDisclaimer);
        editor.putBoolean(APP_PREFERENCES_DONT_SHOW_RATE, dontShowRate);
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

    /**
     * применяет все введенные параметры
     */
    private void apply() {
        ImageView oldWheel_imageView = findViewById(R.id.oldWheel_imageView);
        setWheelView(oldWheel, oldWheel_imageView);
        ImageView newWheel_imageView = findViewById(R.id.newWheel_imageView);
        setWheelView(newWheel, newWheel_imageView);
        setShapes();
        setCautions();
        setSigns();
        setSpecification();
    }

    /**
     * ставит изображение колеса из ресурсов в зависимости от его размеров
     */
    private void setWheelView(Wheel wheel, ImageView wheel_imageView) {
        int et = wheel.getET();
        int jj = wheel.getJJ();
        int height = wheel.getHeight();

        if (et > jj * 254 / 300) {
            if (jj < 60) wheel_imageView.setImageResource(R.drawable.wheel_image1_1);
            else if (jj > 90) wheel_imageView.setImageResource(R.drawable.wheel_image1_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image1_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image1_2);
        } else if (et > 10) {
            if (jj < 60) wheel_imageView.setImageResource(R.drawable.wheel_image2_1);
            else if (jj > 90) wheel_imageView.setImageResource(R.drawable.wheel_image2_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image2_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image2_2);
        } else if (et > -10) {
            if (jj < 60) wheel_imageView.setImageResource(R.drawable.wheel_image3_1);
            else if (jj > 90) wheel_imageView.setImageResource(R.drawable.wheel_image3_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image3_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image3_2);
        } else {
            if (jj < 60) wheel_imageView.setImageResource(R.drawable.wheel_image4_1);
            else if (jj > 90) wheel_imageView.setImageResource(R.drawable.wheel_image4_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image4_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image4_2);
        }
    }

    /**
     * устанавливает размеры схематичных изображений колес
     */
    private void setShapes() {

        float oldWheelDiam = oldWheel.getWheelDiameterMM();
        float newWheelDiam = newWheel.getWheelDiameterMM();
        float oldRimDiam = oldWheel.getRimDiameterMM();
        float newRimDiam = newWheel.getRimDiameterMM();
        int oldWidth = oldWheel.getWheelWidth();
        int newWidth = newWheel.getWheelWidth();

        //Получаем параметры изображений схем:

        //параметры кругов старого колеса и диска
        ImageView oldWheelDiam_imageView = findViewById(R.id.oldWheelDiam_imageView);
        ViewGroup.LayoutParams oldWheelDiam_layoutParams = oldWheelDiam_imageView.getLayoutParams();
        ImageView oldRimDiam_imageView = findViewById(R.id.oldRimDiam_imageView);
        ViewGroup.LayoutParams oldRimDiam_layoutParams = oldRimDiam_imageView.getLayoutParams();

        //параметры прямоугольников старого колеса и диска
        ImageView oldWheelCircuit_imageView = findViewById(R.id.oldWheelCircuit_imageView);
        ViewGroup.MarginLayoutParams oldWheelCircuitParams = (ViewGroup.MarginLayoutParams) oldWheelCircuit_imageView.getLayoutParams();
        ImageView oldRimCircuit_imageView = findViewById(R.id.oldRimCircuit_imageView);
        ViewGroup.MarginLayoutParams oldRimCircuitParams = (ViewGroup.MarginLayoutParams) oldRimCircuit_imageView.getLayoutParams();

        //параметры кругов нового колеса и диска
        ImageView newWheelDiam_imageView = findViewById(R.id.newWheelDiam_imageView);
        ViewGroup.LayoutParams newWheelDiam_layoutParams = newWheelDiam_imageView.getLayoutParams();
        ImageView newRimDiam_imageView = findViewById(R.id.newRimDiam_imageView);
        ViewGroup.LayoutParams newRimDiam_layoutParams = newRimDiam_imageView.getLayoutParams();

        //параметры прямоугольников нового колеса и диска
        ImageView newWheelCircuit_imageView = findViewById(R.id.newWheelCircuit_imageView);
        ViewGroup.MarginLayoutParams newWheelCircuitParams = (ViewGroup.MarginLayoutParams) newWheelCircuit_imageView.getLayoutParams();
        ImageView newRimCircuit_imageView = findViewById(R.id.newRimCircuit_imageView);
        ViewGroup.MarginLayoutParams newRimCircuitParams = (ViewGroup.MarginLayoutParams) newRimCircuit_imageView.getLayoutParams();

        final float scale = getResources().getDisplayMetrics().density;

        // получаем количество мм в одном dp: максимальный круг 190х190 dp, мы обозначим больший из диаметров этим размером на схеме, а остальные рассчитаем от него.
        float mmInDp = Math.max(oldWheelDiam, newWheelDiam) / 180;

        // Изменяем размеры кругов и схем колес и дисков

        // ставим размер и расположение круга и схемы шины старого колеса
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

        // ставим размер и расположение круга и схемы шины нового колеса
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

    /**
     * выводит числовые показатели изменений на схемах колес
     */
    private void setSigns() {

        TextView oldDiam_textView = findViewById(R.id.oldDiam_textView);
        oldDiam_textView.setText(String.valueOf(oldWheel.getWheelDiameterMM()));

        TextView newDiam_textView = findViewById(R.id.newDiam_textView);
        newDiam_textView.setText(String.valueOf(newWheel.getWheelDiameterMM()));

        TextView newSpeed60_textView = findViewById(R.id.newSpeed60_textView);
        newSpeed60_textView.setText(newWheel.getSpeed60(oldWheel.getWheelDiameterMM()) + getResources().getString(R.string.kmH));

        TextView newSpeed90_textView = findViewById(R.id.newSpeed90_textView);
        newSpeed90_textView.setText(newWheel.getSpeed90(oldWheel.getWheelDiameterMM()) + getResources().getString(R.string.kmH));

        TextView upLine_textView = findViewById(R.id.upLine_textView);
        TextView downLine_textView = findViewById(R.id.downLine_textView);
        float horizonLine = Math.round(newWheel.getWheelDiameterMM() * 10 - oldWheel.getWheelDiameterMM() * 10) / 20f;
        if ((horizonLine) > 0) {
            upLine_textView.setText("+" + horizonLine);
            downLine_textView.setText("+" + horizonLine);
        } else {
            upLine_textView.setText(String.valueOf(horizonLine));
            downLine_textView.setText(String.valueOf(horizonLine));
        }

        TextView outLine_textView = findViewById(R.id.outLine_textView);
        int outLine = (newWheel.getOutLine() - oldWheel.getOutLine());
        if ((outLine) > 0) {
            outLine_textView.setText("+" + outLine);
        } else {
            outLine_textView.setText(String.valueOf(outLine));
        }

        TextView inLine_textView = findViewById(R.id.inLine_textView);
        int inLine = (newWheel.getInLine() - oldWheel.getInLine());
        if ((inLine) > 0) {
            inLine_textView.setText("+" + inLine);
        } else {
            inLine_textView.setText(String.valueOf(inLine));
        }
    }

    /**
     * выводит предупреждения
     * если внешний диаметр колеса изменится более, чем на 3%, укажет на сколько % изменился диаметр
     * для кождого колеса (стандартного и нового) выводит свое уведомление, если
     *    - ширина шины меньше минимальной при данной ширине диска
     *    - ширина шины большще максимально допустимой при данной ширине диска
     */
    private void setCautions() {
        String caution = "";

        float changePercent = Math.round((newWheel.getWheelDiameterMM() - oldWheel.getWheelDiameterMM()) / ((oldWheel.getWheelDiameterMM()) / 100f) * 10) / 10f;
        if (changePercent > 3)
            caution = caution + getResources().getString(R.string.diam_increase) + numToString(changePercent) + getResources().getString(R.string.recommended_change_percent) + "\n";
        else if (changePercent < -3)
            caution = caution + getResources().getString(R.string.diam_decrease) + numToString(Math.abs(changePercent)) + getResources().getString(R.string.recommended_change_percent) + "\n";

        if (oldWheel.getWidth() > oldWheel.getMaxWidth()) {
            caution = caution + getResources().getString(R.string.standard_wheel) + getResources().getString(R.string.with_rim_width_of) + numToString(oldWheel.getJJ() / 10f) +
                    getResources().getString(R.string.recommended_tire_width_upto) + oldWheel.getMaxWidth() + getResources().getString(R.string.mm) + "\n";
        }
        if (oldWheel.getWidth() < oldWheel.getMinWidth()) {
            caution = caution + getResources().getString(R.string.standard_wheel) + getResources().getString(R.string.with_rim_width_of) + numToString(oldWheel.getJJ() / 10f) +
                    getResources().getString(R.string.recommended_tire_width_from) + oldWheel.getMinWidth() + getResources().getString(R.string.mm) + "\n";
        }

        if (newWheel.getWidth() > newWheel.getMaxWidth()) {
            caution = caution + getResources().getString(R.string.new_wheel) + getResources().getString(R.string.with_rim_width_of) + numToString(newWheel.getJJ() / 10f) +
                    getResources().getString(R.string.recommended_tire_width_upto) + newWheel.getMaxWidth() + getResources().getString(R.string.mm) + "\n";
        }
        if (newWheel.getWidth() < newWheel.getMinWidth()) {
            caution = caution + getResources().getString(R.string.new_wheel) + getResources().getString(R.string.with_rim_width_of) + numToString(newWheel.getJJ() / 10f) +
                    getResources().getString(R.string.recommended_tire_width_from) + newWheel.getMinWidth() + getResources().getString(R.string.mm) + "\n";
        }

        TextView caution_textView = findViewById(R.id.caution);
        caution_textView.setText(caution);
    }

    /**
     * выводит описания изменений в текстовом формате
     * - на сколько измениятся внешний диаметр колеса
     * - на сколько изменится клиренс
     * - на сколько и куда сместится внешняя граница колеса
     * - на сколько и куда сместится внутренняя граница колеса
     * - какая будет реальная скорость при показаниях спидометра 60 км/ч
     * - какая будет реальная скорость при показаниях спидометра 90 км/ч
     */
    private void setSpecification() {

        TextView change1_textView = findViewById(R.id.specification1);
        String change1 = "- ";
        float changeDiameter = Math.round(newWheel.getWheelDiameterMM() * 10 - oldWheel.getWheelDiameterMM() * 10) / 10f;
        if (changeDiameter > 0)
            change1 = change1 + getResources().getString(R.string.diam_increase) + numToString(changeDiameter) + getResources().getString(R.string.mm);
        else if (changeDiameter < 0)
            change1 = change1 + getResources().getString(R.string.diam_decrease) + numToString(Math.abs(changeDiameter)) + getResources().getString(R.string.mm);
        else change1 = change1 + getResources().getString(R.string.diameter_wont_change);
        change1_textView.setText(change1);

        TextView change2_textView = findViewById(R.id.specification2);
        String change2 = "- ";
        float clearanceChange = Math.round(newWheel.getWheelDiameterMM() * 10 - oldWheel.getWheelDiameterMM() * 10) / 20f;
        if (clearanceChange > 0)
            change2 = change2 + getResources().getString(R.string.clearance_increase) + numToString(clearanceChange) + getResources().getString(R.string.mm);
        else if (clearanceChange < 0)
            change2 = change2 + getResources().getString(R.string.clearance_decrease) + numToString(Math.abs(clearanceChange)) + getResources().getString(R.string.mm);
        else change2 = change2 + getResources().getString(R.string.clearance_wont_change);
        change2_textView.setText(change2);

        TextView change3_textView = findViewById(R.id.specification3);
        String change3 = "- ";
        int outLineChange = newWheel.getOutLine() - oldWheel.getOutLine();
        if (outLineChange > 0)
            change3 = change3 + getResources().getString(R.string.outline_increase) + numToString(outLineChange) + getResources().getString(R.string.mm);
        else if (outLineChange < 0)
            change3 = change3 + getResources().getString(R.string.outline_decrease) + numToString(Math.abs(outLineChange)) + getResources().getString(R.string.mm);
        else change3 = change3 + getResources().getString(R.string.outline_wont_change);
        change3_textView.setText(change3);

        TextView change4_textView = findViewById(R.id.specification4);
        String change4 = "- ";
        int inLineChange = newWheel.getInLine() - oldWheel.getInLine();
        if (inLineChange > 0)
            change4 = change4 + getResources().getString(R.string.inline_increase) + numToString(inLineChange) + getResources().getString(R.string.mm);
        else if (inLineChange < 0)
            change4 = change4 + getResources().getString(R.string.inline_decrease) + numToString(Math.abs(inLineChange)) + getResources().getString(R.string.mm);
        else change4 = change4 + getResources().getString(R.string.inline_wont_change);
        change4_textView.setText(change4);


        TextView change5_textView = findViewById(R.id.specification5);
        int speed1 = 60;
        String speedometerChange1 = getResources().getString(R.string.with_speedometer) + speed1 + getResources().getString(R.string.speed_is) +
                newWheel.getSpeed60(oldWheel.getWheelDiameterMM()) + getResources().getString(R.string.kmH);
        change5_textView.setText(speedometerChange1);

        TextView change6_textView = findViewById(R.id.specification6);
        int speed2 = 90;
        String speedometerChange2 = getResources().getString(R.string.with_speedometer) + speed2 + getResources().getString(R.string.speed_is) +
                newWheel.getSpeed90(oldWheel.getWheelDiameterMM()) + getResources().getString(R.string.kmH);
        change6_textView.setText(speedometerChange2);
    }

    /**
     * Возвращает текстовое представление числа
     * если число целое, возвращает число без точки и нулей за ней
     */
    private String numToString(float num) {
        if (num % 1 == 0.0) return String.valueOf((int) num);
        else return String.valueOf(num);
    }

    /**
     * Инициализирует переменные сохраненными значениями или значениями по умолчанию
     */
    private void getSavedParam() {
        values = getSharedPreferences("values", Context.MODE_PRIVATE);
        showDate = values.getInt(APP_PREFERENCES_DATE, 0);
        dontShowDisclaimer = values.getBoolean(APP_PREFERENCES_DONT_SHOW_DISCLAIMER, false);
        dontShowRate = values.getBoolean(APP_PREFERENCES_DONT_SHOW_RATE, false);
        oldWidthSpinnerPosition = values.getInt(APP_PREFERENCES_OWSPOSITION, 8);
        newWidthSpinnerPosition = values.getInt(APP_PREFERENCES_NWSPOSITION, 8);
        oldHeightSpinnerPosition = values.getInt(APP_PREFERENCES_OHSPOSITION, 5);
        newHeightSpinnerPosition = values.getInt(APP_PREFERENCES_NHSPOSITION, 5);
        oldRimDiamSpinnerPosition = values.getInt(APP_PREFERENCES_ORSPOSITION, 5);
        newRimDiamSpinnerPosition = values.getInt(APP_PREFERENCES_NRSPOSITION, 5);
        oldJJSpinnerPosition = values.getInt(APP_PREFERENCES_OJSPOSITION, 6);
        newJJSpinnerPosition = values.getInt(APP_PREFERENCES_NJSPOSITION, 6);
        oldETSpinnerPosition = values.getInt(APP_PREFERENCES_OESPOSITION, 27);
        newETSpinnerPosition = values.getInt(APP_PREFERENCES_NESPOSITION, 27);
    }
}
