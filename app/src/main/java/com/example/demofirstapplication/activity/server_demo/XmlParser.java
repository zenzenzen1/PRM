package com.example.demofirstapplication.activity.server_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XmlParser {
    public String getXmlFromUrl(String url) {
        HttpURLConnection connection = null;
        URL link = null;
        try {
            link = new URL(url);
            connection = (HttpURLConnection) link.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            
        } 
        return null;
    }
}
