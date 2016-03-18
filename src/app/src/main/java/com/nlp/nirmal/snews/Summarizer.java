package com.nlp.nirmal.snews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Summarizer {
    /**
     * URL of article to summarize.
     */
    URL url;
    /**
     * Default constructor for Summarizer class.
     * @param inputURL Provide URL as string. 
     * @throws MalformedURLException Make sure you account for this in main.
     */
    public Summarizer(String inputURL) throws MalformedURLException {
        inputURL = "https://api-2445581399224.apicast.io/api/v1/summarize?sourceURL="  
                + inputURL + "&user_key=587f1ef466e35c900b30d249043e3896";
        this.url = new URL(inputURL);
    }
    
    /**
     * Gets summary of URL. Returned as string.
     * @return Summary of URL.
     */
    public String getSummary() {
        String summary = new String("");
        InputStream stream = null;
        try {
            stream = this.url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("URL is invalid.");
            return null;
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            String rawText = readURL(reader);
            JSONObject json = new JSONObject(rawText);
            summary = json.get("summary").toString();
            summary = summary.replace("<br/><br/>", "\n");
            stream.close();
        } catch (IOException e) {
            System.err.println("There was an error with processing the data.");
            return null;
        } catch (JSONException e) {
            System.err.println("There was an error with processing the data.");
            return null;
        }
        return summary;
    }

    /**
     * Converts URL data to string.
     * @param reader
     * @return
     * @throws IOException
     */
    private static String readURL(Reader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        int toAdd;
        while ((toAdd = reader.read()) != -1) {
            builder.append((char) toAdd);
        }
        return builder.toString();
    }

    public JSONObject getJSON() {
        InputStream stream = null;
        JSONObject json = null;
        try {
            stream = this.url.openStream();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("URL is invalid.");
            return null;
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            String rawText = readURL(reader);
            json = new JSONObject(rawText);
            stream.close();
        } catch (IOException e) {
            System.err.println("There was an error with processing the data.");
            return null;
        } catch (JSONException e) {
            System.err.println("There was an error with processing the data.");
            return null;
        }
        return json;
    }
}
