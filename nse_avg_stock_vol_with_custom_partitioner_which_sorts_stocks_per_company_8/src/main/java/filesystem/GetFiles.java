package filesystem;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

public class GetFiles {

	public static void main(String[] args)  throws Exception {
		// TODO Auto-generated method stub

		
		String uri = args[0];
		
		Configuration conf = new Configuration();
		
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path path = new Path(args[0]+"/nyse201?");
		// FileStatus [] status = fs.listStatus(path);
		
		FileStatus [] status = fs.globStatus(path); 
		
		// globstatus can detect path based on patterns 
		// e.g /Users/pramod/ny* it will list all paths e.g ../../nyseData ../../nyseOutPut etc
//    Path path = new Path(args[0]+"/nyse201[1-3]"); --> nyse2011,nyse2012,nyse2013
		// /nyse201? --> nyse2010,nyse2011,nyse2012,nyse2013
		
		Path[] paths = FileUtil.stat2Paths(status);
		
		
		for(Path p:paths)
		{
			
			System.out.println(p.toString());
		}
		
	}

}
