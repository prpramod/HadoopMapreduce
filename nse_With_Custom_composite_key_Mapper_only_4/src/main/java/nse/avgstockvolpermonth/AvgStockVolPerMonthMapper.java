package nse.avgstockvolpermonth;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import nse.keyvalues.LongPair;
import nse.keyvalues.TextPair;
import nse.parsers.NYSEParser;



public class AvgStockVolPerMonthMapper extends Mapper<LongWritable,Text,TextPair,LongPair>{
	
	private static NYSEParser parser = new NYSEParser();
	private static TextPair mapOutputKey = new TextPair();
	private static LongPair mapOutputValue = new LongPair();
	
	public void map(LongWritable lineOffset,Text record,Context context) throws IOException,InterruptedException {
		
		
		parser.parse(record.toString());
		
		mapOutputKey.setFirst(new Text(parser.getTradeMonth()));
		mapOutputKey.setSecond(new Text(parser.getStockTicker()));
		
		mapOutputValue.setFirst(new LongWritable(parser.getVolume()));
		mapOutputValue.setSecond(new LongWritable(1));
		
		context.write(mapOutputKey, mapOutputValue);
		
		
	}

}
