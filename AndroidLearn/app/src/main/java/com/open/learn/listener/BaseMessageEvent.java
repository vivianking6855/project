package com.open.learn.listener;

import com.open.learn.model.IDataModel;

/**
 * Created on 2018/2/26.
 */

public class BaseMessageEvent {
    // module tag, to recognize which model is working
    public int moduleTag;
    // data load status
    public int loadStatus;
    public final static int STATUS_FAIL = 0;
    public final static int STATUS_START = 1;
    public final static int STATUS_SUCCESS = 2;
    public final static int STATUS_REFRESH_SUCCESS = 3;
    public final static int STATUS_REFRESH_FAIL = 4;

    // data model
    public IDataModel dataModel;

    // error message
    public String errorMsg;

    public BaseMessageEvent setDataModel(IDataModel model) {
        dataModel = model;
        return this;
    }

    public BaseMessageEvent setLoadStatus(int status) {
        loadStatus = status;
        return this;
    }

    public BaseMessageEvent setModelTag(int tag) {
        moduleTag = tag;
        return this;
    }

    public BaseMessageEvent setErrorMsg(String msg) {
        errorMsg = msg;
        return this;
    }


}
