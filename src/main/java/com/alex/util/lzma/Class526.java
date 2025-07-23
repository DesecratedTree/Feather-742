package com.alex.util.lzma;
/*
 * Class526 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class526 {
	public int anInt7059;
	public Class525 this$0;
	public int anInt7060;
	public int anInt7061;
	public Class535[] aClass535Array7062;

	public void method6363(int i, int i_0_, int i_1_) {
		if (aClass535Array7062 == null || i_0_ != 597687113 * anInt7060 || i != 782905787 * anInt7059) {
			anInt7059 = -1260393613 * i;
			anInt7061 = 1806592443 * ((1 << i) - 1);
			anInt7060 = -140908807 * i_0_;
			int i_2_ = 1 << anInt7060 * 597687113 + anInt7059 * 782905787;
			aClass535Array7062 = new Class535[i_2_];
			for (int i_3_ = 0; i_3_ < i_2_; i_3_++) {
				aClass535Array7062[i_3_] = new Class535(this);
			}
		}
	}

	public void method6364() {
		int i = 1 << 782905787 * anInt7059 + 597687113 * anInt7060;
		for (int i_4_ = 0; i_4_ < i; i_4_++) {
			aClass535Array7062[i_4_].method6424(-894218588);
		}
	}

	public void method6365(byte i) {
		int i_5_ = 1 << 782905787 * anInt7059 + 597687113 * anInt7060;
		for (int i_6_ = 0; i_6_ < i_5_; i_6_++) {
			aClass535Array7062[i_6_].method6424(-1682388926);
		}
	}

	public void method6366() {
		int i = 1 << 782905787 * anInt7059 + 597687113 * anInt7060;
		for (int i_7_ = 0; i_7_ < i; i_7_++) {
			aClass535Array7062[i_7_].method6424(546873810);
		}
	}

	public void method6367(int i, int i_8_) {
		if (aClass535Array7062 == null || i_8_ != 597687113 * anInt7060 || i != 782905787 * anInt7059) {
			anInt7059 = -1260393613 * i;
			anInt7061 = 1806592443 * ((1 << i) - 1);
			anInt7060 = -140908807 * i_8_;
			int i_9_ = 1 << anInt7060 * 597687113 + anInt7059 * 782905787;
			aClass535Array7062 = new Class535[i_9_];
			for (int i_10_ = 0; i_10_ < i_9_; i_10_++) {
				aClass535Array7062[i_10_] = new Class535(this);
			}
		}
	}

	public void method6368(int i, int i_11_) {
		if (aClass535Array7062 == null || i_11_ != 597687113 * anInt7060 || i != 782905787 * anInt7059) {
			anInt7059 = -1260393613 * i;
			anInt7061 = 1806592443 * ((1 << i) - 1);
			anInt7060 = -140908807 * i_11_;
			int i_12_ = 1 << anInt7060 * 597687113 + anInt7059 * 782905787;
			aClass535Array7062 = new Class535[i_12_];
			for (int i_13_ = 0; i_13_ < i_12_; i_13_++) {
				aClass535Array7062[i_13_] = new Class535(this);
			}
		}
	}

	public Class535 method6369(int i, byte i_14_, int i_15_) {
		return aClass535Array7062[((i & 1967059827 * anInt7061) << anInt7060 * 597687113) + ((i_14_ & 0xff) >>> 8 - 597687113 * anInt7060)];
	}

	public void method6370() {
		int i = 1 << 782905787 * anInt7059 + 597687113 * anInt7060;
		for (int i_16_ = 0; i_16_ < i; i_16_++) {
			aClass535Array7062[i_16_].method6424(-1358849148);
		}
	}

	public Class526(Class525 class525) {
		this$0 = class525;
	}

	
}
