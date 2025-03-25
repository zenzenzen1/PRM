package com.example.tourbooking.helpler;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {
    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);
        return formatter.format(amount);
    }
    
    public static String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE dd/M/yyyy", Locale.US);
        return formatter.format(date);
    }
}