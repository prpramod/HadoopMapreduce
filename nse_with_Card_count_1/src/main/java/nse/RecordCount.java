package nse;

import java.io.IOException;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.lib.reduce.LongSumReducer;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class RecordCount extends Configured implements Tool

{

	private static class RecordMapper extends Mapper<LongWritable,Text,Text,LongWritable>
	{
		
		public void map(LongWritable lineOffSet,Text record,Context output) throws IOException,InterruptedException
		  {
			
			output.write(new Text("Count"),new LongWritable(1));
			
		  }
	}
		
	
	public int run(String[] arg) throws Exception {
		Job job = Job.getInstance(getConf());

		
		// If we do not add any mapper/Reducer then these two classes will be used by default 	
		job.setMapperClass(RecordMapper.class);  
	  
		
		 
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		
		job.setMapOutputKeyClass(Text.class); // Default is Text so this line could be skipped 
		job.setMapOutputValueClass(LongWritable.class); // We need to specify this since we are using LongWritable here 
		
		
		  job.setReducerClass(LongSumReducer.class); // Using predefined Reducer
		  
		  // Specifying output key/value type (for reducer) 
		  
		  job.setOutputKeyClass(Text.class); // Default is Text so this line could be skipped 
		  job.setOutputValueClass(LongWritable.class); // We need to specify this since we are using LongWritable here 
			
		  job.setNumReduceTasks(1); // By default it invokes 1 reduce task
		
		// FileInputFormat.setInputPaths(job,new Path("/Users/pramod/deckofcards.txt"));
		// FileOutputFormat.setOutputPath(job,new Path("/Users/pramod/RecordCountWithMapperNLongSumReducer"));
		
		  FileInputFormat.setInputPaths(job, new Path(arg[0]));
		  FileOutputFormat.setOutputPath(job,new Path(arg[1]));
		  
			
		  
		return job.waitForCompletion(true) ? 0: 1;
	}
	
	
	
	public static void main (String args[]) throws Exception
	{
	
		
		System.exit(ToolRunner.run(new RecordCount(), args));
		
		
	}
}
