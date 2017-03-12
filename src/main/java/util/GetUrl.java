package util;

/**
 * Created by mac on 2017. 3. 12..
 */
public class GetUrl {

    private static final int STATUS_REQUEST_LINE = 0;
    private static final int STATUS_URL = 1;

    public static String getURL(String header){

        String token[] = header.split("\\r\\n");
        String URLtoken[] = token[STATUS_REQUEST_LINE].split(" ");
        return URLtoken[STATUS_URL];

    }

}
