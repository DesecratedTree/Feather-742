package com.alex.util.lzma;

/*
 * Class330 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Class330 {
	public int anInt3459;
	public static int anInt3460 = 11;
	public InputStream anInputStream3461;
	public static int anInt3462 = 2048;
	public int anInt3463;
	public static int anInt3464 = 5;
	public static int anInt3465 = -16777216;
	public static byte aByte3466;
	public static File aFile3467;

	public int method4279(int i, int i_0_) throws IOException {
		int i_1_ = 0;
		for (int i_2_ = i; i_2_ != 0; i_2_--) {
			anInt3463 = -1326516403 * (anInt3463 * 150935941 >>> 1);
			int i_3_ = 1999093581 * anInt3459 - 150935941 * anInt3463 >>> 31;
			anInt3459 -= -679767675 * (anInt3463 * 150935941 & i_3_ - 1);
			i_1_ = i_1_ << 1 | 1 - i_3_;
			if ((150935941 * anInt3463 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
			}
		}
		return i_1_;
	}

	public int method4280(int i) throws IOException {
		int i_4_ = 0;
		for (int i_5_ = i; i_5_ != 0; i_5_--) {
			anInt3463 = -1326516403 * (anInt3463 * 150935941 >>> 1);
			int i_6_ = 1999093581 * anInt3459 - 150935941 * anInt3463 >>> 31;
			anInt3459 -= -679767675 * (anInt3463 * 150935941 & i_6_ - 1);
			i_4_ = i_4_ << 1 | 1 - i_6_;
			if ((150935941 * anInt3463 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
			}
		}
		return i_4_;
	}

	public void method4281(int i) {
		anInputStream3461 = null;
	}

	public void method4282(int i) throws IOException {
		anInt3459 = 0;
		anInt3463 = 1326516403;
		for (int i_7_ = 0; i_7_ < 5; i_7_++) {
			anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
		}
	}

	public void method4283(InputStream inputstream, int i) {
		anInputStream3461 = inputstream;
	}

	public int method4284(short[] is, int i, byte i_8_) throws IOException {
		int i_9_ = is[i];
		int i_10_ = (150935941 * anInt3463 >>> 11) * i_9_;
		if ((1999093581 * anInt3459 ^ ~0x7fffffff) < (i_10_ ^ ~0x7fffffff)) {
			anInt3463 = -1326516403 * i_10_;
			is[i] = (short) (i_9_ + (2048 - i_9_ >>> 5));
			if ((anInt3463 * 150935941 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (150935941 * anInt3463 << 8) * -1326516403;
			}
			return 0;
		}
		anInt3463 -= i_10_ * -1326516403;
		anInt3459 -= i_10_ * -679767675;
		is[i] = (short) (i_9_ - (i_9_ >>> 5));
		if (0 == (anInt3463 * 150935941 & ~0xffffff)) {
			anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
			anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
		}
		return 1;
	}

	public void method4285() {
		anInputStream3461 = null;
	}

	public void method4286() {
		anInputStream3461 = null;
	}

	public int method4287(int i) throws IOException {
		int i_11_ = 0;
		for (int i_12_ = i; i_12_ != 0; i_12_--) {
			anInt3463 = -1326516403 * (anInt3463 * 150935941 >>> 1);
			int i_13_ = 1999093581 * anInt3459 - 150935941 * anInt3463 >>> 31;
			anInt3459 -= -679767675 * (anInt3463 * 150935941 & i_13_ - 1);
			i_11_ = i_11_ << 1 | 1 - i_13_;
			if ((150935941 * anInt3463 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
			}
		}
		return i_11_;
	}

	public int method4288(int i) throws IOException {
		int i_14_ = 0;
		for (int i_15_ = i; i_15_ != 0; i_15_--) {
			anInt3463 = -1326516403 * (anInt3463 * 150935941 >>> 1);
			int i_16_ = 1999093581 * anInt3459 - 150935941 * anInt3463 >>> 31;
			anInt3459 -= -679767675 * (anInt3463 * 150935941 & i_16_ - 1);
			i_14_ = i_14_ << 1 | 1 - i_16_;
			if ((150935941 * anInt3463 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
			}
		}
		return i_14_;
	}

	public int method4289(int i) throws IOException {
		int i_17_ = 0;
		for (int i_18_ = i; i_18_ != 0; i_18_--) {
			anInt3463 = -1326516403 * (anInt3463 * 150935941 >>> 1);
			int i_19_ = 1999093581 * anInt3459 - 150935941 * anInt3463 >>> 31;
			anInt3459 -= -679767675 * (anInt3463 * 150935941 & i_19_ - 1);
			i_17_ = i_17_ << 1 | 1 - i_19_;
			if ((150935941 * anInt3463 & ~0xffffff) == 0) {
				anInt3459 = (anInt3459 * 1999093581 << 8 | anInputStream3461.read()) * -679767675;
				anInt3463 = (anInt3463 * 150935941 << 8) * -1326516403;
			}
		}
		return i_17_;
	}

}
