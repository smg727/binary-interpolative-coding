package bic;

import java.io.IOException;
import java.util.ArrayList;




public class Bic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hi");

	}
	
	static void compressIntegers(ArrayList<Integer> values, BitOutputStream out,
			int low, int high, int minValue, int maxValue) throws java.lang.Exception {
		
		if(low>high)
			return;
		
		int mid = low + (high-low)/2;
		int range_high = maxValue - (high-mid);
		int range_low = minValue + (mid-low);
		int midVal = values.get(mid);
		compressAndWriteInteger(midVal, range_low, range_high, out);
		compressIntegers(values, out, low, mid-1, minValue,midVal-1);
		compressIntegers(values, out, mid+1, high, midVal+1, maxValue);
		return;
		
	}
	static int numberOfBits(int x) {
	    return (int)Math.ceil((Math.log(x) / Math.log(2)));
	}
	
	static void compressAndWriteInteger(int value, int range_low, int range_high, BitOutputStream out) throws java.lang.Exception {
		
		if(value<range_low || value>range_high) {
			throw new Exception("compress and write Integer: "+"compress "+value+" in range ["+range_low+" , "+range_high+"] "+" cannot be outside compress range");
		}
		int valueToCompress = value-range_low;
		int range = range_high-range_low+1;
		int bitCount = range==0?0:numberOfBits(range);
		out.write(bitCount, valueToCompress);
		System.out.println("compress "+value+" in range ["+range_low+" , "+range_high+"] with "+bitCount+" bits");
	}
	
	static void decompressInteger(int[] values, int low, int high, int minValue, int maxValue, BitInputStream in) throws IOException {
		
		if(low>high)
			return;
		
		int mid = low + (high-low)/2;
		int range_high = maxValue - (high-mid);
		int range_low = minValue + (mid-low);
		int range = range_high-range_low+1;
		int bitCount = range==0?0:numberOfBits(range);
		int offset = 0;
		if(bitCount!=0) {
			offset = in.read(bitCount);
		}
		
		int value = range_low + offset;
		values[mid] = value;
		decompressInteger(values, low, mid-1, minValue, value-1, in);
		decompressInteger(values, mid+1, high, value+1, maxValue, in);
		return;
		
	}
	
	static int[] decompressData(BitInputStream in) throws IOException{
		// read meta data
		int high = 7;
		int low = 0;
		int minValue = 3;
		int maxValue = 20;
		int[] output = new int[high+1];
		decompressInteger(output, low, high, minValue, maxValue, in);
		return output;
	}
	
	
	

}
