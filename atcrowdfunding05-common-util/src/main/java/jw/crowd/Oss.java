package jw.crowd;

import jw.crowd.util.ResultEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static jw.crowd.util.CrowdUtil.uploadFileToOss;

public class Oss {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("*************");
        System.out.println();
        ResultEntity<String> resultEntity = uploadFileToOss(
                "*************",
                "*************",
                "*************",
                 fileInputStream,
                "*************",
                "*************",
                "*************");
        System.out.println(resultEntity);
    }
}
