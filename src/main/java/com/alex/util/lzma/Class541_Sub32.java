package com.alex.util.lzma;

import java.io.IOException;

/**
 * Created at: Jul 30, 2016 8:55:00 AM
 * 
 * @author Walied-Yassen A.K.A Cody
 */
public class Class541_Sub32 {
	public static int method9556(short[] is, int i, Class330 class330, int i_25_, byte i_26_) throws IOException {
		int i_27_ = 1;
		int i_28_ = 0;
		for (int i_29_ = 0; i_29_ < i_25_; i_29_++) {
			int i_30_ = class330.method4284(is, i_27_ + i, (byte) 21);
			i_27_ <<= 1;
			i_27_ += i_30_;
			i_28_ |= i_30_ << i_29_;
		}
		return i_28_;
	}
}
