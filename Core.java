
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Core
{
    public static void main(String args[]) throws IOException
    {
        buildList("https://ranobes.com/chapters/warlock-of-the-magus-world/8863-glava-750-ozero-polumesjaca.html", 100);
//        int cx=737;
//        ArrayList<String>a=getLink();
//        for(String b:a)
//        {
//            saveText(get(b), cx+"");
//            cx++;
//        }
    }
    public static void buildList(String startUrl,int count) throws IOException
    {
        File f=new File("url");
        if(!f.exists())
            f.createNewFile();
        FileWriter out=new FileWriter(f);
        String curUrl=startUrl;
        out.write(curUrl+'\n');
        while(count-->0)
        {
            curUrl=findNext(curUrl);
            out.write(curUrl+'\n');
            System.out.println(curUrl);
        }
        out.close();
    }
    public static String findNext(String urlString) throws IOException
    {
        StringBuilder sb=new StringBuilder();
        URLConnection url=new URL(urlString).openConnection();
        url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
        InputStreamReader in=new InputStreamReader(url.getInputStream(), Charset.forName("UTF8"));
        char[]src="Оглавление".toCharArray();
        short cx=0;
        char inChar;
        while((inChar=(char) in.read())!='\uffff')
        {
            if(inChar==src[cx++])
            {
                if(cx==src.length)
                    break;
            }else{
                cx=0;
            }
        }
        in.skip(13);
        while((inChar=(char)in.read())!='\"')
            sb.append(inChar);
        in.close();
        return sb.toString();
    }
    public static ArrayList<String> getLink() throws FileNotFoundException, IOException
    {
        ArrayList<String> list=new ArrayList<>();
        FileReader in=new FileReader(new File("link"));
        StringBuilder sb=new StringBuilder();
        char inChar;
        sb=new StringBuilder();
        while((inChar=(char)in.read())!='\uffff')
        {
            if(inChar!='\n')
                sb.append(inChar);
            else
            {
                list.add(sb.toString());
                sb=new StringBuilder();
            }
        }
        in.close();
        return list;
    }
    public static void saveText(String text,String name) throws IOException
    {
        File f=new File("dat\\"+name+".txt");
        if(!f.exists())
            f.createNewFile();
        FileWriter out=new FileWriter(f);
        out.write(text);
        out.close();
    }
    public static String get(String urlString) throws IOException
    {
        StringBuilder sb=new StringBuilder();
        URLConnection url=new URL(urlString).openConnection();
        
        url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
        InputStreamReader in=new InputStreamReader(url.getInputStream(), Charset.forName("UTF8"));
        short cx=117;
        char inChar;
        while(cx--!=0)
        {
            while((inChar=(char)in.read())!='\n'){}
        }
        in.skip(69);
        cx=2;
        while(cx--!=0)
        {
            while((inChar=(char)in.read())!='\n')
                sb.append(inChar);
        }
        in.close();
        String ret=sb.toString();
        ret=ret.replace("<p>", "");
        ret=ret.replace("</p>", "");
        ret=ret.replace("<br/>", "\n");
        return ret;
    }
}