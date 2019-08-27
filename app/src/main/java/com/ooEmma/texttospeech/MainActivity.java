package com.ooEmma.texttospeech;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

        Button btnSpeak;
        EditText editSpeak;
        TextToSpeech textToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSpeak = (EditText) findViewById(R.id.ed_speak);
        btnSpeak = (Button) findViewById(R.id.btn_speak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override

            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if(result == textToSpeech.LANG_MISSING_DATA || result == textToSpeech.LANG_NOT_SUPPORTED)
                    {
                        Toast.makeText(MainActivity.this, "This language is not supported", Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        btnSpeak.setEnabled(true);
                        textToSpeech.setPitch(0.6f);
                        textToSpeech.setSpeechRate(0.6f);
                        speak();
                    }
                }
            }
        });



    }

    private void speak() {
        String text = editSpeak.getText().toString();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech != null)
        {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
