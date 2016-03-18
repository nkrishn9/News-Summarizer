package com.nlp.nirmal.snews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DisplayMessageActivity extends Activity {

    TextView summary;
    TextView title;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        this.url = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        title = (TextView) findViewById(R.id.titleTextView);
        title.setText("Title loading...");
        summary = (TextView) findViewById(R.id.sumTextView);
        summary.setText("Summary loading...");
        new PostTaskJSON().execute(this.url);
    }


    // The definition of our task class
    private class PostTaskJSON extends AsyncTask<String, Void, JSONObject> {
        Drawable imageToAdd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imageToAdd = null;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String url=params[0];
            JSONObject toReturn = null;
            try {
                Summarizer sum = new Summarizer(url);
                toReturn = sum.getJSON();
                URL iURL = new URL((String) toReturn.get("imageURL"));
                InputStream is = (InputStream) iURL.getContent();
                this.imageToAdd = Drawable.createFromStream(is, "src name");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            }
            return toReturn;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result == null) {
                title.setText("The URL you entered was invalid.");
                summary.setText("");
            } else {
                try {
                    if (result.has("title") && result.has("sourceURL")) {
                        //title.setText((CharSequence) result.get("title"));
                        title.setClickable(true);
                        title.setMovementMethod(LinkMovementMethod.getInstance());
                        //String text = "<a href='http://www.google.com'> Google </a>";
                        String text = "<a href= '" + result.get("sourceURL") + "'>" + result.get("title") + "</a>";
                        title.setText(Html.fromHtml(text));
                    } else {
                        title.setText("Title data not available.");
                    }
                    if (result.has("summary")) {
                        String sum = ((String) result.get("summary")).replace("<br/><br/>", " ");
                        summary.setText((CharSequence) sum);
                    } else {
                        summary.setText("Summary data not available.");
                    }
                    ImageView i = (ImageView) findViewById(R.id.imageView2);
                    if (imageToAdd != null) {
                        i.setImageDrawable(imageToAdd);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    title.setText("URL invalid");
                }
            }
        }
    }

}
