package com.open.learn.model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by vivian on 2017/9/26.
 * model for second Fragment
 */

public class SecondModel implements IDataModel{
    public List<String> titleArray;

    @Override
    public String toString() {
        return StringUtils.join(titleArray.toArray(), ",");
    }
}
