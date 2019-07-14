import io.zhangjia.mall.entity.Commodity;
import io.zhangjia.mall.mapper.CommodityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = build.openSession(true);
        CommodityMapper mapper = sqlSession.getMapper(CommodityMapper.class);

        mapper.query(null);


        sqlSession.close();
        inputStream.close();
    }
}