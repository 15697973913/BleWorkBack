package com.android.common.baselibrary.util.crcutil;


import java.util.ArrayList;
import java.util.List;

public class BleHexCrcUtil {


/*    public static long computeCRC32(String bleData, int dataLength) {
        String[] bleDataCharArray = getHexString(bleData, dataLength);
        long crc = Long.parseLong("FFFFFFFF", 16);

        for (int i = 0; i < dataLength; i++) {
            crc = crc^ Long.parseLong(bleDataCharArray[i], 16);
            for (int j = 8; j > 0; j--) {
                crc = (crc >> 1) ^ (Long.parseLong("EDB88320", 16) & ((crc & 1) > 0 ? Long.parseLong("FFFFFFFF", 16) : 0));
            }
        }
        return ~crc;
    }*/

    public static long computeCRC32(String bleData, int dataLength) {
        List<String> bleDataCharArray = getHexString(bleData, dataLength);
        long crc = 0xFFFFFFFFL;

        for (int i = 0; i < dataLength; i++) {
            crc = crc^Integer.parseInt(bleDataCharArray.get(i), 16);
            for (int j = 8; j > 0; j--) {
                crc = (crc >> 1) ^ (0xEDB88320L & ((crc & 1) > 0 ? 0xFFFFFFFFL : 0));
            }
        }
        return ~crc;
    }

    private static List<String> getHexString(String bleData, int length) {
        StringBuffer stringBuffer = new StringBuffer(bleData);

        List<String> hexList = new ArrayList<>();
        for (int i = 0; i < length ; i++) {
            hexList.add(stringBuffer.substring( 2 * i, 2 * (i + 1)));
        }
        return hexList;
    }

/*
    uint32_t crc32_compute(uint8_t const * p_data, uint32_t size, uint32_t const * p_crc)
    {
        uint32_t crc;

        crc = (p_crc == NULL) ? 0xFFFFFFFF : ~(*p_crc);
        for (uint32_t i = 0; i < size; i++)
        {
            crc = crc ^ p_data[i];
            for (uint32_t j = 8; j > 0; j--)
            {
                crc = (crc >> 1) ^ (0xEDB88320U & ((crc & 1) ? 0xFFFFFFFF : 0));
            }
        }
        return ~crc;
    }
*/

}
