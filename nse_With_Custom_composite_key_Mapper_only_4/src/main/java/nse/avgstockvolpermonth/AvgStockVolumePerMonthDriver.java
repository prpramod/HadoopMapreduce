package nse.avgstockvolpermonth;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import nse.keyvalues.LongPair;
import nse.keyvalues.TextPair;

public class AvgStockVolumePerMonthDriver extends Configured implements  Tool {



	public int run(String[] arg) throws Exception {
		
		Job job = Job.getInstance(this.getConf());
		
		job.setJarByClass(getClass()); // to Run the Jar on Hadoop cluster without issues
		
		FileInputFormat.setInputPaths(job,new Path(arg[0]));
		job.setInputFormatClass(CombineTextInputFormat.class); 
		// if you have too many small files then CombineTextInputFormat should be used 
		// or else Too many mappers equal to no of files will be created
		// CombineTextInputFormat combines all the textfiles 
		job.setMapperClass(AvgStockVolPerMonthMapper.class);
		job.setMapOutputKeyClass(TextPair.class);
		job.setMapOutputValueClass(LongPair.class);
		
		
		/*
		 * 
		 * 
		 
		 
		 f MapReduce programmer do not set the Reducer Class using job.setReducerClass then IdentityReducer.class is used as a default value. if you would only want to sort your input. An identity reducer can be used for example to implement embarrasingly parallel algorithms where you just use the mappers to perform the parallel tasks but you want the output key value pairs to be sorted. output will be part-r-xxxxx.

if you set

job.setNumReduceTasks(0);
in this condition no reducer will run and output of program will named as part-m-xxxxx. Output will be not sorted.
		 
		 
		 * 
		 * 
		 */
		
		job.setNumReduceTasks(0);
		
		
		FileOutputFormat.setOutputPath(job, new Path(arg[1]));
		
		
		
		return job.waitForCompletion(true)? 0:1;
	}

	public static void main(String[] args) throws Exception {
		
		System.exit(ToolRunner.run(new AvgStockVolumePerMonthDriver(), args));

	}
	
	
}
