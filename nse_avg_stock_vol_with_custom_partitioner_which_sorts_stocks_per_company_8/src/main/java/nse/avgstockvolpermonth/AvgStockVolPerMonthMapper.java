package nse.avgstockvolpermonth;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
	// setup() will be invoked 1st before map() and per JVM only  once but map() will be invoked the no of times 
	// as many records 
	// cleanup() will be invoked only  once per JVM just before termination of JVM 
	
	// Do not do filtering in  Reducer
	
	private Set<String> stockTickers = new HashSet<String>();
	
	protected void setup(Context context) throws IOException,InterruptedException {
		
		String stockTicker = context.getConfiguration().get("filter.by.stockticker");
		//-D filter.by.stockticker=BAC,APL filter param in eclipse run configuration
		if(stockTicker!=null)
		{
			
			String tickers []  = stockTicker.split(",");
			for(String ticker:tickers){
				
				stockTickers.add(ticker);
				
			}
			
		}
		
		
		
	}
	public void map(LongWritable lineOffset,Text record,Context context) throws IOException,InterruptedException {
		
		
		parser.parse(record.toString());
		
      if(stockTickers.isEmpty() || stockTickers.contains(parser.getStockTicker()) ) 
    // if filter i.e stockTickers is empty then apple map function on all incoming stocktickers 
    // if stockTickers contains filter values then apply map function on those
    // filters only 
    	  
   //command on hadoop clusture 
    	  
   // hadoop jar mapReduceWithFilter.jar  nse.avgstockvolpermonth.AvgStockVolumePerMonthDriver -Dfilter.by.stockticker=BAC,APL /user/root/nyse_data/nyse_20*.csv /user/root/nyse_data/MapReduceFiltered
    	  
    	  
    	  
		  {
		mapOutputKey.setFirst(new Text(parser.getTradeMonth()));
		mapOutputKey.setSecond(new Text(parser.getStockTicker()));
		
		mapOutputValue.setFirst(new LongWritable(parser.getVolume()));
		mapOutputValue.setSecond(new LongWritable(1));
		
		context.write(mapOutputKey, mapOutputValue);
		
		     }	
		}
		
	}


