import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class NgramAuthor implements WritableComparable<NgramAuthor>{

	private String ngram;
	private String author;
	
	public NgramAuthor() { }
	
	public NgramAuthor(String unigram, String author)
	{
		this.ngram = unigram;
		this.author = author;
	}
	
	public String toString()
	{
		return(new StringBuilder())
				.append(ngram)
				.append('\t')
				.append(author)
				.toString();
	}
	
	public void readFields(DataInput in) throws IOException {
		ngram = WritableUtils.readString(in);
		author = WritableUtils.readString(in);	
	}
	
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, ngram);
		WritableUtils.writeString(out, author);
	}
	
	public int compareTo(NgramAuthor na) {
		int result = ngram.compareTo(na.ngram);
		if(result == 0)
		{
			result = author.compareTo(na.author);
		}
		return result;
	}
	
	public String getNgram() {
		return ngram;
	}
	
	public void setNgram(String ngram) {
		this.ngram = ngram;
	}
	public String getauthor() {
		return author;
	}
	
	public void setauthor(String author) {
		this.author = author;
	}
}
