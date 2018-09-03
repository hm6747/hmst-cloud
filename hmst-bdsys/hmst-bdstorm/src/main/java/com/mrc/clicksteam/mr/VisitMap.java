package com.mrc.clicksteam.mr;

import com.mrc.clicksteam.mrbean.PageViewsBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Administrator on 2018/7/16 0016.
 */
public class VisitMap extends Mapper<LongWritable, Text, Text, PageViewsBean> {

    PageViewsBean pvBean = new PageViewsBean();
    Text k = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split("\001");
        int step = Integer.parseInt(fields[6]);
        pvBean.set(fields[0], fields[1], fields[2], fields[3], step, fields[5], fields[6], fields[7], fields[8], fields[9]);
        k.set(pvBean.getSession());
        context.write(k, pvBean);
    }
}
