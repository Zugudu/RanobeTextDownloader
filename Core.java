import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

public class Core
{
    public static void main(String args[]) throws IOException
    {
        System.out.println(get("https://ranobes.com/chapters/warlock-of-the-magus-world/8902-glava-789-rozhdenie.html"));
        
//        int cx=978;
//        ArrayList<String>a=getLink();
//        for(String b:a)
//        {
//            saveText(get(b), cx+"");
//            cx++;
//        }
    }
    public static void buildName() throws IOException
    {
        File f=new File("names");
        if(!f.exists())
            f.createNewFile();
        ArrayList<String>url=(ArrayList<String>) Files.readAllLines(new File("url").toPath());
        FileWriter out=new FileWriter(f);
        int cx=0;
        while(cx<url.size())
        {
            String a=findName(url.get(cx++))+'\n';
            System.out.print(a);
            out.write(a);
        }
        out.close();
    }
    public static String findName(String urlString) throws IOException
    {
        StringBuilder sb=new StringBuilder();
        URLConnection url=new URL(urlString).openConnection();
        url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)");
        InputStreamReader in=new InputStreamReader(url.getInputStream(), Charset.forName("UTF8"));
        in.skip(74);
        char[]src="Оглавление".toCharArray();
        short cx=0;
        char inChar;
        while((inChar=(char) in.read())!=':'){}
        in.read();
        while((inChar=(char)in.read())!='|')
            sb.append(inChar);
        in.close();
        return sb.toString();
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
//        ret=ret.replace("<p>", "");
//        ret=ret.replace("</p>", "");
        ret=ret.replace("<br/>", "\n");
//        sb=new StringBuilder();
//        int i1=0,i2=0;
//        i1=ret.indexOf("<");
//        i2=ret.lastIndexOf(">");
//        if(i1!=-1&&i2!=-1)
//        {
//            i2++;
//            sb.append(ret.substring(0, i1));
//            sb.append(ret.substring(i2, ret.length()));
//            return sb.toString();
//        }else{
//            System.out.println(ret);
//            return ret;
//        }
        return ret;
    }
}