package com.sc.data;

import com.sc.util.LogParser;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by LONG on 2016/9/10.
 */
public class Clean {




    public static void main(String[] args) {


        try {
            Job job = Job.getInstance(new Configuration(),Clean.class.getSimpleName());

            //添加输入和输出路径
            FileInputFormat.addInputPath(job,new Path(args[0]));
            FileOutputFormat.setOutputPath(job,new Path(args[1]));

            //map序列化
            job.setMapperClass(MyMapper.class);
            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(Text.class);


            //reduce序列化
            job.setReducerClass(MyReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);


            job.setJarByClass(Clean.class);
            job.waitForCompletion(true);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    static class MyMapper extends Mapper<LongWritable, Text, LongWritable, Text> {


        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {


            String line = value.toString();
            String[] parser = new LogParser().parser(line);
            if (line.contains(".jpg") || line.contains(".gif") || line.contains(".png") || line.contains(".css")) {

                return;

            }

            if (parser.length != 7) {


                return;
            }

            Text text = new Text();
            text.set(
                    parser[0] + "\t"
                            + parser[1] + "\t"
                            + parser[2] + "\t"
                            + parser[3] + "\t"
                            + parser[4] + "\t"
                            + parser[5] + "\t"
                            + parser[6] + "\t");


            context.write(key,text);


        }
    }

    static class MyReducer extends Reducer<LongWritable, Text, Text, NullWritable> {


        @Override
        protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


            for (Text value : values) {


                context.write(value,NullWritable.get());


            }






        }
    }


}
