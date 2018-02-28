package com.open.learn.model;

import java.util.ArrayList;

/**
 * Created by vivian on 2017/9/26.
 * First Fragment Data API, will load data and deal with all logic here
 */

public class HomeDataApi {
    private final static String[] TYPES = new String[]{
            "Android知识库"
    };

    public static HomeModel getData() {
        HomeModel model = new HomeModel();
        model.mData = new ArrayList<>();
        for (String type : TYPES) {
            model.mData.add(new KnowledgeModel(type, type));
        }

        return model;
    }
}
