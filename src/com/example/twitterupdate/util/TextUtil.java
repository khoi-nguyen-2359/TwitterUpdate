package com.example.twitterupdate.util;

import java.io.InputStream;
import java.io.InputStreamReader;

public final class TextUtil {
    
    /**
     * Read string data from input stream, dont care the encoding.
     * @param is
     * @return
     */
    public static String toString(InputStream is) {
        StringBuilder result = new StringBuilder();
        InputStreamReader isReader = new InputStreamReader(is);
        final int buffLen = 1024;
        char[] buff = new char[buffLen];
        int read = 0;

        try {
            while ((read = isReader.read(buff, read, buffLen)) > 0)
                result.append(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result.toString();
    }
}
