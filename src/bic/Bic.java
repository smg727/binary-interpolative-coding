package bic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.validator.PublicClassValidator;


public class Bic {
	public static ArrayList<String> stack;
	public static ArrayList<String> stack2;
	public static long writeCount;
	public static long readCount;
	
	static void compressData(List<Integer> values, BitOutputStream out) throws Exception {
		stack = new ArrayList<>();
		writeCount = 0;
		// create meta-data
		int low = 0;
		int high = values.size()-1;
		int minValue = values.get(0);
		int maxValue = values.get(high);
		
		// write meta-data (write as 4-byte int, can be compressed)
		out.write(32, low);
		out.write(32, high);
		out.write(32, minValue);
		out.write(32, maxValue);
		
		compressIntegers(values, out, low, high, minValue, maxValue);
		
		System.out.println("wrote: "+writeCount+" bits");
	}
	
	// takes an array list of values and writes the compressed data to bit output stream
	static void compressIntegers(List<Integer> values, BitOutputStream out,
			int low, int high, int minValue, int maxValue) throws java.lang.Exception {
		
		
		if(low>high)
			return;
		
		
		int mid = low + (high-low)/2;
		int range_high = maxValue - (high-mid);
		int range_low = minValue + (mid-low);
		int midVal = values.get(mid);
//		stack.add("low: "+low+" high:"+high+" minval: "+minValue+" maxVal: "+maxValue+" val: "+midVal);
//		if(midVal==25173018) {
//			PrintWriter pwPrintWriter = new PrintWriter("log");
//			for(String s:stack) {
//				pwPrintWriter.write(s+"\n");
//			}
//			pwPrintWriter.close();
//			stack.clear();
//			int range = range_high-range_low+1;
//			int bitCount = range==1?0:numberOfBits(range);
//			System.out.println("low: "+low+" high:"+high+" minval: "+minValue+" maxVal: "+maxValue+" rangelow: "+range_low+" rangehigh: "+range_high);
//			System.out.println("value: "+midVal+" bitcount:"+bitCount);
//			
//		}
		compressAndWriteInteger(midVal, range_low, range_high, out);
		compressIntegers(values, out, low, mid-1, minValue,midVal-1);
		compressIntegers(values, out, mid+1, high, midVal+1, maxValue);
		return;
		
	}
	
	// number of bits required to compress x
	static int numberOfBits(int x) {
	    return (int)Math.ceil((Math.log(x) / Math.log(2)));

	}
	
	// compress integer in range and write
	static void compressAndWriteInteger(int value, int range_low, int range_high, BitOutputStream out) throws java.lang.Exception {
		
		if(value<range_low || value>range_high) {
			System.out.println("range error");
			throw new Exception("compress and write Integer: "+"compress "+value+" in range ["+range_low+" , "+range_high+"] "+" cannot be outside compress range");
		}
		int valueToCompress = value-range_low;
		int range = range_high-range_low+1;
		int bitCount = range==1?0:numberOfBits(range);
		if(bitCount==0)
			return;
		out.write(bitCount, valueToCompress);
		writeCount+=bitCount;
//		System.out.println("compress "+value+" in range ["+range_low+" , "+range_high+"] with "+bitCount+" bits");
	}
	
	// fills data into []values. Decompressing logic sits here
	static void decompressInteger(int[] values, int low, int high, int minValue, int maxValue, BitInputStream in) throws IOException {
		
		if(low>high)
			return;
		
		int mid = low + (high-low)/2;
		int range_high = maxValue - (high-mid);
		int range_low = minValue + (mid-low);
		int range = range_high-range_low+1;
		int bitCount = range==1?0:numberOfBits(range);
		int offset = 0;
		if(bitCount!=0) {
			offset = in.read(bitCount);
			readCount+=bitCount;
		}
		if(offset == -1) {
			System.out.println("read: "+readCount+" bits");
		}
		
		int value = range_low + offset;
//		stack2.add("low: "+low+" high:"+high+" minval: "+minValue+" maxVal: "+maxValue+" val: "+value);
//		if(value==25173013) {
//			PrintWriter pwPrintWriter = new PrintWriter("log2");
//			for(String s:stack2) {
//				pwPrintWriter.write(s+"\n");
//			}
//			pwPrintWriter.close();
//			stack2.clear();
//			System.out.println("low: "+low+" high:"+high+" minval: "+minValue+" maxVal: "+maxValue+" rangelow: "+range_low+" rangehigh: "+range_high);
//			System.out.println("value: "+value+" bitcount:"+bitCount+" offset: "+offset+" range: "+range);
//		}
		values[mid] = value;
		decompressInteger(values, low, mid-1, minValue, value-1, in);
		decompressInteger(values, mid+1, high, value+1, maxValue, in);
		return;
		
	}
	
	// input: Bit-stream to data
	// output: Array of decompresed integers
	static int[] decompressData(BitInputStream in) throws IOException{
		stack2 = new ArrayList<>();
		readCount = 0;
		//read meta-data
		int low = in.read(32);
		int high = in.read(32);
		int minValue = in.read(32);
		int maxValue = in.read(32);
		
		// declare array
		int[] output = new int[high+1];
		decompressInteger(output, low, high, minValue, maxValue, in);
		return output;
	}
	
	
	

}
