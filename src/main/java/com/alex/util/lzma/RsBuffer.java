package com.alex.util.lzma;

/*
 * Class541_Sub35 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.math.BigInteger;

public class RsBuffer {
	public static int anInt10626 = -306674912;
	public static int anInt10627 = 5000;
	public static int anInt10628 = 100;
	public int anInt10629;
	public static int[] anIntArray10630 = new int[256];
	public static long[] aLongArray10631;
	public byte[] aByteArray10632;
	public static long aLong10633 = -3932672073523589310L;

	public void method9602(int i, int i_0_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
	}


	public int method9604(byte i) {
		int i_1_ = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
		int i_2_ = 0;
		for (/**/; i_1_ < 0; i_1_ = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1]) {
			i_2_ = (i_2_ | i_1_ & 0x7f) << 7;
		}
		return i_2_ | i_1_;
	}

	public void method9605(int i, byte i_3_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9606(int i, int i_4_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public void method9607(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
	}

	public void method9608(long l) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 32);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) l;
	}

	public long method9610(int i) {
		long l = readInt() & 0xffffffffL;
		long l_5_ = readInt() & 0xffffffffL;
		return (l << 32) + l_5_;
	}

	public void method9611(long l, int i, int i_6_) {
		if (--i < 0 || i > 7) {
			throw new IllegalArgumentException();
		}
		for (int i_7_ = 8 * i; i_7_ >= 0; i_7_ -= 8) {
			aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> i_7_);
		}
	}

	

	public RsBuffer(int i, boolean bool) {
		aByteArray10632 = new byte[i];
	}

	public void method9616(int i, int i_13_) {
		if (i < 64 && i >= -64) {
			method9703(i + 64, 1504969911);
		} else if (i < 16384 && i >= -16384) {
			method9605(i + 49152, (byte) 21);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void method9617(int i, int i_14_) {
		if (i >= 0 && i < 128) {
			method9703(i, 1504969911);
		} else if (i >= 0 && i < 32768) {
			method9605(32768 + i, (byte) 120);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void method9618(int i, byte i_15_) {
		if (i < -1) {
			throw new IllegalArgumentException();
		}
		if (i == -1) {
			method9605(32767, (byte) 118);
		} else if (i < 32767) {
			method9605(i, (byte) 96);
		} else {
			method9697(i, -1961479020);
			aByteArray10632[822738007 * anInt10629 - 4] |= 0x80;
		}
	}

	public int method9619(byte i) {
		anInt10629 += -641114418;
		int i_16_ = ((aByteArray10632[822738007 * anInt10629 - 1] & 0xff) << 8) + (aByteArray10632[822738007 * anInt10629 - 2] & 0xff);
		if (i_16_ > 32767) {
			i_16_ -= 65536;
		}
		return i_16_;
	}

	public void method9620(int i, int i_17_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public int method9621(int i) {
		anInt10629 += -641114418;
		return (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8);
	}

	public int method9622(int i) {
		anInt10629 += -641114418;
		int i_18_ = (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8);
		if (i_18_ > 32767) {
			i_18_ -= 65536;
		}
		return i_18_;
	}

	public int method9623(int i) {
		anInt10629 += 1185812021;
		return ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 16) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8) + (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff);
	}

	public void method9624(byte[] is, int i, int i_19_, int i_20_) {
		for (int i_21_ = i; i_21_ < i_19_ + i; i_21_++) {
			aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = is[i_21_];
		}
	}

	public int readInt() {
		anInt10629 += -1282228836;
		return ((aByteArray10632[anInt10629 * 822738007 - 4] & 0xff) << 24) + ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 8) + (aByteArray10632[822738007 * anInt10629 - 1] & 0xff);
	}

	public int method9626(short i) {
		anInt10629 += -1282228836;
		return (aByteArray10632[822738007 * anInt10629 - 4] & 0xff) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 1] & 0xff) << 24) + ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 8);
	}

	public void method9627(int i) {
		if ((i & ~0x7f) != 0) {
			if (0 != (i & ~0x3fff)) {
				if ((i & ~0x1fffff) != 0) {
					if (0 != (i & ~0xfffffff)) {
						method9703(i >>> 28 | 0x80, 1504969911);
					}
					method9703(i >>> 21 | 0x80, 1504969911);
				}
				method9703(i >>> 14 | 0x80, 1504969911);
			}
			method9703(i >>> 7 | 0x80, 1504969911);
		}
		method9703(i & 0x7f, 1504969911);
	}

	public long method9628(int i) {
		long l = method9621(-1456477017) & 0xffffffffL;
		long l_22_ = readInt() & 0xffffffffL;
		return l_22_ + (l << 32);
	}

	public long method9629(int i, int i_23_) {
		if (--i < 0 || i > 7) {
			throw new IllegalArgumentException();
		}
		int i_24_ = 8 * i;
		long l = 0L;
		for (/**/; i_24_ >= 0; i_24_ -= 8) {
			l |= (aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xffL) << i_24_;
		}
		return l;
	}

	public float method9630(byte i) {
		return Float.intBitsToFloat(readInt());
	}





	public void read(byte[] is, int i, int i_32_) {
		for (int i_34_ = i; i_34_ < i + i_32_; i_34_++) {
			is[i_34_] = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
		}
	}

	public int method9636(int i) {
		int i_35_ = aByteArray10632[822738007 * anInt10629] & 0xff;
		if (i_35_ < 128) {
			return readUnsignedByte() - 64;
		}
		return method9621(315860342) - 49152;
	}

	public void method9637(long l) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 40);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 32);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) l;
	}

	public int method9638(byte i) {
		int i_36_ = 0;
		int i_37_;
		for (i_37_ = method9710(-147848425); 32767 == i_37_; i_37_ = method9710(1328327137)) {
			i_36_ += 32767;
		}
		i_36_ += i_37_;
		return i_36_;
	}

	public int method9639(int i) {
		if (aByteArray10632[822738007 * anInt10629] < 0) {
			return readInt() & 0x7fffffff;
		}
		return method9621(-1796035971);
	}

	public void method9640(int i, int i_38_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (128 - i);
	}

	public int method9641() {
		anInt10629 += -641114418;
		return (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8);
	}

	public void method9642(byte[] is, int i, int i_39_, byte i_40_) {
		for (int i_41_ = i + i_39_ - 1; i_41_ >= i; i_41_--) {
			is[i_41_] = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
		}
	}

	public void method9643(int[] is, int i) {
		int i_42_ = 822738007 * anInt10629 / 8;
		anInt10629 = 0;
		for (int i_43_ = 0; i_43_ < i_42_; i_43_++) {
			int i_44_ = readInt();
			int i_45_ = readInt();
			int i_46_ = 0;
			int i_47_ = -1640531527;
			int i_48_ = 32;
			while (i_48_-- > 0) {
				i_44_ += (i_45_ << 4 ^ i_45_ >>> 5) + i_45_ ^ is[i_46_ & 0x3] + i_46_;
				i_46_ += i_47_;
				i_45_ += (i_44_ << 4 ^ i_44_ >>> 5) + i_44_ ^ i_46_ + is[i_46_ >>> 11 & 0x3];
			}
			anInt10629 -= 1730509624;
			method9697(i_44_, -2029323954);
			method9697(i_45_, -2134779565);
		}
	}

	public int method9644() {
		if (aByteArray10632[anInt10629 * 822738007] < 0) {
			return readInt() & 0x7fffffff;
		}
		int i = method9621(963012021);
		if (32767 == i) {
			return -1;
		}
		return i;
	}

	public void method9645(int[] is, int i, int i_49_, int i_50_) {
		int i_51_ = 822738007 * anInt10629;
		anInt10629 = i * 1826926439;
		int i_52_ = (i_49_ - i) / 8;
		for (int i_53_ = 0; i_53_ < i_52_; i_53_++) {
			int i_54_ = readInt();
			int i_55_ = readInt();
			int i_56_ = 0;
			int i_57_ = -1640531527;
			int i_58_ = 32;
			while (i_58_-- > 0) {
				i_54_ += i_55_ + (i_55_ << 4 ^ i_55_ >>> 5) ^ i_56_ + is[i_56_ & 0x3];
				i_56_ += i_57_;
				i_55_ += (i_54_ << 4 ^ i_54_ >>> 5) + i_54_ ^ is[i_56_ >>> 11 & 0x3] + i_56_;
			}
			anInt10629 -= 1730509624;
			method9697(i_54_, -1887407174);
			method9697(i_55_, -1860411962);
		}
		anInt10629 = i_51_ * 1826926439;
	}

	public void method9646(int[] is, int i, int i_59_, int i_60_) {
		int i_61_ = anInt10629 * 822738007;
		anInt10629 = 1826926439 * i;
		int i_62_ = (i_59_ - i) / 8;
		for (int i_63_ = 0; i_63_ < i_62_; i_63_++) {
			int i_64_ = readInt();
			int i_65_ = readInt();
			int i_66_ = -957401312;
			int i_67_ = -1640531527;
			int i_68_ = 32;
			while (i_68_-- > 0) {
				i_65_ -= i_64_ + (i_64_ << 4 ^ i_64_ >>> 5) ^ is[i_66_ >>> 11 & 0x3] + i_66_;
				i_66_ -= i_67_;
				i_64_ -= i_65_ + (i_65_ << 4 ^ i_65_ >>> 5) ^ is[i_66_ & 0x3] + i_66_;
			}
			anInt10629 -= 1730509624;
			method9697(i_64_, -1857715103);
			method9697(i_65_, -1892043457);
		}
		anInt10629 = 1826926439 * i_61_;
	}

	public void method9647(BigInteger biginteger, BigInteger biginteger_69_, int i) {
		int i_70_ = 822738007 * anInt10629;
		anInt10629 = 0;
		byte[] is = new byte[i_70_];
		read(is, 0, i_70_);
		BigInteger biginteger_71_ = new BigInteger(is);
		BigInteger biginteger_72_ = biginteger_71_.modPow(biginteger, biginteger_69_);
		byte[] is_73_ = biginteger_72_.toByteArray();
		anInt10629 = 0;
		method9605(is_73_.length, (byte) 70);
		method9624(is_73_, 0, is_73_.length, -2127592248);
	}

	public int method9648(byte i) {
		anInt10629 += -641114418;
		int i_74_ = ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8) + (aByteArray10632[anInt10629 * 822738007 - 1] - 128 & 0xff);
		if (i_74_ > 32767) {
			i_74_ -= 65536;
		}
		return i_74_;
	}

	public void method9649(int i, int i_75_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public int readUnsignedByte() {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public void method9651(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public int method9652(byte i) {
		return 0 - aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public int method9653(byte i) {
		return 128 - aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public void method9654(int i) {
		if (i < 64 && i >= -64) {
			method9703(i + 64, 1504969911);
		} else if (i < 16384 && i >= -16384) {
			method9605(i + 49152, (byte) 126);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public byte method9655(int i) {
		return (byte) (128 - aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1]);
	}

	public void method9656(int i, byte i_76_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public void method9657(int i, int i_77_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public int method9658(int i) {
		anInt10629 += -641114418;
		return ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8) + (aByteArray10632[822738007 * anInt10629 - 1] - 128 & 0xff);
	}

	public int method9659(byte i) {
		int i_78_ = aByteArray10632[anInt10629 * 822738007] & 0xff;
		if (i_78_ < 128) {
			return readUnsignedByte() - 1;
		}
		return method9621(-221537777) - 32769;
	}

	public int method9660(int i) {
		anInt10629 += -641114418;
		int i_79_ = (aByteArray10632[822738007 * anInt10629 - 2] - 128 & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) << 8);
		if (i_79_ > 32767) {
			i_79_ -= 65536;
		}
		return i_79_;
	}

	public int method9661(byte i) {
		anInt10629 += 1185812021;
		return ((aByteArray10632[anInt10629 * 822738007 - 3] & 0xff) << 8) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 16) + (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff);
	}

	public int method9662(int i) {
		anInt10629 += 1185812021;
		int i_80_ = (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 3] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 8);
		if (i_80_ > 8388607) {
			i_80_ -= 16777216;
		}
		return i_80_;
	}

	public int method9663(int i) {
		anInt10629 += -1282228836;
		return (aByteArray10632[822738007 * anInt10629 - 4] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 1] & 0xff) << 24) + ((aByteArray10632[anInt10629 * 822738007 - 3] & 0xff) << 8);
	}

	public int method9664(byte i) {
		anInt10629 += -1282228836;
		return (aByteArray10632[anInt10629 * 822738007 - 3] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 4] & 0xff) << 8) + ((aByteArray10632[822738007 * anInt10629 - 1] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 24);
	}

	public int method9665(int i) {
		anInt10629 += -1282228836;
		return ((aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) << 8) + ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 24) + ((aByteArray10632[822738007 * anInt10629 - 4] & 0xff) << 16) + (aByteArray10632[anInt10629 * 822738007 - 2] & 0xff);
	}

	public int method9666(int i) {
		if (aByteArray10632[anInt10629 * 822738007] < 0) {
			return readInt() & 0x7fffffff;
		}
		int i_81_ = method9621(-604689173);
		if (32767 == i_81_) {
			return -1;
		}
		return i_81_;
	}

	static {
		for (int i = 0; i < 256; i++) {
			int i_82_ = i;
			for (int i_83_ = 0; i_83_ < 8; i_83_++) {
				if ((i_82_ & 0x1) == 1) {
					i_82_ = i_82_ >>> 1 ^ ~0x12477cdf;
				} else {
					i_82_ >>>= 1;
				}
			}
			anIntArray10630[i] = i_82_;
		}
		aLongArray10631 = new long[256];
		for (int i = 0; i < 256; i++) {
			long l = i;
			for (int i_84_ = 0; i_84_ < 8; i_84_++) {
				if (1L == (l & 0x1L)) {
					l = l >>> 1 ^ ~0x3693a86a2878f0bdL;
				} else {
					l >>>= 1;
				}
			}
			aLongArray10631[i] = l;
		}
	}

	public int method9667(int i) {
		int i_85_ = 0;
		int i_86_ = 0;
		int i_87_;
		do {
			i_87_ = readUnsignedByte();
			i_85_ |= (i_87_ & 0x7f) << i_86_;
			i_86_ += 7;
		} while (i_87_ > 127);
		return i_85_;
	}



	public int method9670() {
		anInt10629 += -641114418;
		int i = (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8);
		if (i > 32767) {
			i -= 65536;
		}
		return i;
	}


	public void method9672(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9673(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9674(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9675(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9676(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public void method9677(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public void method9678(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9679(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9680(int i, int i_88_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
	}

	public void method9681(int i) {
		if (i < 64 && i >= -64) {
			method9703(i + 64, 1504969911);
		} else if (i < 16384 && i >= -16384) {
			method9605(i + 49152, (byte) 36);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void method9682(int i, byte i_89_) {
		if ((i & ~0x7f) != 0) {
			if (0 != (i & ~0x3fff)) {
				if ((i & ~0x1fffff) != 0) {
					if (0 != (i & ~0xfffffff)) {
						method9703(i >>> 28 | 0x80, 1504969911);
					}
					method9703(i >>> 21 | 0x80, 1504969911);
				}
				method9703(i >>> 14 | 0x80, 1504969911);
			}
			method9703(i >>> 7 | 0x80, 1504969911);
		}
		method9703(i & 0x7f, 1504969911);
	}

	public byte method9683(int i) {
		return (byte) (aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] - 128);
	}

	public int method9684(byte i) {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] - 128 & 0xff;
	}

	public void method9685(int i) {
		if (i < 0 || i > 255) {
			throw new IllegalArgumentException();
		}
		aByteArray10632[anInt10629 * 822738007 - i - 1] = (byte) i;
	}

	public void method9686(int i) {
		if (i < 64 && i >= -64) {
			method9703(i + 64, 1504969911);
		} else if (i < 16384 && i >= -16384) {
			method9605(i + 49152, (byte) 26);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void method9687(int i, short i_90_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (0 - i);
	}

	public void method9688(int i) {
		if (i >= 0 && i < 128) {
			method9703(i, 1504969911);
		} else if (i >= 0 && i < 32768) {
			method9605(32768 + i, (byte) 83);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void method9689(int i) {
		if ((i & ~0x7f) != 0) {
			if (0 != (i & ~0x3fff)) {
				if ((i & ~0x1fffff) != 0) {
					if (0 != (i & ~0xfffffff)) {
						method9703(i >>> 28 | 0x80, 1504969911);
					}
					method9703(i >>> 21 | 0x80, 1504969911);
				}
				method9703(i >>> 14 | 0x80, 1504969911);
			}
			method9703(i >>> 7 | 0x80, 1504969911);
		}
		method9703(i & 0x7f, 1504969911);
	}

	public int method9690() {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public int method9691() {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public int method9692() {
		anInt10629 += -641114418;
		return (aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 2] & 0xff) << 8);
	}

	public void method9693(int i, int i_91_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
	}

	public void method9694(int i, int i_92_) {
		if (i < 0 || i > 255) {
			throw new IllegalArgumentException();
		}
		aByteArray10632[anInt10629 * 822738007 - i - 1] = (byte) i;
	}

	public int method9695() {
		anInt10629 += -1282228836;
		return ((aByteArray10632[anInt10629 * 822738007 - 4] & 0xff) << 24) + ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 8) + (aByteArray10632[822738007 * anInt10629 - 1] & 0xff);
	}

	public int method9696() {
		anInt10629 += -1282228836;
		return ((aByteArray10632[anInt10629 * 822738007 - 4] & 0xff) << 24) + ((aByteArray10632[822738007 * anInt10629 - 3] & 0xff) << 16) + ((aByteArray10632[822738007 * anInt10629 - 2] & 0xff) << 8) + (aByteArray10632[822738007 * anInt10629 - 1] & 0xff);
	}

	public void method9697(int i, int i_93_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9698(RsBuffer class541_sub35_94_, byte i) {
		method9624(class541_sub35_94_.aByteArray10632, 0, 822738007 * class541_sub35_94_.anInt10629, -2130306488);
	}


	public void method9701(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9702(int i, int i_98_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
	}

	public void method9703(int i, int i_99_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9704(int i, int i_100_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
	}

	public int method9705() {
		if (aByteArray10632[anInt10629 * 822738007] < 0) {
			return readInt() & 0x7fffffff;
		}
		int i = method9621(1725397772);
		if (32767 == i) {
			return -1;
		}
		return i;
	}

	public int method9706() {
		int i = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
		int i_101_ = 0;
		for (/**/; i < 0; i = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1]) {
			i_101_ = (i_101_ | i & 0x7f) << 7;
		}
		return i_101_ | i;
	}

	public int method9707() {
		int i = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
		int i_102_ = 0;
		for (/**/; i < 0; i = aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1]) {
			i_102_ = (i_102_ | i & 0x7f) << 7;
		}
		return i_102_ | i;
	}

	public int method9708() {
		int i = 0;
		int i_103_ = 0;
		int i_104_;
		do {
			i_104_ = readUnsignedByte();
			i |= (i_104_ & 0x7f) << i_103_;
			i_103_ += 7;
		} while (i_104_ > 127);
		return i;
	}

	public void method9709(int i) {
		if (i >= 0 && i < 128) {
			method9703(i, 1504969911);
		} else if (i >= 0 && i < 32768) {
			method9605(32768 + i, (byte) 118);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int method9710(int i) {
		int i_105_ = aByteArray10632[anInt10629 * 822738007] & 0xff;
		if (i_105_ < 128) {
			return readUnsignedByte();
		}
		return method9621(-1565364232) - 32768;
	}

	public void method9711(int i, int i_106_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public long method9712(byte i) {
		long l = readUnsignedByte() & 0xffffffffL;
		long l_107_ = readInt() & 0xffffffffL;
		return (l << 32) + l_107_;
	}

	public void method9713(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public void method9714(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (0 - i);
	}

	public void method9715(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (0 - i);
	}

	public void method9716(int i, byte i_108_) {
		if (i < 0 || i > 65535) {
			throw new IllegalArgumentException();
		}
		aByteArray10632[822738007 * anInt10629 - i - 2] = (byte) (i >> 8);
		aByteArray10632[822738007 * anInt10629 - i - 1] = (byte) i;
	}

	public void method9717(int[] is, int i) {
		int i_109_ = 822738007 * anInt10629 / 8;
		anInt10629 = 0;
		for (int i_110_ = 0; i_110_ < i_109_; i_110_++) {
			int i_111_ = readInt();
			int i_112_ = readInt();
			int i_113_ = -957401312;
			int i_114_ = -1640531527;
			int i_115_ = 32;
			while (i_115_-- > 0) {
				i_112_ -= i_111_ + (i_111_ << 4 ^ i_111_ >>> 5) ^ i_113_ + is[i_113_ >>> 11 & 0x3];
				i_113_ -= i_114_;
				i_111_ -= (i_112_ << 4 ^ i_112_ >>> 5) + i_112_ ^ i_113_ + is[i_113_ & 0x3];
			}
			anInt10629 -= 1730509624;
			method9697(i_111_, -2119143548);
			method9697(i_112_, -1938171701);
		}
	}

	public int method9718() {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] - 128 & 0xff;
	}

	public int method9719() {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] - 128 & 0xff;
	}

	public int method9720() {
		return 0 - aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] & 0xff;
	}

	public void method9721(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public void method9722(int i) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i + 128);
	}

	public RsBuffer(int i) {
		aByteArray10632 = new byte[i];
		anInt10629 = 0;
	}

	public int method9723(byte i) {
		anInt10629 += -641114418;
		return (aByteArray10632[822738007 * anInt10629 - 2] & 0xff) + ((aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) << 8);
	}

	public byte method9724(int i) {
		return (byte) (0 - aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1]);
	}

	public int method9725(int i) {
		anInt10629 += -641114418;
		return ((aByteArray10632[anInt10629 * 822738007 - 1] & 0xff) << 8) + (aByteArray10632[anInt10629 * 822738007 - 2] - 128 & 0xff);
	}

	public RsBuffer(byte[] is) {
		aByteArray10632 = is;
		anInt10629 = 0;
	}

	public void method9727(int i, byte i_117_) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (i >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) i;
	}

	public void method9728(long l) {
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 56);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 48);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 40);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 32);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 24);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 16);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) (l >> 8);
		aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1] = (byte) (int) l;
	}

	public byte method9729(byte i) {
		return aByteArray10632[(anInt10629 += 1826926439) * 822738007 - 1];
	}
}
