package com.taotao.tao_oat_library;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by space on 2018/6/25 0025.
 */

public class AigorithmDemo {

    public void maopao(){
        ArrayList<Integer> list=new ArrayList<>();
        int temp=0;
        int size =list.size();

        for(int i=0;i<list.size()-1;i++){
            for(int j=0;j<list.size()-i-1;j++){
                if (temp>list.get(j+1)){
                    temp=list.get(j);
                    list.set(j,list.get(j+1));
                    list.set(j+1,temp);
                }
            }
        }
    }
    public void kuaiSu(int []arr,int low,int hight){

        int middle=getMiddle(arr,low,hight);
        kuaiSu(arr,low,middle-1);
        kuaiSu(arr,middle+1,hight);

    }
    public int getMiddle(int [] arr,int low,int hight){
        int temp=arr[low];
        while(low<hight){
            while(low<hight&&arr[hight]>temp){
                hight--;
            }
            arr[hight]=arr[low];
            while (low < hight && arr[low]<temp) {
                low++;
            }
            arr[low]=arr[hight];
        }
        arr[low]=temp;
        return low;
    }


    public void xuanzhe(int[] arr){
        int size=arr.length;
        int temp=0;
        for(int i=0;i<size;i++){
            int k=i;
            //选择出剩下元素最小的一个
            for(int j=size-1;j>i;j--){
                if (arr[j] < arr[k]) {
                    k=j;
                }
            }
            temp=arr[i];
            arr[i]=arr[k];
            arr[k]=temp;
        }
    }


}
