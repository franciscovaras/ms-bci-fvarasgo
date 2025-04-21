package com.fvarasgo.bci.demo.msbcifvarasgo.utils;

import net.minidev.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    private static JSONObject json = null;
    private static final String FMT_FECHA_YYYY_MM_DD_GUION = "yyyy-MM-dd";

    public static String messageJson(String value) {
        json = new JSONObject();
        return json.put("message", value).toString();
    }

    //obtener fecha actual
    public static Date obtenerDiaActual() {
        SimpleDateFormat formatter = new SimpleDateFormat(FMT_FECHA_YYYY_MM_DD_GUION);
        return formatter.get2DigitYearStart();
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
