package com.open.learn.model;

import android.os.SystemClock;

import java.util.ArrayList;

/**
 * Created by vivian on 2017/9/26.
 * Second Fragment Data API, will load data and deal with all logic here
 */

public class SecondDataApi {

    public static SecondModel getData() {
        // do your consume work here
        SystemClock.sleep(2000);

        return mock();
    }

    private static SecondModel mock() {
        SecondModel model = new SecondModel();
        model.titleArray = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            model.titleArray.add("" + (char) i);
        }

        return model;
    }

    public static SecondModel refreshData() {
        // do your consume work here
        SystemClock.sleep(2000);

        return mock2();
    }

    private static SecondModel mock2() {
        SecondModel model = new SecondModel();
        model.titleArray = new ArrayList<>();
        for (int i = 'S'; i <= 'Y'; i++) {
            model.titleArray.add("" + (char) i);
        }

        return model;
    }


}
