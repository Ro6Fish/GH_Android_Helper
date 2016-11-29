package me.rokevin.android.lib.helper.util.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 * Created by luokaiwen on 16/4/5.
 * <p>
 * 音频合并类
 */
public class RAudioMerge {

    /**
     * 需求:将两个amr格式音频文件合并为1个
     * 注意:amr格式的头文件为6个字节的长度
     *
     * @param partsPaths     各部分路径
     * @param unitedFilePath 合并后路径
     *                       <p>
     *                       示例:
     *                       合并test1和test2为test3
     *                       String[] paths = {RSDCard.getAmrPath("test1"), RSDCard.getAmrPath("test2")};
     *                       <p>
     *                       RAudioMerge.uniteAMRFile(paths, RSDCard.getAmrPath("test3"));
     */
    public static void uniteAMRFile(String[] partsPaths, String unitedFilePath) {
        try {
            File unitedFile = new File(unitedFilePath);
            FileOutputStream fos = new FileOutputStream(unitedFile);
            RandomAccessFile ra = null;
            for (int i = 0; i < partsPaths.length; i++) {
                ra = new RandomAccessFile(partsPaths[i], "r");
                if (i != 0) {
                    ra.seek(6);
                }
                byte[] buffer = new byte[1024 * 8];
                int len = 0;
                while ((len = ra.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
            ra.close();
            fos.close();
        } catch (Exception e) {
        }
    }
}
