package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import com.google.common.collect.Maps;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.GetUrl;
import util.HttpRequestUtils;
import util.IOUtils;
import util.MatchModel;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            // todo: 요청 라인을 해석하는 작업
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            StringBuffer sb = new StringBuffer();

            String line = br.readLine();

            if(line == null) {
                return;
            }

            Map<String, HttpRequestUtils.Pair> mapPair = Maps.newHashMap();
            while(!"".equals(line)) {
                sb.append(line+"\n");
                HttpRequestUtils.Pair pair = HttpRequestUtils.parseHeader(line);
                if(pair != null) {
                    mapPair.put(pair.getKey(), pair);
                }
                log.info(line);
                line = br.readLine();
            }
            String header = sb.toString();
            String method = GetUrl.getURL(header, 0);


            log.info("[CHeck Method] - {}", method);
            //여기서부터 body
            if(method.equalsIgnoreCase("POST")){

                String bodyContent = IOUtils.readData(br, Integer.parseInt(mapPair.get("Content-Length").getValue()));
                Map<String, String> mapUser = HttpRequestUtils.parseQueryString(bodyContent);
                User user = MatchModel.matchModel(mapUser);
                log.info("[body] - {}", user.toString());
                return;
            }

            String URL = GetUrl.getURL(header, 1);
            Map<String, String> mapUser = HttpRequestUtils.getParameter(URL);
            User user = MatchModel.matchModel(mapUser);
            log.info(URL);

            // 응답을 만드는 과정
            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./webapp"+URL).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
