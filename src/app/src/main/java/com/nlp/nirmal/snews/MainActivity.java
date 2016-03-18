package com.nlp.nirmal.snews;

import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button summarize;
    Button copy;
    Button clear;
    EditText text;
    public final static String EXTRA_MESSAGE = "com.Summarize.Message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summarize = (Button) findViewById(R.id.submit);
        text = (EditText) findViewById(R.id.editText);
        clear = (Button) findViewById(R.id.clear);
        copy = (Button) findViewById(R.id.copyClip);
    }

    public void clearOnClick(View v) {
        if (text != null) {
            text.setText("");
        }
    }

    public void copyOnClick(View v) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (clipboard.getPrimaryClip() != null) {
            String url = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            if (text != null) {
                text.setText((CharSequence) url);
            }
        }
    }

    public void sumOnClick(View v) {
        Intent startNewActivity = new Intent(this, DisplayMessageActivity.class);
        String message = text.getText().toString();
        startNewActivity.putExtra(EXTRA_MESSAGE, message);
        startActivity(startNewActivity);
    }
}
