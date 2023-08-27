package com.BugBazaar.utils;


public class s {
    private static final int J = 256;
    private final int[] j;
    private int d = 0;
    private int L = 0;

    public static String H(Object obj) {
        String str = (String) obj;
        int length = str.length();
        char[] cArr = new char[length];
        int i = length - 1;
        while (i >= 0) {
            int i2 = i - 1;
            cArr[i] = (char) (str.charAt(i) ^ '[');
            if (i2 < 0) {
                break;
            }
            i = i2 - 1;
            cArr[i2] = (char) (str.charAt(i2) ^ 'n');
        }
        return new String(cArr);
    }

    private /* synthetic */ void H(int i, int i2, int[] iArr) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
    }

    private /* synthetic */ int[] H(byte[] bArr) {
        int[] iArr = new int[256];
        for (int i = 0; i < 256; i++) {
            iArr[i] = i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < 256; i3++) {
            i2 = (((i2 + iArr[i3]) + bArr[i3 % bArr.length]) + 256) % 256;
            H(i3, i2, iArr);
        }
        return iArr;
    }

    public s(byte[] bArr) {
        this.j = H(bArr);
    }

    /* renamed from: H  reason: collision with other method in class */
    public byte[] m28H(byte[] bArr) {
        return I(bArr);
    }

    public byte[] I(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (this.d + 1) % 256;
            this.d = i2;
            int i3 = this.L;
            int[] iArr = this.j;
            int i4 = (i3 + iArr[i2]) % 256;
            this.L = i4;
            H(i2, i4, iArr);
            int[] iArr2 = this.j;
            bArr2[i] = (byte) (iArr2[(iArr2[this.d] + iArr2[this.L]) % 256] ^ bArr[i]);
        }
        return bArr2;
    }
}

