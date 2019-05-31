import java.util.Scanner;
import java.io.File;


public class WordGetter
{
    private Scanner s;
    
    //Create a word getter object that accesses the file specified by filename
    public WordGetter(String filename){
        try {
            s = new Scanner(new File(filename));
        }
        catch (Exception e) {
            System.out.println("ERROR:");
            System.out.println(e);
            return;
        }
    }
    
    //return the next word in the file, or null if we have reached the last word
    public String getNextWord(){
        if (!s.hasNext()){
            return null;
        }
        String str = s.next().toLowerCase().replaceAll( "[^a-zA-Z\\s\\-]", "" );
        if (!str.equals("")){
            return str;
        }
        return getNextWord();
    }
}
