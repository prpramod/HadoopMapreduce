package nse.partitioners;

import org.apache.hadoop.mapreduce.Partitioner;

import nse.keyvalues.LongPair;
import nse.keyvalues.TextPair;

public class SecondKeyTextPairPartitioner extends Partitioner<TextPair,LongPair> {



	@Override
	public int getPartition(TextPair key, LongPair value, int numPartitions) {
		// numPartitions is the number of reducers set in the Driver class 
		
		int partitionVlaue =0;
		partitionVlaue = (key.getSecond().hashCode() & Integer.MAX_VALUE) % numPartitions;
		
		return partitionVlaue;
	}

}
