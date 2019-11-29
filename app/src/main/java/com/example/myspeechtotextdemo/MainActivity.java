package com.example.myspeechtotextdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myspeechtotextdemo.adapters.RecyclerAdapter;
import com.example.myspeechtotextdemo.model.DictionaryModel;
import com.example.myspeechtotextdemo.pojos.Dictionary;
import com.example.myspeechtotextdemo.pojos.DictionaryListData;
import com.example.myspeechtotextdemo.utils.APIClient;
import com.example.myspeechtotextdemo.utils.AppUtil;
import com.example.myspeechtotextdemo.utils.EndPointInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myspeechtotextdemo.utils.AppUtil.showSimpleDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RelativeLayout ryt_button_speak;
    RecyclerView recyclerView;
    List<DictionaryModel> dictionaryDataList = new ArrayList<>();
    RecyclerAdapter adapter;

    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;


    TextView txt_speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        darkenStatusBar();
        setContentView(R.layout.activity_main);

        // For binding xml with java code using Id's
        initialize();

        // Calling Dictionary Api
        modelNumberListApiCall();


        // Checking run time permission for android version 6.0 or greater
        checkPermission();



        // Setting adapter for recycler view
        adapter = new RecyclerAdapter(this,dictionaryDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);


        // Initializing Speech Recognizer
         mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);


        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        // Setting Listener for Speech Recognizer
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                txt_speak.setText("Ready...");
            }

            @Override
            public void onBeginningOfSpeech() {

                txt_speak.setText("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

                txt_speak.setText("SPEAK");
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null){

                    Log.e("Return Data",""+matches.get(0));

                    String spokenWord = matches.get(0);

                    if(spokenWord.equalsIgnoreCase("XX")){

                        spokenWord = "Twenty";
                    }else if(spokenWord.equalsIgnoreCase("second exit")){

                        spokenWord = "2nd Exit";
                    }

                    changeItemBackgroundColor(spokenWord);

                }




            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });



        // Setting listener for button click
        ryt_button_speak.setOnClickListener(this);


    }

    private void initialize() {
        ryt_button_speak = findViewById(R.id.ryt_button_speak);
        recyclerView = findViewById(R.id.rcv_word_list);
        txt_speak = findViewById(R.id.txt_speak);
        pref = AppUtil.getAppPreferences(this);
        editor = pref.edit();
    }


    private void modelNumberListApiCall() {

        if (AppUtil.isNetworkAvailable(this)) {
           // final AlertDialog dialog = AppUtil.showIndeterminateProgressDialog(this, getString(R.string.connecting_to_server), getString(R.string.please_wait));

            //   final Dialog dialog = AppUtil.showProgressGif(this);
            EndPointInterface apiService = APIClient.getClient().create(EndPointInterface.class);
            Call<DictionaryListData> call = apiService.DictionaryFetchService();
            call.enqueue(new Callback<DictionaryListData>() {
                @Override
                public void onResponse(Call<DictionaryListData> call, Response<DictionaryListData> response) {
                    //   dialog.dismiss();
                    if (response.code() == 200) {

                        DictionaryListData modelListResponse = response.body();


                        buildDictionaryData(modelListResponse.getDictionary());



                    } else {
                        showSimpleDialog(MainActivity.this, getString(R.string.server_error), getString(R.string.could_not_connect_to_server));
                    }
                }

                @Override
                public void onFailure(Call<DictionaryListData> call, Throwable t) {
                     // dialog.dismiss();
                    showSimpleDialog(MainActivity.this, getString(R.string.server_error), getString(R.string.server_error_occurred));
                }
            });
        }

    }

    // Building data set to pass to recycler view adapter
    private void buildDictionaryData(List<Dictionary> dictionary) {


        dictionaryDataList.clear();


        for(int i = 0;i<dictionary.size();i++){

            DictionaryModel model = new DictionaryModel(dictionary.get(i).getWord(),dictionary.get(i).getFrequency(),false);
            dictionaryDataList.add(model);
        }

        wordFrequencySort(dictionaryDataList);

        if(adapter!= null){

            adapter.notifyDataSetChanged();
        }
    }


    // Highlighting the particular cell of recycler view when the word spoken is present inside the Dictionary.
    private void changeItemBackgroundColor(String returnedText){

        boolean isDataMatch = false;
        for(int i = 0;i<dictionaryDataList.size();i++){

            if(dictionaryDataList.get(i).getWord().equalsIgnoreCase(returnedText)){

                Log.e("Match","Data matches");
                isDataMatch = true;
                int frequency = dictionaryDataList.get(i).getFrequency();

                frequency++;

                dictionaryDataList.set(i,new DictionaryModel(dictionaryDataList.get(i).getWord(),frequency,true));
                wordFrequencySort(dictionaryDataList);
                adapter.notifyDataSetChanged();

                recyclerView.getLayoutManager().scrollToPosition(i);

            }else{

                Log.e("Match","Data does not matches");
               // break;
            }
        }

        if(!isDataMatch){

            isDataMatch = false;

            showDialog(this,"The Word " + "\' "+returnedText+" \'" +" is not present inside Dictionary");

        }else{

            showDialog(this,"You spoke " + "\' "+returnedText+" \'"                                              );

        }

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    public void onClick(View view) {

        if(view == ryt_button_speak){

            Log.e("Button","clicked");
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);




        }

    }


    // Sorting the elements in the recycler view according to the frequency of the word spoken.
    static void wordFrequencySort(List<DictionaryModel> dictionaryDataList) {
        int n = dictionaryDataList.size();
        DictionaryModel temp ;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(dictionaryDataList.get(j-1).getFrequency() < dictionaryDataList.get(j).getFrequency()){
                    //swap elements
                    temp = dictionaryDataList.get(j-1);
                    dictionaryDataList.set(j-1,dictionaryDataList.get(j));
                    dictionaryDataList.set(j,temp);


                }

            }
        }



    }

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public  void darkenStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#00574B"));

        }


    }





}

