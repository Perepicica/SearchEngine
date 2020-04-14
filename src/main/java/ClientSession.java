import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ClientSession implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;

    public ClientSession(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }

    @Override
    public void run() {
        try {
            String header = readHeader();
            if(readUrl(header).equals("/")){
                String param = getParam(header);
                if(param == null || param.equals("GET")){
                    System.out.println("main");
                    page(index+index2);
                } else {
                    System.out.println(param);
                    resultPage(param);
                }
            }
        } catch (Throwable t) {
            /*do nothing*/
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                /*do nothing*/
            }
        }
        System.err.println("Client processing finished");
    }

    private void resultPage(String param) throws IOException{
        Set<String> result = Ranging.selectRequest(param);
        if (result == null) {
            page(index+notFound);
        }
        List<String> aList = new ArrayList<>();
        aList.addAll(result);
    }

    private String getParam(String header){
        int from = header.indexOf("?") + 1;
        if (from>20){
            return null;
        }
        if(header.substring(from+1,from+2).equals(" ")){
            return null;
        }
        int to = header.indexOf(" ", from);
        String params = header.substring(from, to);
        int paramIndex = params.indexOf("=");
        return params.substring(paramIndex + 1);
    }

    private void page(String page) throws IOException{
        System.out.println(page);
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: Server/2020-04-13\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + page.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + page ;
        os.write(result.getBytes());
        os.flush();
    }



     private String readUrl(String header){
        return "/";
     }

    private String readHeader() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder = new StringBuilder();
        String ln;
        while (true) {
            ln = reader.readLine();
            if (ln == null || ln.isEmpty()) {
                break;
            }
            builder.append(ln + System.getProperty("line.separator"));
        }
        return builder.toString();
    }

    private String notFound = "<h1>RESPONSE</h1>\n" +
            "</body>\n" +
            "</html>";

    private String index = "<!DOCTYPE html>\n" +
            "<html lang=\"ru\" dir=\"ltr\">\n" +
            "<head>\n" +
            "    <style>\n" +
            "   .fig {\n" +
            "   margin-top: 100px;\n" +
            "    text-align: center; /* Выравнивание по центру */\n" +
            "   }\n" +
            "   .input{\n" +
            "   border: solid #d3d3d3;\n" +
            "   outline: 0;\n" +
            "   padding-left: 10px;\n" +
            "   height: 30px;\n" +
            "   border-radius: 20px; /*Закругляем уголки*/\n" +
            "   }\n" +
            "  .button{\n" +
            "   border: solid #d3d3d3;\n" +
            "   outline: 0;\n" +
            "   height: 30px;\n" +
            "   border-radius: 20px; /*Закругляем уголки*/\n" +
            "  }\n" +
            "\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"fig\">\n" +
            "    <form>\n" +
            "        <input class=\"button\" type=\"submit\" value=\"Search\">\n" +
            "        <input class=\"input\" type=\"text\" size=\"55\" name=\"search\">\n" +
            "    </form>\n" +
            "</div>\n" ;

            String index2="\n" +
                    "aaa"+
                    "</body>\n" +
                    "</html>";
}
