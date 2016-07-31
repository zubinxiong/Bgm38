package me.ewriter.bangumitv.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by Zubin on 2016/4/19.
 */
public class SerializeUtils {

    /**
     *  序列化对象到本地文件
     * @param obj
     * @param filePath
     * @return
     */
    public static boolean serializeToLocalFile(Serializable obj, String filePath) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filePath));
            os.writeObject(obj);
            os.flush();
            os.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 反序列文件到对象
     * @param filePath
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserializeFromLocalFile(String filePath){

        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filePath));

            return (T)ois.readObject();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] serializeToBytes(Serializable obj) throws Exception {
        if (obj == null)
            return null;

        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            return serialObj.toByteArray();
        } catch (Exception e) {
            throw e;
        }
    }


}
