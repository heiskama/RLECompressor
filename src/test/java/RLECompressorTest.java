import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RLECompressorTest {

	@Test
	@DisplayName("Byte array compress and decompress")
	public void testByteArray() {
		String str = "aaaaaaaaaa7777aaaaaaaaaaaaaaaabbaaaaaaaaab";
		byte[] orig = str.getBytes();
		byte[] compressed = RLECompressor.compress(orig);
		byte[] decompressed = RLECompressor.decompress(compressed);
		assertArrayEquals(orig, decompressed);
	}

	@Test
	@DisplayName("File compress and decompress")
	public void testFile() throws IOException {
		String str = "ADDAAAAADDDbbbbbbbb";
		byte[] orig = str.getBytes();
		File uncompressed = File.createTempFile("RLECompressor", "test");
		File compressed = File.createTempFile("RLECompressor", "test");
		File decompressed = File.createTempFile("RLECompressor", "test");
		Files.write(uncompressed.toPath(), orig);
		RLECompressor.compress(uncompressed, compressed);
		RLECompressor.decompress(compressed, decompressed);
		byte[] restored = Files.readAllBytes(decompressed.toPath());
		assertArrayEquals(orig, restored);
	}

	@Test
	@DisplayName("String compress and decompress")
	public void testString() {
		String orig = "TTTTTTaaaaaaaaaaCCCCaaaaaaabbaaaaBBaaaba";
		String compressed = RLECompressor.compress(orig);
		String decompressed = RLECompressor.decompress(compressed);
		assertEquals(orig, decompressed);
	}

	// TODO: Add more test cases

}