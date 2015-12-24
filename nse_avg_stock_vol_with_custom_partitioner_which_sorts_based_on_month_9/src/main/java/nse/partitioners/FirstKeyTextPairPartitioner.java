package nse.partitioners;

import org.apache.hadoop.mapreduce.Partitioner;

import nse.keyvalues.LongPair;
import nse.keyvalues.TextPair;

public class FirstKeyTextPairPartitioner extends Partitioner<TextPair,LongPair> {



	@Override
	public int getPartition(TextPair key, LongPair value, int numPartitions) {
		// numPartitions is the number of reducers set in the Driver class 
		
		int partitionValue =0;
		partitionValue = new Integer(key.getFirst().toString().replace("-", "")).intValue() 
				% numPartitions;
//	eclipse configuration 	-D filter.by.stockticker=BAC,APL  -Dpartition.by=trademonth /Users/pramod/nyse_data/nyse_201[2-3].csv /Users/pramod/nyse_data/PartitionByTradeMonth02
		
		return partitionValue;
	}

}
