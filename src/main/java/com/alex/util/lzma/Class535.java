package com.alex.util.lzma;

/*
 * Class535 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.IOException;

public class Class535 {
	public Class526 this$1;
	public short[] aShortArray7101;

	public byte method6420(Class330 class330, byte i, byte i_0_) throws IOException {
		int i_1_ = 1;
		do {
			int i_2_ = i >> 7 & 0x1;
			i <<= 1;
			int i_3_ = class330.method4284(aShortArray7101, (1 + i_2_ << 8) + i_1_, (byte) 21);
			i_1_ = i_1_ << 1 | i_3_;
			if (i_3_ != i_2_) {
				for (/**/; i_1_ < 256; i_1_ = i_1_ << 1 | class330.method4284(aShortArray7101, i_1_, (byte) 21)) {
					/* empty */
				}
				break;
			}
		} while (i_1_ < 256);
		return (byte) i_1_;
	}

	public void method6421() {
		Class93.method1305(aShortArray7101, (byte) -68);
	}

	public byte method6422(Class330 class330, int i) throws IOException {
		int i_4_ = 1;
		do {
			i_4_ = i_4_ << 1 | class330.method4284(aShortArray7101, i_4_, (byte) 21);
		} while (i_4_ < 256);
		return (byte) i_4_;
	}

	public Class535(Class526 class526) {
		this$1 = class526;
		aShortArray7101 = new short[768];
	}

	public void method6423() {
		Class93.method1305(aShortArray7101, (byte) -3);
	}

	public void method6424(int i) {
		Class93.method1305(aShortArray7101, (byte) -71);
	}


}
