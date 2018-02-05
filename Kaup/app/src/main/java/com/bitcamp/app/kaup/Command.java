package com.bitcamp.app.kaup;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 1027 on 2018-02-05.
 */

public class Command {
    public static Double changeDouble(EditText eText){
        return Double.parseDouble(String.valueOf(eText.getText()));
    }
    public static String kaup(Double wei,Double hei,String name){
        String result=name;
        Double bmi= (wei/((hei*hei)/10000.0));
        if(bmi>=35){
           result+="님은 고도비만입니다" ;
        }else if(bmi>25&&bmi<35){
            result+="님은 중등,경도 비만입니다";
        }else if(bmi<35&&bmi>18){
            result+="님은 과체중입니다";
        }else if(bmi<18){
            result+="님은 저체중입니다";
        }
        return result+bmi;
    }
}
