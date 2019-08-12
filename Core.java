import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Core
{
    public static POST post=null;
    public static void main(String args[]) throws InterruptedException, IOException
    {
        String data,name,token="???",mangaNum="???"
                mangaSesion="???",
                XToken="???";
        init(mangaNum,XToken,mangaSesion);
        Scanner namae=null;
        try{
            namae=new Scanner(new File("names"));
            short index=821;
        
            while(index<=850)
            {
            data=URLEncoder.encode(Util.readFile(index+""), "UTF8");
            name=URLEncoder.encode(namae.nextLine(), "UTF8");
            System.out.println(index);
            send(token, 1, index, name, data, mangaNum);
            index++;
            Thread.sleep(1000);
            }
        }catch(IOException e){
            System.out.println(e);
        }finally{
            namae.close();
        }
    }
    public static void getTexts(short start,short end) throws IOException
    {
        Scanner link=new Scanner(new File("link"));
        
        short cx=start;
        while(cx<=end)
        {
            Util.saveFile(get(link.nextLine()), cx+"");
            System.out.println(cx);
            cx++;
        }
    }//Download all text file from list 'link'
    public static void init(String id,String XToken,String session)
    {
        post=new POST("https://ranobelib.me/admin/manga/"+id+"/chapter");
        post.setCookie("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
        post.setCookie("Content-Type","application/x-www-form-urlencoded");
//        post.setCookie("X-CSRF-TOKEN",token[0]);
        post.setCookie("X-Requested-With", "XMLHttpRequest");
        post.setCookie("X-XSRF-TOKEN", XToken);
        post.setCookie("Cookie", "mangalib_session="+session);
    }//Init POST
    public static void send(String token,int volume,int num,String name,String data,String mangaId)
    {
        post.sendData("_token="+token+"&volume="+volume+"&number="+num+"&scanlator_id=0&name="+name+"&content="+data+"&mangaId="+mangaId);
    }//send mes to POST
    public static String get(String urlString) throws IOException
    {
        StringBuilder sb=new StringBuilder();
        URLConnection url=new URL(urlString).openConnection();
        
        url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
        InputStreamReader in=new InputStreamReader(url.getInputStream(), Charset.forName("UTF8"));
        short cx=115;
        char inChar;
        while(cx--!=0)
        {
            while((inChar=(char)in.read())!='\n'){}
        }
        cx=2;
        while(cx--!=0)
        {
            while((inChar=(char)in.read())!='\n')
                sb.append(inChar);
        }
        in.close();
        String ret=sb.toString();
        ret=ret.replace("<br/>", "\n");
        return Util.deleteTag(Util.deleteTag(ret, "ins"), "script");
    }//Get text from current link
}