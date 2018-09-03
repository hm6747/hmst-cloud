import com.mrc.clicksteam.mr.ClickReducer;
import com.mrc.clicksteam.mr.ClickStreamThree;
import com.mrc.clicksteam.mrbean.WebLogBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/7/5 0005.
 */

/**
 * 这是统计单词个数的例子
 *
 * Created by zhangws on 16/7/31.
 */
public class App {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("dfs.client.use.datanode.hostname","true");
        //本地调试模式
        conf.set("mapreduce.framework.name","local");
        conf.set("fs.defaultFS","file:///");
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }
        //先删除output目录
        rmr(conf, otherArgs[otherArgs.length - 1]);
        Job job = Job.getInstance(conf, "WordsCount");
        job.setJarByClass(App.class);
/*
        job.setJar("G:\\hm\\codes\\hmst-bdsys\\out\\artifacts\\hmst_bdsys_jar\\hmst-bdsys.jar");
*/

    /*    job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);*/
 /*     job.setMapperClass(WeblogPreProcess.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setNumReduceTasks(0);*/


        job.setMapperClass(ClickStreamThree.class);
        job.setReducerClass(ClickReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(WebLogBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);


   /*     job.setMapperClass(VisitMap.class);
        job.setReducerClass(VisitReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(PageViewsBean.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(VisitBean.class);*/

/*        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);*/

        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));

        if (job.waitForCompletion(true)) {
            cat(conf, otherArgs[1] + "/part-r-00000");
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }

    /**
     * 删除指定目录
     *
     * @param conf
     * @param dirPath
     *
     * @throws IOException
     */
    private static Boolean rmr(Configuration conf, String dirPath) throws IOException {
        boolean delResult = false;
//        FileSystem fs = FileSystem.get(conf);
        Path targetPath = new Path(dirPath);
        FileSystem fs = targetPath.getFileSystem(conf);
        if (fs.exists(targetPath)) {
            delResult = fs.delete(targetPath, true);
            if (delResult) {
                System.out.println(targetPath + " has been deleted sucessfullly.");
            } else {
                System.out.println(targetPath + " deletion failed.");
            }
        }
        return delResult;
    }

    /**
     * 输出指定文件内容
     *
     * @param conf     HDFS配置
     * @param filePath 文件路径
     *
     * @return 文件内容
     *
     * @throws IOException
     */
    public static void cat(Configuration conf, String filePath) throws IOException {

//        FileSystem fileSystem = FileSystem.get(conf);
        InputStream in = null;
        Path file = new Path(filePath);
        FileSystem fileSystem = file.getFileSystem(conf);
        try {
            in = fileSystem.open(file);
            IOUtils.copyBytes(in, System.out, 4096, true);
        } finally {
            if (in != null) {
                IOUtils.closeStream(in);
            }
        }
    }
}
