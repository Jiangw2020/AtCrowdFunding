package jw.crowd.test;

import jw.crowd.entity.Admin;
import jw.crowd.entity.Role;
import jw.crowd.mapper.AdminMapper;
import jw.crowd.mapper.RoleMapper;
import jw.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//创建 Spring 的 Junit 测试类
// 指定 Spring 给 Junit 提供的运行器类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 配置文件的注解
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testTX(){
        adminService.saveAdmin(new Admin(null,"汤姆","tom123","tom","tom@qq.com","2021-01-12"));
    }
    @Test
    public void testAdmin(){
        int insert = adminMapper.insert(new Admin(null,"jiang","123456","admin","123456@qq.com","2021-1-12"));
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        // 按照 Debug 级别打印日志
        logger.debug("受影响行数："+insert);
    }
    @Test
    public void testDataSource() throws SQLException {
        // 1.通过数据源对象获取数据源连接
        Connection connection = dataSource.getConnection();
        // 2.打印数据库连接
        System.out.println(connection);
    }
    @Test
    public void testSaveAdminMulti() {
        for(int i = 0; i < 352; i++) {
            adminMapper.insert(new Admin(null, "loginAcct"+i, "userPswd"+i, "userName"+i, "email"+i+"@qq.com", null));
        }
    }
    @Test
    public void testSaveRoleMulti() {
        for(int i = 0; i < 37; i++) {
            roleMapper.insert(new Role(null,"role"+i));
        }
    }
    @Test
    public void testPasswordEncoder(){
        System.out.println(passwordEncoder.encode("123456"));
    }
}