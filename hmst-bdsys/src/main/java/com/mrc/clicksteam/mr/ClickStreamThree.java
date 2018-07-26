package com.mrc.clicksteam.mr;

import com.mrc.clicksteam.mrbean.WebLogBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 将清洗之后的日志梳理出点击流日志
 * 应该先执行WeblogPreValid清洗日志，得到所有valid类型的记录
 * <p>
 * 相比Two，给每一次visit增加了session（随机uuid）
 *
 * @author
 */
public class ClickStreamThree extends Mapper<LongWritable, Text, Text, WebLogBean> {


    Text k = new Text();
    WebLogBean v = new WebLogBean();

    @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();

        String[] fields = line.split("\001");
        if (fields.length < 9) return;
        //将切分出来的各字段set到weblogbean中
        v.set("true".equals(fields[0]) ? true : false, fields[1], fields[2], fields[3], fields[4], fields[5], fields[6], fields[7], fields[8]);
        //只有有效记录才进入后续处理
        if (v.isValid()) {
            k.set(v.getRemote_addr());
            context.write(k, v);//ip地址作为key, 发到reduce中
        }
    }

    private String toStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(date);
    }

    private Date toDate(String timeStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.parse(timeStr);
    }

    private long timeDiff(String time1, String time2) throws ParseException {
        Date d1 = toDate(time1);
        Date d2 = toDate(time2);
        return d1.getTime() - d2.getTime();

    }

    private long timeDiff(Date time1, Date time2) throws ParseException {

        return time1.getTime() - time2.getTime();

    }


    public static void main(String[] args) throws Exception {
/*
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(ClickStreamThree.class);

        job.setMapperClass(ClickStreamMapper.class);
        job.setReducerClass(ClickStreamReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(WebLogBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

//		 FileInputFormat.setInputPaths(job, new Path(args[0]));
//		 FileOutputFormat.setOutputPath(job, new Path(args[1]));

        FileInputFormat.setInputPaths(job, new Path("D:/temp/temp/part-m-00000"));
        FileOutputFormat.setOutputPath(job, new Path("D:/temp/pageviews"));

        job.waitForCompletion(true);*/

    }

}
