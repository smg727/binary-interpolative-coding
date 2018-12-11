package bic;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;





class Tests {

	@Test
	void test() {
//		String testFile = "testFile";
////		BitOutputStream outputStream = new BitOutputStream(testFile);
////		for(int i=0;i<8;i++) {
////			outputStream.write(3, i);
////		}
////		outputStream.close();
//		BitInputStream inputStream = new BitInputStream(testFile);
//		for(int i = 0;i<8;i++) {
//			int data = 0;
//			try {
//			data = inputStream.read(3);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				System.out.println("data "+data);
//				fail("exception "+data);
//			} finally {
////				inputStream.close();
//			}
//			System.out.println(data);
//		}
////		fail("Not yet implemented");
//	}	
//		BitOutputStream outputStream = null;
//		try {
//		String testFile = "testFile";
//		outputStream = new BitOutputStream(testFile);
//		Integer[] t = {3,8,9,11,12,13,17,20};
//		ArrayList<Integer> test = new ArrayList<>(Arrays.asList(t));
//		System.out.println(Arrays.toString(t));
//		Bic.compressIntegers(test, outputStream, 0, test.size()-1, test.get(0), test.get(test.size()-1));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally {
//			outputStream.close();
//		}
		
		BitInputStream inputStream = null;
		try {
			String testFile = "testFile";
			inputStream = new BitInputStream(testFile);
			int[] output = Bic.decompressData(inputStream);
			System.out.println(Arrays.toString(output));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			inputStream.close();
		}
	
	}

}
