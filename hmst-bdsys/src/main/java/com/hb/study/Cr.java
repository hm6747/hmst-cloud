package com.hb.study;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/7/24 0024.
 */
public class Cr {
    static Configuration conf = null;
    static Table table = null;
    public static void main(String[] args) throws Exception {
        conf = HBaseConfiguration.create();// 配置
        conf.set("hbase.zookeeper.quorum", "hmstbd");// zookeeper地址
        conf.set("hbase.zookeeper.property.clientPort", "2181");// zookeeper端口
        System.setProperty("hadoop.home.dir", "G:/ha-2.6.5/hadoop-2.6.5");
        Connection Connection = ConnectionFactory.createConnection(conf);
        table = Connection.getTable(TableName.valueOf("test4"));
        insertData();
       /* createTable();*/
    }

    /**
     * 创建一个表
     *
     * @throws Exception
     */
    private static void createTable() throws Exception {
        // 创建表管理类
        HBaseAdmin admin = new HBaseAdmin(conf); // hbase表管理
        // 创建表描述类
        TableName tableName = TableName.valueOf("test4"); // 表名称
        HTableDescriptor desc = new HTableDescriptor(tableName);
        // 创建列族的描述类
        HColumnDescriptor family = new HColumnDescriptor("info"); // 列族
        // 将列族添加到表中
        desc.addFamily(family);
        HColumnDescriptor family2 = new HColumnDescriptor("info2"); // 列族
        // 将列族添加到表中
        desc.addFamily(family2);
        // 创建表
        admin.createTable(desc); // 创建表
    }

    public static  void insertData() throws Exception {
        table.setWriteBufferSize(534534534);
        ArrayList<Put> arrayList = new ArrayList<Put>();
        for (int i = 21; i < 50; i++) {
            Put put = new Put(Bytes.toBytes("1234"+i));
            put.add(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes("wangwu"+i));
            put.add(Bytes.toBytes("info"), Bytes.toBytes("password"), Bytes.toBytes(1234+i));
            arrayList.add(put);
        }

        //插入数据
        table.put(arrayList);
    }

    /**
     * 修改数据
     *
     * @throws Exception
     */
    public static void uodateData() throws Exception {
        Put put = new Put(Bytes.toBytes("1234"));
        put.add(Bytes.toBytes("info"), Bytes.toBytes("namessss"), Bytes.toBytes("lisi1234"));
        put.add(Bytes.toBytes("info"), Bytes.toBytes("password"), Bytes.toBytes(1234));
        //插入数据
        table.put(put);
    }

    /**
     * 删除数据
     *
     * @throws Exception
     */
    public static void deleteDate() throws Exception {
        Delete delete = new Delete(Bytes.toBytes("1234"));
        table.delete(delete);
    }

    /**
     * 单条查询
     *
     * @throws Exception
     */
    public static void queryData() throws Exception {
        Get get = new Get(Bytes.toBytes("1234"));
        Result result = table.get(get);
        System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"))));
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("namessss"))));
        System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex"))));
    }

    /**
     * 全表扫描
     *
     * @throws Exception
     */
    public static void scanData() throws Exception {
        Scan scan = new Scan();
        //scan.addFamily(Bytes.toBytes("info"));
        //scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("password"));
        scan.setStartRow(Bytes.toBytes("wangsf_0"));
        scan.setStopRow(Bytes.toBytes("wangwu"));
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
            //System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("password"))));
            //System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("name"))));
        }
    }

    /**
     * 全表扫描的过滤器
     * 列值过滤器
     *
     * @throws Exception
     */
    public static void scanDataByFilter1() throws Exception {

        // 创建全表扫描的scan
        Scan scan = new Scan();
        //过滤器：列值过滤器
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("info"),
                Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("zhangsan2"));
        // 设置过滤器
        scan.setFilter(filter);

        // 打印结果集
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
            //System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("password"))));
            //System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("name"))));
        }

    }
    /**
     * rowkey过滤器
     * @throws Exception
     */
    public static void scanDataByFilter2() throws Exception {

        // 创建全表扫描的scan
        Scan scan = new Scan();
        //匹配rowkey以wangsenfeng开头的
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^12341"));
        // 设置过滤器
        scan.setFilter(filter);
        // 打印结果集
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("password"))));
            System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name"))));
            //System.out.println(Bytes.toInt(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("password"))));
            //System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("name"))));
        }


    }

    /**
     * 匹配列名前缀
     * @throws Exception
     */
    public static void scanDataByFilter3() throws Exception {

        // 创建全表扫描的scan
        Scan scan = new Scan();
        //匹配rowkey以wangsenfeng开头的
        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("na"));
        // 设置过滤器
        scan.setFilter(filter);
        // 打印结果集
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println("rowkey：" + Bytes.toString(result.getRow()));
            System.out.println("info:name："
                    + Bytes.toString(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("name"))));
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")) != null) {
                System.out.println("info:age："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info"),
                        Bytes.toBytes("age"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex")) != null) {
                System.out.println("infi:sex："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info"),
                        Bytes.toBytes("sex"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("name")) != null) {
                System.out
                        .println("info2:name："
                                + Bytes.toString(result.getValue(
                                Bytes.toBytes("info2"),
                                Bytes.toBytes("name"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("age")) != null) {
                System.out.println("info2:age："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info2"),
                        Bytes.toBytes("age"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("sex")) != null) {
                System.out.println("info2:sex："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info2"),
                        Bytes.toBytes("sex"))));
            }
        }

    }
    /**
     * 过滤器集合
     * @throws Exception
     */
    public void scanDataByFilter4() throws Exception {

        // 创建全表扫描的scan
        Scan scan = new Scan();
        //过滤器集合：MUST_PASS_ALL（and）,MUST_PASS_ONE(or)
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        //匹配rowkey以wangsenfeng开头的
        RowFilter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("^wangsenfeng"));
        //匹配name的值等于wangsenfeng
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("info"),
                Bytes.toBytes("name"), CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("zhangsan"));
        filterList.addFilter(filter);
        filterList.addFilter(filter2);
        // 设置过滤器
        scan.setFilter(filterList);
        // 打印结果集
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println("rowkey：" + Bytes.toString(result.getRow()));
            System.out.println("info:name："
                    + Bytes.toString(result.getValue(Bytes.toBytes("info"),
                    Bytes.toBytes("name"))));
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info"), Bytes.toBytes("age")) != null) {
                System.out.println("info:age："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info"),
                        Bytes.toBytes("age"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info"), Bytes.toBytes("sex")) != null) {
                System.out.println("infi:sex："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info"),
                        Bytes.toBytes("sex"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("name")) != null) {
                System.out
                        .println("info2:name："
                                + Bytes.toString(result.getValue(
                                Bytes.toBytes("info2"),
                                Bytes.toBytes("name"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("age")) != null) {
                System.out.println("info2:age："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info2"),
                        Bytes.toBytes("age"))));
            }
            // 判断取出来的值是否为空
            if (result.getValue(Bytes.toBytes("info2"), Bytes.toBytes("sex")) != null) {
                System.out.println("info2:sex："
                        + Bytes.toInt(result.getValue(Bytes.toBytes("info2"),
                        Bytes.toBytes("sex"))));
            }
        }

    }

}
