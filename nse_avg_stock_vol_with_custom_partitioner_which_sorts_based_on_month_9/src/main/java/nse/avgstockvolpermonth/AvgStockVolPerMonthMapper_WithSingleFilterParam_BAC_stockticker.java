package nse.avgstockvolpermonth;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import nse.keyvalues.LongPair;
import nse.keyvalues.TextPair;
import nse.parsers.NYSEParser;



public class AvgStockVolPerMonthMapper_WithSingleFilterParam_BAC_stockticker extends Mapper<LongWritable,Text,TextPair,LongPair>{
	
	private static NYSEParser parser = new NYSEParser();
	private static TextPair mapOutputKey = new TextPair();
	private static LongPair mapOutputValue = new LongPair();
	// setup() will be invoked 1st before map() and per JVM only  once but map() will be invoked the no of times 
	// as many records 
	// cleanup() will be invoked only  once per JVM just before termination of JVM 
	
	// Do not do filtering in  Reducer
	
	private String stockTicker = new String();
	
	protected void setup(Context context) throws IOException,InterruptedException {
		
		stockTicker = context.getConfiguration().get("filter.by.stockticker");
		// -D filter.by.stockticker=BAC  filter param in eclipse run configuration
		
	}
	public void map(LongWritable lineOffset,Text record,Context context) throws IOException,InterruptedException {
		
		
		parser.parse(record.toString());
		
      if(parser.getStockTicker().equals(stockTicker) ) 
		  {
		mapOutputKey.setFirst(new Text(parser.getTradeMonth()));
		mapOutputKey.setSecond(new Text(parser.getStockTicker()));
		
		mapOutputValue.setFirst(new LongWritable(parser.getVolume()));
		mapOutputValue.setSecond(new LongWritable(1));
		
		context.write(mapOutputKey, mapOutputValue);
		
		     }	
		}
		
	}


