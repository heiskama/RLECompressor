import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Compress/decompress files, strings and byte arrays with run-length encoding.
 * 
 * @author Matti Heiskanen
 * @version 1.0, 01/31/18
 * @see https://en.wikipedia.org/wiki/Run-length_encoding
 */
public class RLECompressor {

	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Usage: RLECompressor [-c|--compress|-d|--decompress] [input file] [output file]");
			return;
		}
		else {
			try {
				if (args[0].equalsIgnoreCase("-c") || args[0].equalsIgnoreCase("--compress")) {
					compress(new File(args[1]), new File(args[2]));
				}
				else if (args[0].equalsIgnoreCase("-d") || args[0].equalsIgnoreCase("--decompress")) {
					decompress(new File(args[1]), new File(args[2]));
				}
			} catch (IOException ioe) {
				System.err.println("ERROR: Unable to access " + ioe.getLocalizedMessage());
			}
		}
		
	}

	/**
	 * Compress a byte array with run-length encoding.
	 */
	public static byte[] compress(byte[] array) {
		if (array == null || array.length == 0)
			return null;

		List<Byte> list = new ArrayList<>();
		int i = 0;
		
		while (i < array.length) {
			Integer count = 1;
			
			while (i+count < array.length && array[i] == array[i+count] && count < Byte.MAX_VALUE) {
				count++;
			}
			
			list.add(count.byteValue());
			list.add(array[i]);
			i = i + count;
		}
		
		return toByteArray(list);
	}
	
	/**
	 * Decompress a run-length encoded byte array.
	 */
	public static byte[] decompress(byte[] array) {
		if (array == null || array.length == 0)
			return null;
		
		List<Byte> list = new ArrayList<>();
		
		for (int i = 0; i+1 < array.length; i = i + 2) {
			int count = array[i];
			byte b = array[i+1];
			
			while (count > 0) {
				list.add(b);
				count--;
			}
		}
		
		return toByteArray(list);
	}

	/**
	 * Compress a string with run-length encoding.
	 */
	public static String compress(String str) {
		return new String(compress(str.getBytes()));
	}
	
	/**
	 * Decompress a string compressed with run-length encoding.
	 */
	public static String decompress(String str) {
		return new String(decompress(str.getBytes()));
	}
	
	/**
	 * Compress a file with run-length encoding.
	 */
	public static void compress(File input, File output) throws IOException {
		byte[] array = Files.readAllBytes(input.toPath());
		byte[] compressed = compress(array);
		Files.write(output.toPath(), compressed);
	}
	
	/**
	 * Decompress a file compressed with run-length encoding.
	 */
	public static void decompress(File input, File output) throws IOException {
		byte[] array = Files.readAllBytes(input.toPath());
		byte[] decompressed = decompress(array);
		Files.write(output.toPath(), decompressed);
	}
	
	/**
	 * Convert a list into a byte array.
	 */
	private static byte[] toByteArray(List<Byte> list) {
		byte[] array = new byte[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}

}
