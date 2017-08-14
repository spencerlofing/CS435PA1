import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class OneAMapperClass extends Mapper<LongWritable, Text, NgramYear, Text> {
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		//first read the Text into a string
		String line = value.toString();
		//parse the line by the defined separator
		String[] lineParse = line.split("<===>");
		//get the author from the first position in this string array
		String author = lineParse[0];
		//get the date from the second position in the string array
		String[] date = lineParse[1].split(" ");
		//parse the date into just a year
		int year = Integer.parseInt(date[date.length - 1]);
		//parse the actual text by spaces from the third position in the string array
		//will need to perform other parsing because of punctuation
		String writing = lineParse[2];
		//need to figure out how to treat double hyphens
		writing = writing.replaceAll("[^a-zA-Z0-9\\s]", "");
		writing = writing.toLowerCase();
		String[] wordArray = writing.split("\\s+");
		for(String word : wordArray)
		{
			if(!word.equals(""))
			{
				NgramYear ny = new NgramYear(word, year);
				context.write(ny, new Text(author));
			}
		}
	}
}
