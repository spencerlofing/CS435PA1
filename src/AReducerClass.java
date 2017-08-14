import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AReducerClass extends Reducer<NgramYear, Text, Text, IntWritable>{
	
	public void reduce(NgramYear key, Iterable<Text> values, Context context) throws
	IOException, InterruptedException {
		int count = 0;
		for(Text val: values) 
		{
			count++;
		}
		context.write(new Text(key.toString()), new IntWritable(count));
	}
}
