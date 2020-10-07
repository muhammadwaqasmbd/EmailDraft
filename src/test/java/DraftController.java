

import com.email.draft.service.DraftService;
import com.email.draft.vo.DraftVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DraftController {

    @Autowired
    private DraftService service;

    @Test
    public void testEmail() {
    	DraftVO draft = service.findById(1);
    	System.out.println(draft.getId());
    }
}