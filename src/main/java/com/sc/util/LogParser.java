package com.sc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LONG on 2016/9/10.
 */
public class LogParser {


    /**
     * 日志解析
     *
     * @param line
     */
    public String[] parser(String line){



        String pattern = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}) - - \\[(.*) ([-|+][0-9]{1,4})\\] \"([A-Z]{1,4}) (.*) HTTP/1.1\" ([0-9]*) ([0-9]*)";
        Matcher matcher = Pattern.compile(pattern).matcher(line);
        if(matcher.find()){

            String ip = matcher.group(1);
            String time = matcher.group(2);
            String timeArea = matcher.group(3);
            String request = matcher.group(4);
            String url = matcher.group(5);
            String status = matcher.group(6);
            String dataSize = matcher.group(7);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
            try {

                time = dateFormat.format(dateFormat1.parse(time));


            } catch (ParseException e) {
                e.printStackTrace();
            }


            return new String[]{ip,time,timeArea,request,url,status,dataSize};

        }


        return new String[]{};
    }



}
