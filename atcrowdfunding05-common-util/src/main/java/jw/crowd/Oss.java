package jw.crowd;

import jw.crowd.util.ResultEntity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static jw.crowd.util.CrowdUtil.uploadFileToOss;

public class Oss {
    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ASUS\\Pictures\\Camera Roll\\1.jpg");
        System.out.println();
        ResultEntity<String> resultEntity = uploadFileToOss(
                "http://oss.aliyuncs.com",
                "LTAI4GKTr7bJ",
                "7bqohh0RvHn3EsFsL6",
                 fileInputStream,
                "jw2021",
                "http://jw2021.oss-cn-hangzhou.aliyuncs.com",
                "1.jpg");
        System.out.println(resultEntity);
    }
}
