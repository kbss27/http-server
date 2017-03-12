package util;

/**
 * Created by mac on 2017. 3. 12..
 */
public class GetUrl {

    private static final int STATUS_REQUEST_LINE = 0;
    private static final int STATUS_METHOD = 0;
    private static final int STATUS_URL = 1;
    private static final int STATUS_PROTOCOL = 2;

    public static String getURL(String header, int status){

        String token[] = header.split("\\r\\n");
        String URLtoken[] = token[STATUS_REQUEST_LINE].split(" ");
        if(status == STATUS_METHOD)
            return URLtoken[STATUS_METHOD];
        else if(status == STATUS_URL)
            return URLtoken[STATUS_URL];
        else if(status == STATUS_PROTOCOL)
            return URLtoken[STATUS_PROTOCOL];
        return null;
    }

}
