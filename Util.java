import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
    public static String readFile(String name)
    {
        FileReader in = null;
        try {
            in = new FileReader(new File(name));
            StringBuilder sb=new StringBuilder();
            char tempChar;
            while((tempChar=(char)in.read())!='\uffff')
                sb.append(tempChar);
            return sb.toString();
        } catch (FileNotFoundException ex) {
            System.out.println("FNF");
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try{
                in.close();
            } catch (IOException ex) {}
        }
        return null;
    }
    public static void saveFile(String data,String name)
    {
        File f=new File(name);
        FileWriter out=null;
        try
        {
            if(!f.exists())
            f.createNewFile();
            out=new FileWriter(f);
            out.write(data);
        }catch(IOException e){
            System.out.println(e);
        }finally{
            try{
                out.close();
            }catch(IOException e){}
        }
        
    }
    public static String deleteTag(String text,String[]tags)
    {
        String ret=text;
        for(String tag:tags)
            ret=deleteTag(ret, tag);
        return ret;
    }
    public static String deleteTag(String text, String tag)
    {
        Pattern pattern=Pattern.compile('<'+tag+".*</"+tag+">\\s?\n?");
        Matcher mat=pattern.matcher(text);
        return mat.replaceAll("");
    }
}