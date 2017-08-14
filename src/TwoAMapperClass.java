import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class TwoAMapperClass extends Mapper<LongWritable, Text, NgramYear, Text>{
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
		writing = writing.replaceAll("[^a-zA-Z0-9\\s\\.]", "");
		writing = writing.toLowerCase();
		String[] sentenceArray = writing.split("\\.");
		for(int i = 0; i < sentenceArray.length; i++)
		{
			String[] sentence = sentenceArray[i].split("\\s+");
			for(int j = 0; j < sentence.length; j++)
			{
				if(j == 0)
				{
					if(!sentence[0].equals(""))
					{
						String bigram = "__ " + sentence[0];
						NgramYear ny = new NgramYear(bigram, year);
						context.write(ny, new Text(author));
					}
				}
				if(j == sentence.length - 1)
				{
					if(!sentence[j].equals(""))
					{
						String bigram = sentence[j] + " __";
						NgramYear ny = new NgramYear(bigram, year);
						context.write(ny, new Text(author));
					}
				}
				else
				{
					if(!sentence[j].equals("") && !sentence[j].equals(""))
					{
						String bigram = sentence[j] + " " + sentence[j+1];
						NgramYear ny = new NgramYear(bigram, year);
						context.write(ny, new Text(author));
					}
				}
			}
		}
	}
}
