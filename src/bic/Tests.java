package bic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void test() {
		String testFile = "testFile";
		Integer[] input = {3,8,9,11,12,13,17,20};
		int output[];
		// compress and write file
		BitOutputStream outputStream = null;
		try {
		outputStream = new BitOutputStream(testFile);
		
		ArrayList<Integer> test = new ArrayList<>(Arrays.asList(input));
		Bic.compressIntegers(test, outputStream, 0, test.size()-1, test.get(0), test.get(test.size()-1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			outputStream.close();
		}
		
		BitInputStream inputStream = null;
		try {
			inputStream = new BitInputStream(testFile);
			output = Bic.decompressData(inputStream);
			System.out.println("input "+Arrays.toString(input));
			System.out.println("output "+Arrays.toString(output));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			inputStream.close();
			new File(testFile).delete();
		}
		
		
	
	}

}
