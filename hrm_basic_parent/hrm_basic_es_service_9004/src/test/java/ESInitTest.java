import cn.itsource.hrm.Es9004Application;
import cn.itsource.hrm.doc.EsCourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Es9004Application.class)
public class ESInitTest {

    @Autowired
    private ElasticsearchTemplate template;
    @Test
    public void testInit()throws Exception{
        template.createIndex(EsCourse.class);
        template.putMapping(EsCourse.class);
    }
}
