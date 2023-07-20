import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String [] args)
    {
        List<String> words = Arrays.asList("hi","Neata","faci","stiu","Gibberish");

        // Produce a single String that is the result of concatenating the uppercase versions of all of the Strings.
        // Use a single reduce operation, without using map.
        String P6= words.stream().reduce("",(s1,s2)->s1.toUpperCase()+s2.toUpperCase());
        System.out.println(P6);

        //ex7
        // Produce a single String that is the result of concatenating the uppercase versions of all of the Strings.
        // Use a single reduce operation, without using map.
        String words2= words.stream().map(String::toUpperCase).reduce("",(s1,s2)->s1+s2);
        System.out.println(words2);

        //ex8
        //Produce a String that is all the words concatenated together, but with commas in between.
        // E.g., the result should be "hi,hello,...". Note that there is no comma at the beginning,
        // before “hi”, and also no comma at the end, after the last word. Major hint:
        // there are two versions of reduce discussed in the notes.
        String words3= words.stream().map(String::toUpperCase).reduce("",(s1,s2)->s1+","+s2);
        System.out.println(words3);
    }
}
