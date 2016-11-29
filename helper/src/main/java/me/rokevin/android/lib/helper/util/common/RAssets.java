package me.rokevin.android.lib.helper.util.common;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by luokaiwen on 16/4/6.
 * <p/>
 * 资源帮助类
 */
public class RAssets {

    public static void copyBigDataToSD(Context context, String strOutFileName, String assetName) throws IOException {

        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = context.getAssets().open(assetName);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }
}
