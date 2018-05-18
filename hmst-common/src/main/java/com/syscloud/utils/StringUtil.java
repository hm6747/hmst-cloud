package com.syscloud.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15 0015.
 */
public class StringUtil {
    public  static  List<Integer> strSplitToList(String str,String prefix){
        List<Integer> list = Lists.newArrayList();
        if(StringUtils.isEmpty(str)){
            return Lists.newArrayList();
        }
        String[] listArr;
        if(str.indexOf(prefix) != -1){
            listArr = str.split(prefix);
        }else{
             listArr = new String[]{str};
        }
        for (int i = 0; i < listArr.length; i++) {
            list.add(Integer.parseInt(listArr[i]));
        }
        return  list;
    }
}
