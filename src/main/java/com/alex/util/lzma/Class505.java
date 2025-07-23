package com.alex.util.lzma;

/*
 * Class505 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.IOException;
import java.io.OutputStream;

public class Class505 {
	public int anInt5556;
	public OutputStream anOutputStream5557;
	public int anInt5558 = 0;
	public int anInt5559;
	public byte[] aByteArray5560;

	public void method6014(int i, int i_0_) {
		if (aByteArray5560 == null || -1902588717 * anInt5558 != i) {
			aByteArray5560 = new byte[i];
		}
		anInt5558 = 2109827931 * i;
		anInt5556 = 0;
		anInt5559 = 0;
	}

	public void method6015(byte i) throws IOException {
		int i_1_ = anInt5556 * 382052739 - anInt5559 * 1809863529;
		if (i_1_ != 0) {
			anOutputStream5557.write(aByteArray5560, anInt5559 * 1809863529, i_1_);
			if (382052739 * anInt5556 >= anInt5558 * -1902588717) {
				anInt5556 = 0;
			}
			anInt5559 = -447548405 * anInt5556;
		}
	}

	public void method6016(int i, int i_2_) throws IOException {
		int i_3_ = anInt5556 * 382052739 - i - 1;
		if (i_3_ < 0) {
			i_3_ += -1902588717 * anInt5558;
		}
		for (/**/; i_2_ != 0; i_2_--) {
			if (i_3_ >= -1902588717 * anInt5558) {
				i_3_ = 0;
			}
			aByteArray5560[(anInt5556 += 1351986475) * 382052739 - 1] = aByteArray5560[i_3_++];
			if (anInt5556 * 382052739 >= anInt5558 * -1902588717) {
				method6015((byte) -41);
			}
		}
	}

	public void method6017(boolean bool, short i) {
		if (!bool) {
			anInt5559 = 0;
			anInt5556 = 0;
		}
	}

	public void method6018(byte i) throws IOException {
		method6015((byte) 80);
		anOutputStream5557 = null;
	}

	public void method6019(int i, int i_4_, int i_5_) throws IOException {
		int i_6_ = anInt5556 * 382052739 - i - 1;
		if (i_6_ < 0) {
			i_6_ += -1902588717 * anInt5558;
		}
		for (/**/; i_4_ != 0; i_4_--) {
			if (i_6_ >= -1902588717 * anInt5558) {
				i_6_ = 0;
			}
			aByteArray5560[(anInt5556 += 1351986475) * 382052739 - 1] = aByteArray5560[i_6_++];
			if (anInt5556 * 382052739 >= anInt5558 * -1902588717) {
				method6015((byte) 29);
			}
		}
	}

	public void method6020(byte i, int i_7_) throws IOException {
		aByteArray5560[(anInt5556 += 1351986475) * 382052739 - 1] = i;
		if (anInt5556 * 382052739 >= anInt5558 * -1902588717) {
			method6015((byte) 116);
		}
	}

	public void method6021(int i) {
		if (aByteArray5560 == null || -1902588717 * anInt5558 != i) {
			aByteArray5560 = new byte[i];
		}
		anInt5558 = 2109827931 * i;
		anInt5556 = 0;
		anInt5559 = 0;
	}

	public void method6022() throws IOException {
		method6015((byte) -17);
		anOutputStream5557 = null;
	}

	public void method6023() throws IOException {
		method6015((byte) -36);
		anOutputStream5557 = null;
	}

	public void method6024() throws IOException {
		method6015((byte) 29);
		anOutputStream5557 = null;
	}

	public void method6025() throws IOException {
		int i = anInt5556 * 382052739 - anInt5559 * 1809863529;
		if (i != 0) {
			anOutputStream5557.write(aByteArray5560, anInt5559 * 1809863529, i);
			if (382052739 * anInt5556 >= anInt5558 * -1902588717) {
				anInt5556 = 0;
			}
			anInt5559 = -447548405 * anInt5556;
		}
	}

	public void method6026() throws IOException {
		int i = anInt5556 * 382052739 - anInt5559 * 1809863529;
		if (i != 0) {
			anOutputStream5557.write(aByteArray5560, anInt5559 * 1809863529, i);
			if (382052739 * anInt5556 >= anInt5558 * -1902588717) {
				anInt5556 = 0;
			}
			anInt5559 = -447548405 * anInt5556;
		}
	}

	public void method6027(OutputStream outputstream, int i) throws IOException {
		method6018((byte) -43);
		anOutputStream5557 = outputstream;
	}

	public byte method6028(int i, int i_8_) {
		int i_9_ = anInt5556 * 382052739 - i - 1;
		if (i_9_ < 0) {
			i_9_ += -1902588717 * anInt5558;
		}
		return aByteArray5560[i_9_];
	}

}
