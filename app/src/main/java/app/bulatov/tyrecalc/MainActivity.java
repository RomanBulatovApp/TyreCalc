package app.bulatov.tyrecalc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Wheel oldWheel = new Wheel();
    private Wheel newWheel = new Wheel();
    private final Calendar today = Calendar.getInstance();
    private final Gson gson = new Gson();
    private int showDisclaimerDay;
    private boolean needShowDisclaimer;
    private boolean isRateShowed;
    private boolean needShowRate;

    private final Integer[] TIRE_WIDTH_SPINNER_VARS = {135,145,155,165,175,185,195,205,215,225,235,245,255,265,275,285,295,305,315,325,335,345,355};
    private final Integer[] TIRE_HEIGHT_SPINNER_VARS = {25,30,35,40,45,50,55,60,65,70,75,80,85};
    private final Integer[] RIM_DIAMETERS_SPINNER_VARS = {12,13,14,15,16,17,18,19,20,21,22,23,24};
    private final Double[] JJ_SPINNER_VARS = {4.0,4.5,5.0,5.5,6.0,6.5,7.0,7.5,8.0,8.5,9.0,9.5,10.0,10.5,11.0,11.5,12.0,12.5,13.0,13.5,14.0};
    private final Integer[] ET_SPINNER_VARS = {-98,-88,-76,-72,-65,-55,-50,-48,-46,-45,-44,-40,-38,-36,-35,-34,-33,-32,-30,
            -29,-28,-27,-26,-25,-24,-23,-22,-21,-20,-19,-18,-17,-16,-15,-14,-13,-12,-11,-10,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,
            1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,
            41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,75,76,79,80};

    private SharedPreferences appPreferences;
    private final String APP_PREFERENCES_SHOW_DISCLAIMER_DATE = "date";
    private final String APP_PREFERENCES_NEED_SHOW_DISCLAIMER = "notShowDisclaimer";
    private final String APP_PREFERENCES_NEED_SHOW_RATE = "notShowRate";
    private final String APP_PREFERENCES_OLD_WHEEL = "oldWheel";
    private final String APP_PREFERENCES_NEW_WHEEL = "newWheel";

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        if(intent != null) {
                            needShowDisclaimer = intent.getBooleanExtra(DisclaimerActivity.DISCLAIMER_KEY, true);
                            needShowRate = intent.getBooleanExtra(RateActivity.NEED_RATE_SHOW_KEY, true);
                            isRateShowed = intent.getBooleanExtra(RateActivity.IS_RATE_SHOWED_KEY, false);
                            showRate();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSavedParams();
        showDisclaimer();
        showRate();

        Spinner oldTireWidthSpinner = findViewById(R.id.oldTireWidth_spinner);
        oldTireWidthSpinner.setAdapter(getAdapter(TIRE_WIDTH_SPINNER_VARS));
        oldTireWidthSpinner.setSelection(Arrays.binarySearch(TIRE_WIDTH_SPINNER_VARS, oldWheel.getTireWidth()));
        oldTireWidthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oldWheel.setTireWidth((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner newTireWidthSpinner = findViewById(R.id.newTireWidth_spinner);
        newTireWidthSpinner.setAdapter(getAdapter(TIRE_WIDTH_SPINNER_VARS));
        newTireWidthSpinner.setSelection(Arrays.binarySearch(TIRE_WIDTH_SPINNER_VARS, newWheel.getTireWidth()));
        newTireWidthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newWheel.setTireWidth((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner oldTireHeightSpinner = findViewById(R.id.oldTireHeight_spinner);
        oldTireHeightSpinner.setAdapter(getAdapter(TIRE_HEIGHT_SPINNER_VARS));
        oldTireHeightSpinner.setSelection(Arrays.binarySearch(TIRE_HEIGHT_SPINNER_VARS, oldWheel.getTireHeight()));
        oldTireHeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oldWheel.setTireHeight((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner newTireHeightSpinner = findViewById(R.id.newTireHeight_spinner);
        newTireHeightSpinner.setAdapter(getAdapter(TIRE_HEIGHT_SPINNER_VARS));
        newTireHeightSpinner.setSelection(Arrays.binarySearch(TIRE_HEIGHT_SPINNER_VARS, newWheel.getTireHeight()));
        newTireHeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newWheel.setTireHeight((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner oldRimDiamSpinner = findViewById(R.id.oldRimDiam_spinner);
        oldRimDiamSpinner.setAdapter(getAdapter(RIM_DIAMETERS_SPINNER_VARS));
        oldRimDiamSpinner.setSelection(Arrays.binarySearch(RIM_DIAMETERS_SPINNER_VARS, oldWheel.getRimDiam()));
        oldRimDiamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oldWheel.setRimDiam((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner newRimDiamSpinner = findViewById(R.id.newRimDiam_spinner);
        newRimDiamSpinner.setAdapter(getAdapter(RIM_DIAMETERS_SPINNER_VARS));
        newRimDiamSpinner.setSelection(Arrays.binarySearch(RIM_DIAMETERS_SPINNER_VARS, newWheel.getRimDiam()));
        newRimDiamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newWheel.setRimDiam((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner oldJJSpinner = findViewById(R.id.oldJJ_spinner);
        oldJJSpinner.setAdapter(getAdapter(JJ_SPINNER_VARS));
        oldJJSpinner.setSelection(Arrays.binarySearch(JJ_SPINNER_VARS, oldWheel.getJJ()));
        oldJJSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oldWheel.setJJ((double) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner newJJSpinner = findViewById(R.id.newJJ_spinner);
        newJJSpinner.setAdapter(getAdapter(JJ_SPINNER_VARS));
        newJJSpinner.setSelection(Arrays.binarySearch(JJ_SPINNER_VARS, newWheel.getJJ()));
        newJJSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newWheel.setJJ((double) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner oldETSpinner = findViewById(R.id.oldET_spinner);
        oldETSpinner.setAdapter(getAdapter(ET_SPINNER_VARS));
        oldETSpinner.setSelection(Arrays.binarySearch(ET_SPINNER_VARS, oldWheel.getET()));
        oldETSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oldWheel.setET((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner newETSpinner = findViewById(R.id.newET_spinner);
        newETSpinner.setAdapter(getAdapter(ET_SPINNER_VARS));
        newETSpinner.setSelection(Arrays.binarySearch(ET_SPINNER_VARS, newWheel.getET()));
        newETSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newWheel.setET((int) parent.getItemAtPosition(position));
                commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = appPreferences.edit();

        editor.putInt(APP_PREFERENCES_SHOW_DISCLAIMER_DATE, showDisclaimerDay);
        editor.putBoolean(APP_PREFERENCES_NEED_SHOW_DISCLAIMER, needShowDisclaimer);
        editor.putBoolean(APP_PREFERENCES_NEED_SHOW_RATE, needShowRate);

        String jsonOldWheel = gson.toJson(oldWheel);
        editor.putString(APP_PREFERENCES_OLD_WHEEL,jsonOldWheel);
        String jsonNewWheel = gson.toJson(newWheel);
        editor.putString(APP_PREFERENCES_NEW_WHEEL,jsonNewWheel);

        editor.apply();
    }

    private void getSavedParams() {
        appPreferences = getSharedPreferences("values", Context.MODE_PRIVATE);

        showDisclaimerDay = appPreferences.getInt(APP_PREFERENCES_SHOW_DISCLAIMER_DATE, 0);
        needShowDisclaimer = appPreferences.getBoolean(APP_PREFERENCES_NEED_SHOW_DISCLAIMER, true);
        needShowRate = appPreferences.getBoolean(APP_PREFERENCES_NEED_SHOW_RATE, true);

        String jsonOldWheel = appPreferences.getString(APP_PREFERENCES_OLD_WHEEL, "");
        if (jsonOldWheel.length() != 0) oldWheel = gson.fromJson(jsonOldWheel, Wheel.class);
        String jsonNewWheel = appPreferences.getString(APP_PREFERENCES_NEW_WHEEL, "");
        if (jsonNewWheel.length() != 0) newWheel = gson.fromJson(jsonNewWheel, Wheel.class);
    }
    private void showDisclaimer() {
        if (needShowDisclaimer && showDisclaimerDay != today.get(Calendar.DAY_OF_YEAR)) {
            showDisclaimerDay = today.get(Calendar.DAY_OF_YEAR);
            Intent disclaimer = new Intent(this, DisclaimerActivity.class);
            startForResult.launch(disclaimer);
        }
    }
    private void showRate() {
        if (needShowRate && !isRateShowed) {
            Intent rateIntent = new Intent(this, RateActivity.class);

            Button rateYes_button = findViewById(R.id.rateYes_button);
            rateYes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateIntent.putExtra("likeApp", true);
                    startForResult.launch(rateIntent);
                }
            });

            Button rateNo_button = findViewById(R.id.rateNo_button);
            rateNo_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateIntent.putExtra("likeApp", false);
                    startForResult.launch(rateIntent);
                }
            });
        } else {
            LinearLayout rateLayout = findViewById(R.id.rate_layout);
            ViewGroup.LayoutParams rate_layoutParams = rateLayout.getLayoutParams();
            rate_layoutParams.height = (int) (0);
            rateLayout.setLayoutParams(rate_layoutParams);
        }
    }

    private void commit() {
        changeWheelImage(oldWheel, findViewById(R.id.oldWheel_imageView));
        changeWheelImage(newWheel, findViewById(R.id.newWheel_imageView));
        setShapeSize();
        setImageSigns();
        describeChanges();
        showCautions();
    }
    private void changeWheelImage(Wheel wheel, ImageView wheel_imageView) {
        int et = wheel.getET();
        double jj = wheel.getJJ();
        int height = wheel.getTireHeight();

        if (et > jj * 254 / 30) {
            if (jj < 6.0) wheel_imageView.setImageResource(R.drawable.wheel_image1_1);
            else if (jj > 9.0) wheel_imageView.setImageResource(R.drawable.wheel_image1_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image1_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image1_2);
        } else if (et > 10) {
            if (jj < 6.0) wheel_imageView.setImageResource(R.drawable.wheel_image2_1);
            else if (jj > 9.0) wheel_imageView.setImageResource(R.drawable.wheel_image2_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image2_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image2_2);
        } else if (et > -10) {
            if (jj < 6.0) wheel_imageView.setImageResource(R.drawable.wheel_image3_1);
            else if (jj > 9.0) wheel_imageView.setImageResource(R.drawable.wheel_image3_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image3_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image3_2);
        } else {
            if (jj < 6.0) wheel_imageView.setImageResource(R.drawable.wheel_image4_1);
            else if (jj > 9.0) wheel_imageView.setImageResource(R.drawable.wheel_image4_4);
            else if (height < 50) wheel_imageView.setImageResource(R.drawable.wheel_image4_3);
            else wheel_imageView.setImageResource(R.drawable.wheel_image4_2);
        }
    }
    private void setShapeSize() {
        double scale = Math.max(oldWheel.getWheelDiamMM(), newWheel.getWheelDiamMM()) / 180; //the max size of the wheel circuit in the layout is 180dp; take the largest wheel diameter mm equal 180dp & set the scale depending on it
        double oldWheelDiamInDP = oldWheel.getWheelDiamMM() / scale;
        double newWheelDiamInDP = newWheel.getWheelDiamMM() / scale;
        double oldRimDiamInDP = oldWheel.getRimDiamMM() / scale;
        double newRimDiamInDP = newWheel.getRimDiamMM() / scale;
        double oldWheelWidthInDP = oldWheel.getWheelWidth() / scale;
        double newWheelWidthInDP = newWheel.getWheelWidth() / scale;
        double oldEtInDP = oldWheel.getET() / scale;
        double newEtInDP = newWheel.getET() / scale;

        ImageView oldWheelCircle_imageView = findViewById(R.id.oldWheelCircle_imageView);
        setCircleSize(oldWheelCircle_imageView, oldWheelDiamInDP);

        ImageView oldRimCircle_imageView = findViewById(R.id.oldRimCircle_imageView);
        setCircleSize(oldRimCircle_imageView, oldRimDiamInDP);

        ImageView newWheelCircle_imageView = findViewById(R.id.newWheelCircle_imageView);
        setCircleSize(newWheelCircle_imageView, newWheelDiamInDP);

        ImageView newRimCircle_imageView = findViewById(R.id.newRimCircle_imageView);
        setCircleSize(newRimCircle_imageView, newRimDiamInDP);

        ImageView oldWheelRectangle_imageView = findViewById(R.id.oldWheelRectangle_imageView);
        setRectangleSize(oldWheelRectangle_imageView, oldWheelWidthInDP, oldWheelDiamInDP, oldEtInDP);

        ImageView oldRimRectangle_imageView = findViewById(R.id.oldRimRectangle_imageView);
        setRectangleSize(oldRimRectangle_imageView, oldWheelWidthInDP, oldRimDiamInDP, oldEtInDP);

        ImageView newWheelRectangle_imageView = findViewById(R.id.newWheelRectangle_imageView);
        setRectangleSize(newWheelRectangle_imageView, newWheelWidthInDP, newWheelDiamInDP, newEtInDP);

        ImageView newRimRectangle_imageView = findViewById(R.id.newRimRectangle_imageView);
        setRectangleSize(newRimRectangle_imageView, newWheelWidthInDP, newRimDiamInDP, newEtInDP);
    }
    private void setCircleSize(ImageView circle, double diamInDp) {
        ViewGroup.LayoutParams params = circle.getLayoutParams();
        float screenDensity = getResources().getDisplayMetrics().density;

        params.width = (int) (diamInDp * screenDensity);
        params.height = (int) (diamInDp * screenDensity);
        circle.setLayoutParams(params);
    }
    private void setRectangleSize(ImageView rectangle, double widthInDP, double diamInDP, double etInDP) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rectangle.getLayoutParams();
        double screenDensity = getResources().getDisplayMetrics().density;

        params.width = (int) (widthInDP * screenDensity);
        params.height = (int) (diamInDP * screenDensity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            params.setMarginStart((int)((etInDP-10) * screenDensity));
            params.setMarginEnd((int)((-etInDP+10) * screenDensity));
        } else {
            params.setMargins((int)((etInDP-10) * screenDensity), 0, (int)((-etInDP+10) * screenDensity), 0);
        }

        rectangle.setLayoutParams(params);
    }
    private void setImageSigns() {
        TextView oldTyreWidth_textView = findViewById(R.id.oldTireWidth_textView);
        oldTyreWidth_textView.setText(String.valueOf(oldWheel.getTireWidth()));

        TextView oldTireHeight_textView = findViewById(R.id.oldTireHeight_textView);
        oldTireHeight_textView.setText(String.valueOf(oldWheel.getTireHeight()));

        TextView oldRimDiam_textView = findViewById(R.id.oldRimDiam_textView);
        oldRimDiam_textView.setText(String.valueOf(oldWheel.getRimDiam()));

        TextView oldJJ_textView = findViewById(R.id.oldJJ_textView);
        oldJJ_textView.setText(String.valueOf(oldWheel.getJJ()));

        TextView oldET_textView = findViewById(R.id.oldET_textView);
        oldET_textView.setText(String.valueOf(oldWheel.getET()));

        TextView oldWheelDiam_textView = findViewById(R.id.oldWheelDiam_textView);
        oldWheelDiam_textView.setText(String.valueOf(oldWheel.getWheelDiamMM()));

        TextView newTireWidth_textView = findViewById(R.id.newTireWidth_textView);
        newTireWidth_textView.setText(String.valueOf(newWheel.getTireWidth()));

        TextView newTireHeight_textView = findViewById(R.id.newTireHeight_textView);
        newTireHeight_textView.setText(String.valueOf(newWheel.getTireHeight()));

        TextView newRimDiam_textView = findViewById(R.id.newRimDiam_textView);
        newRimDiam_textView.setText(String.valueOf(newWheel.getRimDiam()));

        TextView newJJ_textView = findViewById(R.id.newJJ_textView);
        newJJ_textView.setText(String.valueOf(newWheel.getJJ()));

        TextView newET_textView = findViewById(R.id.newET_textView);
        newET_textView.setText(String.valueOf(newWheel.getET()));

        TextView newWheelDiam_textView = findViewById(R.id.newWheelDiam_textView);
        newWheelDiam_textView.setText(String.valueOf(newWheel.getWheelDiamMM()));

        TextView realSpeed60_textView = findViewById(R.id.realSpeed60_textView);
        String realSpeed60 = getDoubleCutZeroToString(newWheel.getRealSpeed(oldWheel.getWheelDiamMM(), 60))
                + getResources().getString(R.string.kmH);
        realSpeed60_textView.setText(realSpeed60);

        TextView realSpeed90_textView = findViewById(R.id.realSpeed90_textView);
        String realSpeed90 = getDoubleCutZeroToString(newWheel.getRealSpeed(oldWheel.getWheelDiamMM(), 90))
                + getResources().getString(R.string.kmH);
        realSpeed90_textView.setText(realSpeed90);

        TextView upLineChange_textView = findViewById(R.id.upLineChange_textView);
        TextView downLineChange_textView = findViewById(R.id.downLineChange_textView);
        double wheelRadiusChange = Math.round((newWheel.getWheelDiamMM() - oldWheel.getWheelDiamMM()) / 2 * 10) / 10d;
        upLineChange_textView.setText(getNumToStringWithPlusOrMinus(wheelRadiusChange));
        downLineChange_textView.setText(getNumToStringWithPlusOrMinus(wheelRadiusChange));

        TextView outLineChange_textView = findViewById(R.id.outLineChange_textView);
        int outLineChange = newWheel.getOutLineFromHub() - oldWheel.getOutLineFromHub();
        outLineChange_textView.setText(getNumToStringWithPlusOrMinus(outLineChange));

        TextView inLine_textView = findViewById(R.id.inLine_textView);
        int inLineChange = newWheel.getInLineFromHub() - oldWheel.getInLineFromHub();
        inLine_textView.setText(getNumToStringWithPlusOrMinus(inLineChange));
    }
    private void describeChanges() {
        TextView changeDiam_textView = findViewById(R.id.wheelDiamChangeDescription_textView);
        double changeDiameter = Math.round(newWheel.getWheelDiamMM() * 10 - oldWheel.getWheelDiamMM() * 10) / 10d;
        String describeDiam = getSizeDescribe(changeDiameter, R.string.diam_increase, R.string.diam_decrease, R.string.diameter_wont_change);
        changeDiam_textView.setText(describeDiam);

        TextView changeClearance_textView = findViewById(R.id.clearanceChangeDescription_textView);
        double clearanceChange = Math.round(newWheel.getWheelDiamMM() * 10 - oldWheel.getWheelDiamMM() * 10) / 20d;
        String describeClearance = getSizeDescribe(clearanceChange, R.string.clearance_increase, R.string.clearance_decrease, R.string.clearance_wont_change);
        changeClearance_textView.setText(describeClearance);

        TextView changeOutLine_textView = findViewById(R.id.outLineChangeDescription_textView);
        double outLineChange = newWheel.getOutLineFromHub() - oldWheel.getOutLineFromHub();
        String describeOutLine = getSizeDescribe(outLineChange, R.string.outline_increase, R.string.outline_decrease, R.string.outline_wont_change);
        changeOutLine_textView.setText(describeOutLine);

        TextView changeInLine_textView = findViewById(R.id.inLineChangeDescription_textView);
        double inLineChange = newWheel.getInLineFromHub() - oldWheel.getInLineFromHub();
        String describeInLine = getSizeDescribe(inLineChange, R.string.inline_increase, R.string.inline_decrease, R.string.inline_wont_change);
        changeInLine_textView.setText(describeInLine);

        TextView changeSpeed60_textView = findViewById(R.id.speed60ChangeDescription_textView);
        changeSpeed60_textView.setText(getSpeedDescribe(60));

        TextView changeSpeed90_textView = findViewById(R.id.speed90ChangeDescription_textView);
        changeSpeed90_textView.setText(getSpeedDescribe(90));
    }
    private String getSizeDescribe(double change, int increaseStrId, int decreaseStrId, int notChangeStrId) {
        String describe = "- ";
        if (change > 0)
            describe = describe + getResources().getString(increaseStrId) + getDoubleCutZeroToString(change) + getResources().getString(R.string.mm);
        else if (change < 0)
            describe = describe + getResources().getString(decreaseStrId) + getDoubleCutZeroToString(Math.abs(change)) + getResources().getString(R.string.mm);
        else {
            describe = describe + getResources().getString(notChangeStrId);
        }
        return describe;
    }
    private String getSpeedDescribe(int speed) {
        String withSpeedometerReeds = getResources().getString(R.string.with_speedometer_reeds);
        String realSpeedIs = getResources().getString(R.string.real_speed_is);
        String realSpeed = getDoubleCutZeroToString(newWheel.getRealSpeed(oldWheel.getWheelDiamMM(), speed));
        String kmH = getResources().getString(R.string.kmH);

        return withSpeedometerReeds + speed + realSpeedIs + realSpeed + kmH;
    }
    private void showCautions() {
        String caution = "";
        double changePercent = Math.round((newWheel.getWheelDiamMM() - oldWheel.getWheelDiamMM()) / (oldWheel.getWheelDiamMM() / 100d) * 10) / 10d;

        if (changePercent > 3)
            caution = caution + getResources().getString(R.string.diam_increase) + getDoubleCutZeroToString(changePercent)
                    + getResources().getString(R.string.recommended_change_percent) + "\n";
        else if (changePercent < -3)
            caution = caution + getResources().getString(R.string.diam_decrease) + getDoubleCutZeroToString(Math.abs(changePercent))
                    + getResources().getString(R.string.recommended_change_percent) + "\n";

        if (oldWheel.getTireWidth() > oldWheel.getMaxTireWidthForJJ()) {
            caution = caution + getResources().getString(R.string.standard_wheel) + getResources().getString(R.string.with_rim_width_of)
                    + getDoubleCutZeroToString(oldWheel.getJJ()) + getResources().getString(R.string.recommended_tire_width_upto)
                    + oldWheel.getMaxTireWidthForJJ() + getResources().getString(R.string.mm) + "\n";
        }
        if (oldWheel.getTireWidth() < oldWheel.getMinTireWidthForJJ()) {
            caution = caution + getResources().getString(R.string.standard_wheel) + getResources().getString(R.string.with_rim_width_of)
                    + getDoubleCutZeroToString(oldWheel.getJJ()) + getResources().getString(R.string.recommended_tire_width_from)
                    + oldWheel.getMinTireWidthForJJ() + getResources().getString(R.string.mm) + "\n";
        }

        if (newWheel.getTireWidth() > newWheel.getMaxTireWidthForJJ()) {
            caution = caution + getResources().getString(R.string.new_wheel) + getResources().getString(R.string.with_rim_width_of)
                    + getDoubleCutZeroToString(newWheel.getJJ()) + getResources().getString(R.string.recommended_tire_width_upto)
                    + newWheel.getMaxTireWidthForJJ() + getResources().getString(R.string.mm) + "\n";
        }
        if (newWheel.getTireWidth() < newWheel.getMinTireWidthForJJ()) {
            caution = caution + getResources().getString(R.string.new_wheel) + getResources().getString(R.string.with_rim_width_of)
                    + getDoubleCutZeroToString(newWheel.getJJ()) + getResources().getString(R.string.recommended_tire_width_from)
                    + newWheel.getMinTireWidthForJJ() + getResources().getString(R.string.mm) + "\n";
        }

        TextView caution_textView = findViewById(R.id.caution_textWiew);
        caution_textView.setText(caution);
    }

    private String getDoubleCutZeroToString(double num) {
        if (num % 1 == 0.0) return String.valueOf((int) num);
        else return String.valueOf(num);
    }
    private String getNumToStringWithPlusOrMinus(double num) {
        String str = String.valueOf(num);
        if ((num) > 0) str = "+" + str;
        return str;
    }
    private ArrayAdapter<Double> getAdapter(Double[] values) {
        ArrayAdapter<Double> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
    private ArrayAdapter<Integer> getAdapter(Integer[] values) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
