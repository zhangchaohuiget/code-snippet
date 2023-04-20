package com.app.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) { //相邻两个元素作比较，如果前面元素大于后面，进行交换
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,5,4,3,6,7,9,2,1,5};
        BubbleSort.bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
