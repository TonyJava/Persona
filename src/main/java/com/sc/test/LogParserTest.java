package com.sc.test;

import com.sc.util.LogParser;
import org.junit.Test;

/**
 * Created by LONG on 2016/9/10.
 */
public class LogParserTest {



    @Test
    public void test(){

        String str = "110.52.250.126 - - [30/Mar/2015:17:38:20 +0800] \"GET /static/image/common/logo.png HTTP/1.1\" 200 4542";
        String[] s = new LogParser().parser(str);
        for (String s1 : s) {

            System.out.println(s1);

        }



    }


}
