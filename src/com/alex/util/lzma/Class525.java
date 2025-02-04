package com.alex.util.lzma;

/*
 * Class525 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Class525 {
	public Class318[] aClass318Array7041;
	public Class330 aClass330_7042;
	public short[] aShortArray7043;
	public Class505 aClass505_7044 = new Class505();
	public short[] aShortArray7045;
	public short[] aShortArray7046;
	public Class526 aClass526_7047;
	public int anInt7048;
	public short[] aShortArray7049;
	public short[] aShortArray7050;
	public Class318 aClass318_7051;
	public short[] aShortArray7052;
	public Class516 aClass516_7053;
	public Class516 aClass516_7054;
	public short[] aShortArray7055;
	public int anInt7056;
	public int anInt7057;

	public boolean method6350(int i, int i_0_) {
		if (i < 0) {
			return false;
		}
		if (anInt7048 * 1556483561 != i) {
			anInt7048 = i * -2060477863;
			anInt7056 = Math.max(anInt7048 * 1556483561, 1) * -283198905;
			aClass505_7044.method6014(Math.max(1932054391 * anInt7056, 4096), 543511149);
		}
		return true;
	}

	public Class525() {
		aClass330_7042 = new Class330();
		aShortArray7043 = new short[192];
		aShortArray7055 = new short[12];
		aShortArray7045 = new short[12];
		aShortArray7046 = new short[12];
		aShortArray7052 = new short[12];
		aShortArray7049 = new short[192];
		aClass318Array7041 = new Class318[4];
		aShortArray7050 = new short[114];
		aClass318_7051 = new Class318(4);
		aClass516_7054 = new Class516(this);
		aClass516_7053 = new Class516(this);
		aClass526_7047 = new Class526(this);
		anInt7048 = 2060477863;
		anInt7056 = 283198905;
		for (int i = 0; i < 4; i++) {
			aClass318Array7041[i] = new Class318(6);
		}
	}

	public void method6351(int i) throws IOException {
		aClass505_7044.method6017(false, (short) -15290);
		Class93.method1305(aShortArray7043, (byte) -58);
		Class93.method1305(aShortArray7049, (byte) -42);
		Class93.method1305(aShortArray7055, (byte) -11);
		Class93.method1305(aShortArray7045, (byte) -111);
		Class93.method1305(aShortArray7046, (byte) -98);
		Class93.method1305(aShortArray7052, (byte) -116);
		Class93.method1305(aShortArray7050, (byte) -119);
		aClass526_7047.method6365((byte) -7);
		for (int i_1_ = 0; i_1_ < 4; i_1_++) {
			aClass318Array7041[i_1_].method4167((byte) 14);
		}
		aClass516_7054.method6275(507630956);
		aClass516_7053.method6275(-1349265785);
		aClass318_7051.method4167((byte) 14);
		aClass330_7042.method4282(-1438996523);
	}

	public boolean method6352(InputStream inputstream, OutputStream outputstream, long l) throws IOException {
		aClass330_7042.method4283(inputstream, 252293369);
		aClass505_7044.method6027(outputstream, 1085721754);
		method6351(434604653);
		int i = Class384.method4785((byte) 0);
		int i_2_ = 0;
		int i_3_ = 0;
		int i_4_ = 0;
		int i_5_ = 0;
		long l_6_ = 0L;
		byte i_7_ = 0;
		while (l < 0L || l_6_ < l) {
			int i_8_ = (int) l_6_ & 1011654445 * anInt7057;
			if (aClass330_7042.method4284(aShortArray7043, (i << 4) + i_8_, (byte) 21) == 0) {
				Class535 class535 = aClass526_7047.method6369((int) l_6_, i_7_, 255964755);
				if (!Class610.method7280(i, -1755554473)) {
					i_7_ = class535.method6420(aClass330_7042, aClass505_7044.method6028(i_2_, -1891878585), (byte) 10);
				} else {
					i_7_ = class535.method6422(aClass330_7042, 393112095);
				}
				aClass505_7044.method6020(i_7_, -247363757);
				i = Class383.method4775(i, (byte) 1);
				l_6_++;
			} else {
				int i_9_;
				if (aClass330_7042.method4284(aShortArray7055, i, (byte) 21) == 1) {
					i_9_ = 0;
					if (aClass330_7042.method4284(aShortArray7045, i, (byte) 21) == 0) {
						if (aClass330_7042.method4284(aShortArray7049, (i << 4) + i_8_, (byte) 21) == 0) {
							i = Class290.method3853(i, (byte) 8);
							i_9_ = 1;
						}
					} else {
						int i_10_;
						if (aClass330_7042.method4284(aShortArray7046, i, (byte) 21) == 0) {
							i_10_ = i_3_;
						} else {
							if (aClass330_7042.method4284(aShortArray7052, i, (byte) 21) == 0) {
								i_10_ = i_4_;
							} else {
								i_10_ = i_5_;
								i_5_ = i_4_;
							}
							i_4_ = i_3_;
						}
						i_3_ = i_2_;
						i_2_ = i_10_;
					}
					if (0 == i_9_) {
						i_9_ = aClass516_7053.method6276(aClass330_7042, i_8_, 342674303) + 2;
						i = Class261.method3603(i, (byte) 1);
					}
				} else {
					i_5_ = i_4_;
					i_4_ = i_3_;
					i_3_ = i_2_;
					i_9_ = 2 + aClass516_7054.method6276(aClass330_7042, i_8_, 342674303);
					i = Class695_Sub26.method10113(i, (byte) 38);
					int i_11_ = aClass318Array7041[Class189_Sub3.method8994(i_9_, 923786194)].method4163(aClass330_7042, (byte) -94);
					if (i_11_ >= 4) {
						int i_12_ = (i_11_ >> 1) - 1;
						i_2_ = (0x2 | i_11_ & 0x1) << i_12_;
						if (i_11_ < 14) {
							i_2_ += Class541_Sub32.method9556(aShortArray7050, i_2_ - i_11_ - 1, aClass330_7042, i_12_, (byte) -35);
						} else {
							i_2_ += aClass330_7042.method4279(i_12_ - 4, -21682814) << 4;
							i_2_ += aClass318_7051.method4164(aClass330_7042, 183527120);
							if (i_2_ < 0) {
								if (-1 != i_2_) {
									return false;
								}
								break;
							}
						}
					} else {
						i_2_ = i_11_;
					}
				}
				if (i_2_ >= l_6_ || i_2_ >= 1932054391 * anInt7056) {
					return false;
				}
				aClass505_7044.method6019(i_2_, i_9_, 599768051);
				l_6_ += i_9_;
				i_7_ = aClass505_7044.method6028(0, -426725689);
			}
		}
		aClass505_7044.method6015((byte) 43);
		aClass505_7044.method6018((byte) -21);
		aClass330_7042.method4281(1241448717);
		return true;
	}

	public boolean method6353(byte[] is, int i) {
		if (is.length < 5) {
			return false;
		}
		int i_13_ = is[0] & 0xff;
		int i_14_ = i_13_ % 9;
		int i_15_ = i_13_ / 9;
		int i_16_ = i_15_ % 5;
		int i_17_ = i_15_ / 5;
		int i_18_ = 0;
		for (int i_19_ = 0; i_19_ < 4; i_19_++) {
			i_18_ += (is[i_19_ + 1] & 0xff) << 8 * i_19_;
		}
		if (!method6356(i_14_, i_16_, i_17_, 1760730414)) {
			return false;
		}
		return method6350(i_18_, 712365188);
	}

	public boolean method6354(int i) {
		if (i < 0) {
			return false;
		}
		if (anInt7048 * 1556483561 != i) {
			anInt7048 = i * -2060477863;
			anInt7056 = Math.max(anInt7048 * 1556483561, 1) * -283198905;
			aClass505_7044.method6014(Math.max(1932054391 * anInt7056, 4096), 711799102);
		}
		return true;
	}

	public void method6355() throws IOException {
		aClass505_7044.method6017(false, (short) -29812);
		Class93.method1305(aShortArray7043, (byte) -120);
		Class93.method1305(aShortArray7049, (byte) -114);
		Class93.method1305(aShortArray7055, (byte) -105);
		Class93.method1305(aShortArray7045, (byte) -90);
		Class93.method1305(aShortArray7046, (byte) -53);
		Class93.method1305(aShortArray7052, (byte) -96);
		Class93.method1305(aShortArray7050, (byte) -77);
		aClass526_7047.method6365((byte) -65);
		for (int i = 0; i < 4; i++) {
			aClass318Array7041[i].method4167((byte) 14);
		}
		aClass516_7054.method6275(226812984);
		aClass516_7053.method6275(-2040414209);
		aClass318_7051.method4167((byte) 14);
		aClass330_7042.method4282(-1543320300);
	}

	public boolean method6356(int i, int i_20_, int i_21_, int i_22_) {
		if (i > 8 || i_20_ > 4 || i_21_ > 4) {
			return false;
		}
		aClass526_7047.method6363(i_20_, i, -2123361387);
		int i_23_ = 1 << i_21_;
		aClass516_7054.method6274(i_23_, (byte) -94);
		aClass516_7053.method6274(i_23_, (byte) -37);
		anInt7057 = (i_23_ - 1) * 1521748133;
		return true;
	}

	public boolean method6357(int i, int i_24_, int i_25_) {
		if (i > 8 || i_24_ > 4 || i_25_ > 4) {
			return false;
		}
		aClass526_7047.method6363(i_24_, i, -2123361387);
		int i_26_ = 1 << i_25_;
		aClass516_7054.method6274(i_26_, (byte) 67);
		aClass516_7053.method6274(i_26_, (byte) -4);
		anInt7057 = (i_26_ - 1) * 1521748133;
		return true;
	}

}
