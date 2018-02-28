package com.open.learn.model;

/**
 * Created on 2018/2/28.
 * KnowledgeModel include all knowledge items
 */

public class KnowledgeModel implements IDataModel {
    public String id;
    public String title;

    public KnowledgeModel(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
