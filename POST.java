import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class POST
{
    public HttpURLConnection con;
    private String url;
    private ArrayList<String> name=new ArrayList<String>(),
            arg=new ArrayList<String>();
    
    public POST(String url)
    {
       this.url=url;
    }
    public void setCookie(String name,String arg)
    {
        this.name.add(name);
        this.arg.add(arg);
    }
    public void sendData(String data)
    {
        try {
            byte[]mes=data.getBytes("UTF8");
            con=(HttpURLConnection)new URL(url).openConnection();
            con.setRequestMethod("POST");
            for(short i=0;i<name.size();i++)
                con.setRequestProperty(name.get(i), arg.get(i));
            con.setRequestProperty("Content-Length", mes.length+"");
            con.setDoOutput(true);
            OutputStream out=con.getOutputStream();
            out.write(mes);
            out.flush();
            out.close();
            con.disconnect();
            System.out.println("Status: "+con.getResponseCode()+' '+con.getResponseMessage());
        } catch (ProtocolException ex) {
            System.out.println(ex);
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Encode table NF");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}