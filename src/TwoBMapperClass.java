import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

public class TwoBMapperClass extends Mapper<LongWritable, Text, NgramAuthor, Text>{
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
	{
		//first read the Text into a string
		String line = value.toString();
		//parse the line by the defined separator
		String[] lineParse = line.split("<===>");
		//get the author from the first position in this string array
		String author = lineParse[0];
		String[] authorArray = author.split(" ");
		String authorLastName = authorArray[authorArray.length - 1];
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
						NgramAuthor na = new NgramAuthor(bigram, authorLastName);
						context.write(na, new Text(" "));
					}
				}
				if(j == sentence.length - 1)
				{
					if(!sentence[j].equals(""))
					{
						String bigram = sentence[j] + " __";
						NgramAuthor na = new NgramAuthor(bigram, authorLastName);
						context.write(na, new Text(" "));
					}
				}
				else
				{
					if(!sentence[j].equals("") && !sentence[j+1].equals(""))
					{
						String bigram = sentence[j] + " " + sentence[j+1];
						NgramAuthor na = new NgramAuthor(bigram, authorLastName);
						context.write(na, new Text(" "));
					}
				}
			}
		}
	}
}
