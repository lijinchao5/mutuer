package com.xuanli.oepcms.util;

import java.util.Base64;
import java.util.Base64.Decoder;

public final class ImageUtil {
    private ImageUtil() {
    }

    public static byte[] decodeToBytes(String base64Image) {
        if (base64Image.startsWith("data:")) {
            int index = base64Image.indexOf("base64,");
            if (index > 0) {
                base64Image = base64Image.substring(index + "base64,".length());
            }
        }
        Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Image);
    }
}
