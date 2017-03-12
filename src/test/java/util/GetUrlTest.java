package util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by mac on 2017. 3. 12..
 */
public class GetUrlTest {

    @Test
    public void STATUS_LINE_해석_테스트(){
        String statusLine = "GET /index.html HTTP/1.1";
        String path = GetUrl.getURL(statusLine);

        assertThat(path, is("/index.html"));
    }
}
