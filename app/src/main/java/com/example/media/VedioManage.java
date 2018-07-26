package com.example.media;

import java.util.ArrayList;
import java.util.List;

import kotlin.Function;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class VedioManage {
    private VedioManage() {

    }

    private static VedioManage instance;

    public static VedioManage getInstance() {
        if (instance == null) {
            synchronized (VedioManage.class) {
                if (instance == null) {
                    instance = new VedioManage();
                }

            }
        }
        return instance;
    }

    private List<Function1<byte[], Unit>> list = new ArrayList<>();


    public void addFftListener(Function1<byte[], Unit> function1) {
        list.add(function1);
    }

    public void notifyFft(byte[] bytes) {
        for (Function1 function1 : list) {
            function1.invoke(bytes);
        }
    }

    public void clearFftListener() {
        list.clear();
    }


}
