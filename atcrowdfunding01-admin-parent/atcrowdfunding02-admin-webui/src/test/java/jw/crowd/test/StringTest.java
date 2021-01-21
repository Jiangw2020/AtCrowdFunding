package jw.crowd.test;

import jw.crowd.util.CrowdUtil;
import org.junit.Test;

public class StringTest {
    @Test
    public void testMd5(){
        String source="123456";
        System.out.println(CrowdUtil.md5(source));
    }
}
