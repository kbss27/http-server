package util;

import model.User;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by mac on 2017. 3. 12..
 */
public class MatchModelTest {
    @Test
    public void matchModel_테스트(){
        String URL = "/user/create?userId=kbss27&password=1234&name=%EA%B9%80%ED%98%84%EC%9A%B0&email=kbss29%40naver.com";
        Map<String, String> mapUser = HttpRequestUtils.getParameter(URL);
        User user = new User("kbss27", "1234", "%EA%B9%80%ED%98%84%EC%9A%B0", "kbss29@naver.com");
        assertThat(user.toString(), is(new User("kbss27", "1234","%EA%B9%80%ED%98%84%EC%9A%B0", "kbss29@naver.com").toString()));

    }
}
