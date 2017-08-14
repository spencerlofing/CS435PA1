import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;

public class NgramYear implements WritableComparable<NgramYear>{

	private String ngram;
	private Integer year;
	
	public NgramYear() { }
	
	public NgramYear(String unigram, Integer year)
	{
		this.ngram = unigram;
		this.year = year;
	}
	
	public String toString()
	{
		return(new StringBuilder())
				.append(ngram)
				.append('\t')
				.append(year)
				.toString();
	}
	
	public void readFields(DataInput in) throws IOException {
		ngram = WritableUtils.readString(in);
		year = in.readInt();	
	}
	
	public void write(DataOutput out) throws IOException {
		WritableUtils.writeString(out, ngram);
		out.writeInt(year);
	}
	
	public int compareTo(NgramYear uy) {
		int result = ngram.compareTo(uy.ngram);
		if(result == 0)
		{
			result = year.compareTo(uy.year);
		}
		return result;
	}
	
	public String getNgram() {
		return ngram;
	}
	
	public void setNgram(String ngram) {
		this.ngram = ngram;
	}
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
}
