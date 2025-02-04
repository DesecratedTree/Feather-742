package com.alex.util.lzma;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created at: Jul 30, 2016 8:43:41 AM
 * @author Walied-Yassen A.K.A Cody
 */
public class LZDecompressor {
    public static Class525 aClass525_7175 = new Class525();
    
	public static byte[] method5579(RsBuffer class541_sub35, int i, int i_3_) throws IOException {
		ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(class541_sub35.aByteArray10632);
		bytearrayinputstream.skip(class541_sub35.anInt10629 * 822738007);
		return method7936(bytearrayinputstream, i, 988211963);
	}
	public static byte[] method7936(InputStream inputstream, int i, int i_2_) throws IOException {
		byte[] is = new byte[5];
		if (inputstream.read(is, 0, 5) != 5) {
			throw new IOException("2");
		}
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(i);
		synchronized (aClass525_7175) {
			if (!aClass525_7175.method6353(is, 678769825)) {
				throw new IOException("3");
			}
			aClass525_7175.method6352(inputstream, bytearrayoutputstream, i);
		}
		bytearrayoutputstream.flush();
		return bytearrayoutputstream.toByteArray();
	}
}
