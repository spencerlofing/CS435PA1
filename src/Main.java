import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		// TODO Auto-generated method stub
		if (args.length != 3) {
			 System.out.printf("Usage: <jar file> <output type> <input dir> <output dir>\n");
			 System.exit(-1);
			}
			Configuration conf = new Configuration();
			Job job=Job.getInstance(conf);
			
			//sets up the main, mapper, and reducer classes for the job
			job.setJarByClass(Main.class);
			if(args[0].equals("1A"))
			{
				job.setMapperClass(OneAMapperClass.class);
				job.setMapOutputKeyClass(NgramYear.class);
				job.setReducerClass(AReducerClass.class);
			}
			else if(args[0].equals("2A"))
			{
				job.setMapperClass(TwoAMapperClass.class);
				job.setMapOutputKeyClass(NgramYear.class);
				job.setReducerClass(AReducerClass.class);
			}
			else if(args[0].equals("1B"))
			{
				job.setMapperClass(OneBMapperClass.class);
				job.setMapOutputKeyClass(NgramAuthor.class);
				job.setReducerClass(BReducerClass.class);
			}
			else if(args[0].equals("2B"))
			{
				job.setMapperClass(TwoBMapperClass.class);
				job.setMapOutputKeyClass(NgramAuthor.class);
				job.setReducerClass(BReducerClass.class);
			}
			
			//sets up the mapper output by key, value
			job.setMapOutputValueClass(Text.class);
			
			//sets up the reducer output by key, value
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			
			//sets up input for mapper and reducer as a text key, value pair
			job.setInputFormatClass(TextInputFormat.class);
			job.setOutputFormatClass(TextOutputFormat.class);
			
			FileInputFormat.setInputPaths(job, new Path(args[1]));
			FileOutputFormat.setOutputPath(job, new Path(args[2]));
			System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
