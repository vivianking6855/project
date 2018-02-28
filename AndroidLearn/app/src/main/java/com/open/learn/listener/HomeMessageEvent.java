package com.open.learn.listener;

/**
 * Created on 2018/2/26.
 */

public class HomeMessageEvent extends BaseMessageEvent {
    public HomeMessageEvent(int tag) {
        moduleTag = tag;
    }
}
