package com.mrc.clicksteam.mr;

import com.mrc.clicksteam.mrbean.WebLogBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/7/16 0016.
 */
public class ClickReducer extends Reducer<Text, WebLogBean, NullWritable, Text> {
    Text v = new Text();

    @Override
    protected void reduce(Text key, Iterable<WebLogBean> values, Context context) throws IOException, InterruptedException {
        ArrayList<WebLogBean> beans = new ArrayList<WebLogBean>();

        /**
         * 将一个用户的访问记录拿出来放到集合中, 然后进行排序
         */
        try {
            for (WebLogBean bean : values) {
                WebLogBean webLogBean = new WebLogBean();
                try {
                    BeanUtils.copyProperties(webLogBean, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                beans.add(webLogBean);
            }
            //将bean按时间先后顺序排序
            Collections.sort(beans, new Comparator<WebLogBean>() {
                public int compare(WebLogBean o1, WebLogBean o2) {
                    try {
                        Date d1 = toDate(o1.getTime_local());
                        Date d2 = toDate(o2.getTime_local());
                        if (d1 == null || d2 == null)
                            return 0;
                        return d1.compareTo(d2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }

            });

            /**
             * 以下逻辑为：从有序bean中分辨出各次visit，并对一次visit中所访问的page按顺序标号step
             * 	只有一条
             * 	有多条
             * 		第一条 continue
             * 		不是第一条
             * 			时间在30分钟内吗?在, 写出去, step+1, 不在就step =1, 重置sessionid
             * 		最后一条
             * 			写出去, 设置默认停留市场为60s
             */

            int step = 1;
            String session = UUID.randomUUID().toString();
            for (int i = 0; i < beans.size(); i++) {
                WebLogBean bean = beans.get(i);
                // 如果仅有1条数据，则直接输出
                if (1 == beans.size()) {

                    // 设置默认停留市场为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemote_user() + "\001" + bean.getTime_local() + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttp_referer() + "\001" + bean.getHttp_user_agent() + "\001" + bean.getBody_bytes_sent() + "\001"
                            + bean.getStatus());
                    context.write(NullWritable.get(), v);
                    session = UUID.randomUUID().toString();
                    break;
                }

                // 如果不止1条数据，则将第一条跳过不输出，遍历第二条时再输出
                if (i == 0) {
                    continue;
                }

                // 求近两次时间差
                long timeDiff = timeDiff(toDate(bean.getTime_local()), toDate(beans.get(i - 1).getTime_local()));
                // 如果本次-上次时间差<30分钟，则输出前一次的页面访问信息

                if (timeDiff < 30 * 60 * 1000) {

                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemote_user() + "\001" + beans.get(i - 1).getTime_local() + "\001" + beans.get(i - 1).getRequest() + "\001" + step + "\001" + (timeDiff / 1000) + "\001" + beans.get(i - 1).getHttp_referer() + "\001"
                            + beans.get(i - 1).getHttp_user_agent() + "\001" + beans.get(i - 1).getBody_bytes_sent() + "\001" + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    step++;
                } else {

                    // 如果本次-上次时间差>30分钟，则输出前一次的页面访问信息且将step重置，以分隔为新的visit
                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemote_user() + "\001" + beans.get(i - 1).getTime_local() + "\001" + beans.get(i - 1).getRequest() + "\001" + (step) + "\001" + (60) + "\001" + beans.get(i - 1).getHttp_referer() + "\001"
                            + beans.get(i - 1).getHttp_user_agent() + "\001" + beans.get(i - 1).getBody_bytes_sent() + "\001" + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    // 输出完上一条之后，重置step编号
                    step = 1;
                    session = UUID.randomUUID().toString();
                }

                // 如果此次遍历的是最后一条，则将本条直接输出
                if (i == beans.size() - 1) {
                    // 设置默认停留市场为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemote_user() + "\001" + bean.getTime_local() + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttp_referer() + "\001" + bean.getHttp_user_agent() + "\001" + bean.getBody_bytes_sent() + "\001" + bean.getStatus());
                    context.write(NullWritable.get(), v);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();

        }

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
}
