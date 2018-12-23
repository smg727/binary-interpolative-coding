package bic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class Tests {
	
	// this test takes an array input compresses it and writes it out to the file 
	@Test
	void testCompressDecompressArray() {
				
		String testFile = "testFile";
		Integer[] input = {1,2,3,4,5};
		int output[];
		// compress and write file
		BitOutputStream outputStream = null;
		try {
		outputStream = new BitOutputStream(testFile);
		
		ArrayList<Integer> test = new ArrayList<>(Arrays.asList(input));
		Bic.compressData(test, outputStream);
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
			
			// compare two arrays
			for(int i=0;i<input.length;i++) {
				if(input[i]!=output[i])
					fail("input and output arrays not equal");
			}
			System.out.println("testCompressDecompressArray : PASS");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			inputStream.close();
			new File(testFile).delete();
		}
	}
	

}
