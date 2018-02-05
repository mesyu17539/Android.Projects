package com.bitcamp.app.lotto;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by 1027 on 2018-02-05.
 */

public class Command {
    public static int making(){
        return (int) (Math.random()*44)+1;
    }
    public static String makNum(){
        String result="로또번호 : ";
        int[] nums=new int[6];
        int in=0;
        int num=making();
        nums[in++]=num;
        boolean bool;
        while(in!=6){
            bool=true;
            num=making();
            for(int i=0;i<in;i++){
                if(num==nums[i]){
                    bool=false;
                }
            }
            if(bool){
                nums[in++]=num;

            }
        }
        Arrays.sort(nums);
        for(int i=0;i<in;i++){
            result+=nums[i];
            if(i!=in){
                result+=", ";
            }
        }
        return result;
    }
}
