package com.alex.util.lzma;

/*
 * Class516 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.IOException;

public class Class516 {
	public short[] aShortArray6987;
	public int anInt6988;
	public Class318[] aClass318Array6989;
	public Class318 aClass318_6990;
	public Class318[] aClass318Array6991;
	public Class525 this$0;
	public static int anInt6992;

	public Class516(Class525 class525) {
		this$0 = class525;
		aShortArray6987 = new short[2];
		aClass318Array6989 = new Class318[16];
		aClass318Array6991 = new Class318[16];
		aClass318_6990 = new Class318(8);
		anInt6988 = 0;
	}

	public void method6274(int i, byte i_0_) {
		for (/**/; -187210011 * anInt6988 < i; anInt6988 += 794093293) {
			aClass318Array6989[-187210011 * anInt6988] = new Class318(3);
			aClass318Array6991[-187210011 * anInt6988] = new Class318(3);
		}
	}

	public void method6275(int i) {
		Class93.method1305(aShortArray6987, (byte) -115);
		for (int i_1_ = 0; i_1_ < anInt6988 * -187210011; i_1_++) {
			aClass318Array6989[i_1_].method4167((byte) 14);
			aClass318Array6991[i_1_].method4167((byte) 14);
		}
		aClass318_6990.method4167((byte) 14);
	}

	public int method6276(Class330 class330, int i, int i_2_) throws IOException {
		if (class330.method4284(aShortArray6987, 0, (byte) 21) == 0) {
			return aClass318Array6989[i].method4163(class330, (byte) -14);
		}
		int i_3_ = 8;
		if (class330.method4284(aShortArray6987, 1, (byte) 21) == 0) {
			i_3_ += aClass318Array6991[i].method4163(class330, (byte) -52);
		} else {
			i_3_ += 8 + aClass318_6990.method4163(class330, (byte) -39);
		}
		return i_3_;
	}

	public void method6277(int i) {
		for (/**/; -187210011 * anInt6988 < i; anInt6988 += 794093293) {
			aClass318Array6989[-187210011 * anInt6988] = new Class318(3);
			aClass318Array6991[-187210011 * anInt6988] = new Class318(3);
		}
	}

	public void method6278() {
		Class93.method1305(aShortArray6987, (byte) -27);
		for (int i = 0; i < anInt6988 * -187210011; i++) {
			aClass318Array6989[i].method4167((byte) 14);
			aClass318Array6991[i].method4167((byte) 14);
		}
		aClass318_6990.method4167((byte) 14);
	}


}
