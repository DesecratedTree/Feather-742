package com.alex.util.lzma;

/*
 * Class318 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.IOException;

public class Class318 {
	public short[] aShortArray3365;
	public int anInt3366;
	public static int anInt3368;

	public void method4162() {
		Class93.method1305(aShortArray3365, (byte) -123);
	}

	public int method4163(Class330 class330, byte i) throws IOException {
		int i_0_ = 1;
		for (int i_1_ = anInt3366 * -1777985159; i_1_ != 0; i_1_--) {
			i_0_ = (i_0_ << 1) + class330.method4284(aShortArray3365, i_0_, (byte) 21);
		}
		return i_0_ - (1 << -1777985159 * anInt3366);
	}

	public int method4164(Class330 class330, int i) throws IOException {
		int i_2_ = 1;
		int i_3_ = 0;
		for (int i_4_ = 0; i_4_ < anInt3366 * -1777985159; i_4_++) {
			int i_5_ = class330.method4284(aShortArray3365, i_2_, (byte) 21);
			i_2_ <<= 1;
			i_2_ += i_5_;
			i_3_ |= i_5_ << i_4_;
		}
		return i_3_;
	}

	public Class318(int i) {
		anInt3366 = i * -949616439;
		aShortArray3365 = new short[1 << i];
	}

	public void method4165() {
		Class93.method1305(aShortArray3365, (byte) -43);
	}

	public void method4166() {
		Class93.method1305(aShortArray3365, (byte) -100);
	}

	public void method4167(byte i) {
		Class93.method1305(aShortArray3365, (byte) -117);
	}

	public void method4168() {
		Class93.method1305(aShortArray3365, (byte) -25);
	}

	
}
