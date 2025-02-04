package com.alex.util.crc32;

import java.util.zip.CRC32;

public final class CRC32HGenerator {
	
	public static final CRC32 CRC32Instance = new CRC32();
	
	public static int getCrc(byte[] data) {
		return getCrc(data, 0, data.length);
	}
	
	public static int getCrc(byte[] data, int offset, int length) {
		synchronized(CRC32Instance) {
			CRC32Instance.update(data, offset, length);
			int hash = (int) CRC32Instance.getValue();
			CRC32Instance.reset();
			return hash;
		}
	}
	
	
	private CRC32HGenerator() {
		
	}
}
