package pers.ken.rt;

/**
 * <code> BaseTest </code>
 * <desc> BaseTest </desc>
 * <b>Creation Time:</b> 2021/10/8 16:04.
 *
 * @author _Ken.Hu
 */
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;

@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {
    @Autowired
    private WebApplicationContext context;
    public MockMvc mockMvc;

    @BeforeEach
    public void init() throws SQLException {
        //mockMvc = MockMvcBuilders.standaloneSetup(new DemoController()).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();    //构造器，构造一个mockMvc
        //通过context上下文获取controller产生对应的MVC推荐使用第二种
    }
}
