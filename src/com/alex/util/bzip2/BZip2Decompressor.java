package com.alex.util.bzip2;

public class BZip2Decompressor {
	static BZip2BlockEntry aBZip2BlockEntry4865 = new BZip2BlockEntry();

	static byte method5391(BZip2BlockEntry bzip2blockentry) {
		return (byte) method5394(1, bzip2blockentry);
	}

	public static int decompress(byte[] is, int i, byte[] is_0_, int i_1_, int i_2_) {
		synchronized (aBZip2BlockEntry4865) {
			aBZip2BlockEntry4865.aByteArray4909 = is_0_;
			aBZip2BlockEntry4865.anInt4901 = i_2_;
			aBZip2BlockEntry4865.aByteArray4917 = is;
			aBZip2BlockEntry4865.anInt4913 = 0;
			aBZip2BlockEntry4865.anInt4915 = i;
			aBZip2BlockEntry4865.anInt4910 = 0;
			aBZip2BlockEntry4865.anInt4924 = 0;
			aBZip2BlockEntry4865.anInt4902 = 0;
			aBZip2BlockEntry4865.anInt4906 = 0;
			method5393(aBZip2BlockEntry4865);
			i -= aBZip2BlockEntry4865.anInt4915;
			aBZip2BlockEntry4865.aByteArray4909 = null;
			aBZip2BlockEntry4865.aByteArray4917 = null;
			int i_3_ = i;
			return i_3_;
		}
	}

	static void method5393(BZip2BlockEntry bzip2blockentry) {
		boolean bool = false;
		boolean bool_4_ = false;
		boolean bool_5_ = false;
		boolean bool_6_ = false;
		boolean bool_7_ = false;
		boolean bool_8_ = false;
		boolean bool_9_ = false;
		boolean bool_10_ = false;
		boolean bool_11_ = false;
		boolean bool_12_ = false;
		boolean bool_13_ = false;
		boolean bool_14_ = false;
		boolean bool_15_ = false;
		boolean bool_16_ = false;
		boolean bool_17_ = false;
		boolean bool_18_ = false;
		boolean bool_19_ = false;
		boolean bool_20_ = false;
		int i = 0;
		int[] is = null;
		int[] is_21_ = null;
		int[] is_22_ = null;
		bzip2blockentry.anInt4911 = 1;
		if (BZip2BlockEntry.anIntArray4922 == null) {
			BZip2BlockEntry.anIntArray4922 = new int[bzip2blockentry.anInt4911 * 100000];
		}
		boolean bool_23_ = true;
		while (bool_23_) {
			byte i_24_ = method5395(bzip2blockentry);
			if (i_24_ == 23) {
				break;
			}
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5395(bzip2blockentry);
			i_24_ = method5391(bzip2blockentry);
			if (i_24_ == 0) {
				/* empty */
			}
			bzip2blockentry.anInt4912 = 0;
			int i_25_ = method5395(bzip2blockentry);
			bzip2blockentry.anInt4912 = bzip2blockentry.anInt4912 << 8 | i_25_ & 0xff;
			i_25_ = method5395(bzip2blockentry);
			bzip2blockentry.anInt4912 = bzip2blockentry.anInt4912 << 8 | i_25_ & 0xff;
			i_25_ = method5395(bzip2blockentry);
			bzip2blockentry.anInt4912 = bzip2blockentry.anInt4912 << 8 | i_25_ & 0xff;
			for (int i_26_ = 0; i_26_ < 16; i_26_++) {
				i_24_ = method5391(bzip2blockentry);
				if (i_24_ == 1) {
					bzip2blockentry.aBoolArray4921[i_26_] = true;
				} else {
					bzip2blockentry.aBoolArray4921[i_26_] = false;
				}
			}
			for (int i_27_ = 0; i_27_ < 256; i_27_++) {
				bzip2blockentry.aBoolArray4896[i_27_] = false;
			}
			for (int i_28_ = 0; i_28_ < 16; i_28_++) {
				if (bzip2blockentry.aBoolArray4921[i_28_]) {
					for (int i_29_ = 0; i_29_ < 16; i_29_++) {
						i_24_ = method5391(bzip2blockentry);
						if (i_24_ == 1) {
							bzip2blockentry.aBoolArray4896[i_28_ * 16 + i_29_] = true;
						}
					}
				}
			}
			method5396(bzip2blockentry);
			int i_30_ = bzip2blockentry.anInt4919 + 2;
			int i_31_ = method5394(3, bzip2blockentry);
			int i_32_ = method5394(15, bzip2blockentry);
			for (int i_33_ = 0; i_33_ < i_32_; i_33_++) {
				int i_34_ = 0;
				for (;;) {
					i_24_ = method5391(bzip2blockentry);
					if (i_24_ == 0) {
						break;
					}
					i_34_++;
				}
				bzip2blockentry.aByteArray4926[i_33_] = (byte) i_34_;
			}
			byte[] is_35_ = new byte[6];
			for (byte i_36_ = 0; i_36_ < i_31_; i_36_++) {
				is_35_[i_36_] = i_36_;
			}
			for (int i_37_ = 0; i_37_ < i_32_; i_37_++) {
				byte i_38_ = bzip2blockentry.aByteArray4926[i_37_];
				byte i_39_ = is_35_[i_38_];
				for (/**/; i_38_ > 0; i_38_--) {
					is_35_[i_38_] = is_35_[i_38_ - 1];
				}
				is_35_[0] = i_39_;
				bzip2blockentry.aByteArray4925[i_37_] = i_39_;
			}
			for (int i_40_ = 0; i_40_ < i_31_; i_40_++) {
				int i_41_ = method5394(5, bzip2blockentry);
				for (int i_42_ = 0; i_42_ < i_30_; i_42_++) {
					for (;;) {
						i_24_ = method5391(bzip2blockentry);
						if (i_24_ == 0) {
							break;
						}
						i_24_ = method5391(bzip2blockentry);
						if (i_24_ == 0) {
							i_41_++;
						} else {
							i_41_--;
						}
					}
					bzip2blockentry.getLength[i_40_][i_42_] = (byte) i_41_;
				}
			}
			for (int i_43_ = 0; i_43_ < i_31_; i_43_++) {
				int i_44_ = 32;
				byte i_45_ = 0;
				for (int i_46_ = 0; i_46_ < i_30_; i_46_++) {
					if (bzip2blockentry.getLength[i_43_][i_46_] > i_45_) {
						i_45_ = bzip2blockentry.getLength[i_43_][i_46_];
					}
					if (bzip2blockentry.getLength[i_43_][i_46_] < i_44_) {
						i_44_ = bzip2blockentry.getLength[i_43_][i_46_];
					}
				}
				method5397(bzip2blockentry.anIntArrayArray4894[i_43_], bzip2blockentry.anIntArrayArray4928[i_43_],
						bzip2blockentry.anIntArrayArray4918[i_43_], bzip2blockentry.getLength[i_43_], i_44_, i_45_,
						i_30_);
				bzip2blockentry.anIntArray4930[i_43_] = i_44_;
			}
			int i_47_ = bzip2blockentry.anInt4919 + 1;
			int i_48_ = -1;
			int i_49_ = 0;
			for (int i_50_ = 0; i_50_ <= 255; i_50_++) {
				bzip2blockentry.anIntArray4900[i_50_] = 0;
			}
			int i_51_ = 4095;
			for (int i_52_ = 15; i_52_ >= 0; i_52_--) {
				for (int i_53_ = 15; i_53_ >= 0; i_53_--) {
					bzip2blockentry.aByteArray4923[i_51_] = (byte) (i_52_ * 16 + i_53_);
					i_51_--;
				}
				bzip2blockentry.anIntArray4916[i_52_] = i_51_ + 1;
			}
			int i_54_ = 0;
			if (i_49_ == 0) {
				i_48_++;
				i_49_ = 50;
				byte i_55_ = bzip2blockentry.aByteArray4925[i_48_];
				i = bzip2blockentry.anIntArray4930[i_55_];
				is = bzip2blockentry.anIntArrayArray4894[i_55_];
				is_22_ = bzip2blockentry.anIntArrayArray4918[i_55_];
				is_21_ = bzip2blockentry.anIntArrayArray4928[i_55_];
			}
			i_49_--;
			int i_56_ = i;
			int i_57_;
			int i_58_;
			for (i_58_ = method5394(i_56_, bzip2blockentry); i_58_ > is[i_56_]; i_58_ = i_58_ << 1 | i_57_) {
				i_56_++;
				i_57_ = method5391(bzip2blockentry);
			}
			int i_59_ = is_22_[i_58_ - is_21_[i_56_]];
			while (i_59_ != i_47_) {
				if (i_59_ == 0 || i_59_ == 1) {
					int i_60_ = -1;
					int i_61_ = 1;
					do {
						if (i_59_ == 0) {
							i_60_ += i_61_;
						} else if (i_59_ == 1) {
							i_60_ += 2 * i_61_;
						}
						i_61_ *= 2;
						if (i_49_ == 0) {
							i_48_++;
							i_49_ = 50;
							byte i_62_ = bzip2blockentry.aByteArray4925[i_48_];
							i = bzip2blockentry.anIntArray4930[i_62_];
							is = bzip2blockentry.anIntArrayArray4894[i_62_];
							is_22_ = bzip2blockentry.anIntArrayArray4918[i_62_];
							is_21_ = bzip2blockentry.anIntArrayArray4928[i_62_];
						}
						i_49_--;
						i_56_ = i;
						for (i_58_ = method5394(i_56_, bzip2blockentry); i_58_ > is[i_56_]; i_58_ = i_58_ << 1
								| i_57_) {
							i_56_++;
							i_57_ = method5391(bzip2blockentry);
						}
						i_59_ = is_22_[i_58_ - is_21_[i_56_]];
					} while (i_59_ == 0 || i_59_ == 1);
					i_60_++;
					i_25_ = bzip2blockentry.aByteArray4905[bzip2blockentry.aByteArray4923[bzip2blockentry.anIntArray4916[0]]
							& 0xff];
					bzip2blockentry.anIntArray4900[i_25_ & 0xff] += i_60_;
					for (/**/; i_60_ > 0; i_60_--) {
						BZip2BlockEntry.anIntArray4922[i_54_] = i_25_ & 0xff;
						i_54_++;
					}
				} else {
					int i_63_ = i_59_ - 1;
					if (i_63_ < 16) {
						int i_64_ = bzip2blockentry.anIntArray4916[0];
						i_24_ = bzip2blockentry.aByteArray4923[i_64_ + i_63_];
						for (/**/; i_63_ > 3; i_63_ -= 4) {
							int i_65_ = i_64_ + i_63_;
							bzip2blockentry.aByteArray4923[i_65_] = bzip2blockentry.aByteArray4923[i_65_ - 1];
							bzip2blockentry.aByteArray4923[i_65_ - 1] = bzip2blockentry.aByteArray4923[i_65_ - 2];
							bzip2blockentry.aByteArray4923[i_65_ - 2] = bzip2blockentry.aByteArray4923[i_65_ - 3];
							bzip2blockentry.aByteArray4923[i_65_ - 3] = bzip2blockentry.aByteArray4923[i_65_ - 4];
						}
						for (/**/; i_63_ > 0; i_63_--) {
							bzip2blockentry.aByteArray4923[i_64_
									+ i_63_] = bzip2blockentry.aByteArray4923[i_64_ + i_63_ - 1];
						}
						bzip2blockentry.aByteArray4923[i_64_] = i_24_;
					} else {
						int i_66_ = i_63_ / 16;
						int i_67_ = i_63_ % 16;
						int i_68_ = bzip2blockentry.anIntArray4916[i_66_] + i_67_;
						i_24_ = bzip2blockentry.aByteArray4923[i_68_];
						for (/**/; i_68_ > bzip2blockentry.anIntArray4916[i_66_]; i_68_--) {
							bzip2blockentry.aByteArray4923[i_68_] = bzip2blockentry.aByteArray4923[i_68_ - 1];
						}
						bzip2blockentry.anIntArray4916[i_66_]++;
						for (/**/; i_66_ > 0; i_66_--) {
							bzip2blockentry.anIntArray4916[i_66_]--;
							bzip2blockentry.aByteArray4923[bzip2blockentry.anIntArray4916[i_66_]] = bzip2blockentry.aByteArray4923[bzip2blockentry.anIntArray4916[i_66_
									- 1] + 16 - 1];
						}
						bzip2blockentry.anIntArray4916[0]--;
						bzip2blockentry.aByteArray4923[bzip2blockentry.anIntArray4916[0]] = i_24_;
						if (bzip2blockentry.anIntArray4916[0] == 0) {
							i_51_ = 4095;
							for (int i_69_ = 15; i_69_ >= 0; i_69_--) {
								for (int i_70_ = 15; i_70_ >= 0; i_70_--) {
									bzip2blockentry.aByteArray4923[i_51_] = bzip2blockentry.aByteArray4923[bzip2blockentry.anIntArray4916[i_69_]
											+ i_70_];
									i_51_--;
								}
								bzip2blockentry.anIntArray4916[i_69_] = i_51_ + 1;
							}
						}
					}
					bzip2blockentry.anIntArray4900[bzip2blockentry.aByteArray4905[i_24_ & 0xff] & 0xff]++;
					BZip2BlockEntry.anIntArray4922[i_54_] = bzip2blockentry.aByteArray4905[i_24_ & 0xff] & 0xff;
					i_54_++;
					if (i_49_ == 0) {
						i_48_++;
						i_49_ = 50;
						byte i_71_ = bzip2blockentry.aByteArray4925[i_48_];
						i = bzip2blockentry.anIntArray4930[i_71_];
						is = bzip2blockentry.anIntArrayArray4894[i_71_];
						is_22_ = bzip2blockentry.anIntArrayArray4918[i_71_];
						is_21_ = bzip2blockentry.anIntArrayArray4928[i_71_];
					}
					i_49_--;
					i_56_ = i;
					for (i_58_ = method5394(i_56_, bzip2blockentry); i_58_ > is[i_56_]; i_58_ = i_58_ << 1 | i_57_) {
						i_56_++;
						i_57_ = method5391(bzip2blockentry);
					}
					i_59_ = is_22_[i_58_ - is_21_[i_56_]];
				}
			}
			bzip2blockentry.anInt4908 = 0;
			bzip2blockentry.aByte4904 = (byte) 0;
			bzip2blockentry.anIntArray4927[0] = 0;
			for (int i_72_ = 1; i_72_ <= 256; i_72_++) {
				bzip2blockentry.anIntArray4927[i_72_] = bzip2blockentry.anIntArray4900[i_72_ - 1];
			}
			for (int i_73_ = 1; i_73_ <= 256; i_73_++) {
				bzip2blockentry.anIntArray4927[i_73_] += bzip2blockentry.anIntArray4927[i_73_ - 1];
			}
			for (int i_74_ = 0; i_74_ < i_54_; i_74_++) {
				i_25_ = (byte) (BZip2BlockEntry.anIntArray4922[i_74_] & 0xff);
				BZip2BlockEntry.anIntArray4922[bzip2blockentry.anIntArray4927[i_25_ & 0xff]] |= i_74_ << 8;
				bzip2blockentry.anIntArray4927[i_25_ & 0xff]++;
			}
			bzip2blockentry.anInt4907 = BZip2BlockEntry.anIntArray4922[bzip2blockentry.anInt4912] >> 8;
			bzip2blockentry.anInt4903 = 0;
			bzip2blockentry.anInt4907 = BZip2BlockEntry.anIntArray4922[bzip2blockentry.anInt4907];
			bzip2blockentry.anInt4914 = (byte) (bzip2blockentry.anInt4907 & 0xff);
			BZip2BlockEntry bzip2blockentry_75_;
			(bzip2blockentry_75_ = bzip2blockentry).anInt4907 = bzip2blockentry_75_.anInt4907 >> 8;
			bzip2blockentry.anInt4903++;
			bzip2blockentry.anInt4931 = i_54_;
			method5398(bzip2blockentry);
			if (bzip2blockentry.anInt4903 == bzip2blockentry.anInt4931 + 1 && bzip2blockentry.anInt4908 == 0) {
				bool_23_ = true;
			} else {
				bool_23_ = false;
			}
		}
	}

	static int method5394(int i, BZip2BlockEntry bzip2blockentry) {
		for (;;) {
			if (bzip2blockentry.anInt4910 >= i) {
				int i_76_ = bzip2blockentry.anInt4924 >> bzip2blockentry.anInt4910 - i & (1 << i) - 1;
				bzip2blockentry.anInt4910 -= i;
				return i_76_;
			}
			bzip2blockentry.anInt4924 = bzip2blockentry.anInt4924 << 8
					| bzip2blockentry.aByteArray4909[bzip2blockentry.anInt4901] & 0xff;
			bzip2blockentry.anInt4910 += 8;
			bzip2blockentry.anInt4901++;
			bzip2blockentry.anInt4902++;
			if (bzip2blockentry.anInt4902 == 0) {
				/* empty */
			}
		}
	}

	static byte method5395(BZip2BlockEntry bzip2blockentry) {
		return (byte) method5394(8, bzip2blockentry);
	}

	static void method5396(BZip2BlockEntry bzip2blockentry) {
		bzip2blockentry.anInt4919 = 0;
		for (int i = 0; i < 256; i++) {
			if (bzip2blockentry.aBoolArray4896[i]) {
				bzip2blockentry.aByteArray4905[bzip2blockentry.anInt4919] = (byte) i;
				bzip2blockentry.anInt4919++;
			}
		}
	}

	static void method5397(int[] is, int[] is_77_, int[] is_78_, byte[] is_79_, int i,
			int i_80_, int i_81_) {
		int i_82_ = 0;
		for (int i_83_ = i; i_83_ <= i_80_; i_83_++) {
			for (int i_84_ = 0; i_84_ < i_81_; i_84_++) {
				if (is_79_[i_84_] == i_83_) {
					is_78_[i_82_] = i_84_;
					i_82_++;
				}
			}
		}
		for (int i_85_ = 0; i_85_ < 23; i_85_++) {
			is_77_[i_85_] = 0;
		}
		for (int i_86_ = 0; i_86_ < i_81_; i_86_++) {
			is_77_[is_79_[i_86_] + 1]++;
		}
		for (int i_87_ = 1; i_87_ < 23; i_87_++) {
			is_77_[i_87_] += is_77_[i_87_ - 1];
		}
		for (int i_88_ = 0; i_88_ < 23; i_88_++) {
			is[i_88_] = 0;
		}
		int i_89_ = 0;
		for (int i_90_ = i; i_90_ <= i_80_; i_90_++) {
			i_89_ += is_77_[i_90_ + 1] - is_77_[i_90_];
			is[i_90_] = i_89_ - 1;
			i_89_ <<= 1;
		}
		for (int i_91_ = i + 1; i_91_ <= i_80_; i_91_++) {
			is_77_[i_91_] = (is[i_91_ - 1] + 1 << 1) - is_77_[i_91_];
		}
	}

	BZip2Decompressor() throws Throwable {
		throw new Error();
	}

	static void method5398(BZip2BlockEntry bzip2blockentry) {
		byte i = bzip2blockentry.aByte4904;
		int i_92_ = bzip2blockentry.anInt4908;
		int i_93_ = bzip2blockentry.anInt4903;
		int i_94_ = bzip2blockentry.anInt4914;
		int[] is = BZip2BlockEntry.anIntArray4922;
		int i_95_ = bzip2blockentry.anInt4907;
		byte[] is_96_ = bzip2blockentry.aByteArray4917;
		int i_97_ = bzip2blockentry.anInt4913;
		int i_98_ = bzip2blockentry.anInt4915;
		int i_99_ = i_98_;
		int i_100_ = bzip2blockentry.anInt4931 + 1;
		while_42_: for (;;) {
			if (i_92_ > 0) {
				for (;;) {
					if (i_98_ == 0) {
						break while_42_;
					}
					if (i_92_ == 1) {
						break;
					}
					is_96_[i_97_] = i;
					i_92_--;
					i_97_++;
					i_98_--;
				}
				if (i_98_ == 0) {
					i_92_ = 1;
					break;
				}
				is_96_[i_97_] = i;
				i_97_++;
				i_98_--;
			}
			for (;;) {
				if (i_93_ == i_100_) {
					i_92_ = 0;
					break while_42_;
				}
				i = (byte) i_94_;
				i_95_ = is[i_95_];
				int i_101_ = (byte) i_95_;
				i_95_ >>= 8;
				i_93_++;
				if (i_101_ != i_94_) {
					i_94_ = i_101_;
					if (i_98_ == 0) {
						i_92_ = 1;
						break while_42_;
					}
					is_96_[i_97_] = i;
					i_97_++;
					i_98_--;
				} else {
					if (i_93_ != i_100_) {
						break;
					}
					if (i_98_ == 0) {
						i_92_ = 1;
						break while_42_;
					}
					is_96_[i_97_] = i;
					i_97_++;
					i_98_--;
				}
			}
			i_92_ = 2;
			i_95_ = is[i_95_];
			int i_102_ = (byte) i_95_;
			i_95_ >>= 8;
			if (++i_93_ != i_100_) {
				if (i_102_ != i_94_) {
					i_94_ = i_102_;
				} else {
					i_92_ = 3;
					i_95_ = is[i_95_];
					i_102_ = (byte) i_95_;
					i_95_ >>= 8;
					if (++i_93_ != i_100_) {
						if (i_102_ != i_94_) {
							i_94_ = i_102_;
						} else {
							i_95_ = is[i_95_];
							i_102_ = (byte) i_95_;
							i_95_ >>= 8;
							i_93_++;
							i_92_ = (i_102_ & 0xff) + 4;
							i_95_ = is[i_95_];
							i_94_ = (byte) i_95_;
							i_95_ >>= 8;
							i_93_++;
						}
					}
				}
			}
		}
		int i_103_ = bzip2blockentry.anInt4906;
		bzip2blockentry.anInt4906 += i_99_ - i_98_;
		if (bzip2blockentry.anInt4906 >= i_103_) {
			/* empty */
		}
		bzip2blockentry.aByte4904 = i;
		bzip2blockentry.anInt4908 = i_92_;
		bzip2blockentry.anInt4903 = i_93_;
		bzip2blockentry.anInt4914 = i_94_;
		BZip2BlockEntry.anIntArray4922 = is;
		bzip2blockentry.anInt4907 = i_95_;
		bzip2blockentry.aByteArray4917 = is_96_;
		bzip2blockentry.anInt4913 = i_97_;
		bzip2blockentry.anInt4915 = i_98_;
	}
}
