import java.util.ArrayList;

public class WordList {

    private ArrayList<WordCount> list = new ArrayList<WordCount>();
    private ArrayList<String> possibleW = new ArrayList<String>();
    
    public WordList(String filename) {
        WordGetter wg = new WordGetter(filename);
        //take all words on the file and add them to the arraylist (in sorted order)
        String s = wg.getNextWord();
        while (s != null) {
            sortedAdding(s);
            s = wg.getNextWord();            
        }
    }
    
    private void sortedAdding(String s) {
        
        WordCount wc = new WordCount(s, 0);
        for (int i = 0; i < list.size(); i++) {
            if (s.compareTo(list.get(i).word) < 0) {
                wc.count++;
                list.add(i, wc);
                return;
            }
            else if (s.compareTo(list.get(i).word) == 0) {
                list.get(i).count++;
                return;
            }
        }  
        list.add(wc);
        wc.count++;
    }
    
    public static void test() {
        WordList wg = new WordList("medium.txt");
        //wg.printAll();
        wg.spellCheck("heytt");
    }
    
    public void printAll() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).word + " " + list.get(i).count);
        }
    }
    
    public int indexOf(String target) {
        boolean correct = false;
        int start = 0;
        int end = list.size();
        int finalIndex = -1;
        
        while (start != end && !correct) {
            int mid = start + (end - start) / 2;
            if (list.get(mid).word.equals(target)) {
                correct = true;
                finalIndex = mid;
            }
            else if (list.get(mid).word.compareTo(target) < 0) {
                start = mid + 1;
            }
            else {
                end = mid;
            }
        }
               
        return finalIndex;

    }    
    
    public ArrayList<String> collectMistakes(String s) {       
        
        ArrayList<String> oneM = new ArrayList<String>();
        
        //remove one letter (cycle through length of string)
        for (int i = 0; i < s.length(); i++) { 
            String newS = s.substring(0, i) + s.substring(i + 1);
            oneM.add(newS);
        }
        
        //add one letter (cycle through string length and letters)
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char addedChar = (char) ('a' + j);
                String newS = s.substring (0, i) + addedChar + s.substring(i);
                oneM.add(newS);
            }
        }
        
        //swap two adjacent letters
        for (int i = 0; i < s.length() - 1; i++) {
            int j = i + 1;
            if (j < s.length()) {
                String newS = s.substring(0, i) + s.substring(j, j + 1) + s.substring(i, i + 1) + s.substring(j + 1);
                oneM.add(newS);
            }
            else if (j == s.length()) {
                String newS = s.substring(0, i) + s.substring(j, j + 1) + s.substring(i, i + 1);
                oneM.add(newS);
            }
        }        
        
        //mistyped a letter
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char replaceChar = (char) ('a' + j); 
                String newS = s.substring(0, i) + replaceChar + s.substring(i + 1);
                oneM.add(newS);
            }
        }
        
        return oneM;
    }
    
    public void collectMistakesNoReturn(String s) {       
        
        //remove one letter (cycle through length of string)
        for (int i = 0; i < s.length(); i++) { 
            String newS = s.substring(0, i) + s.substring(i + 1);
            possibleW.add(newS);
        }
        
        //add one letter (cycle through string length and letters)
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char addedChar = (char) ('a' + j);
                String newS = s.substring (0, i) + addedChar + s.substring(i);
                possibleW.add(newS);
            }
        }
        
        //swap two adjacent letters
        for (int i = 0; i < s.length() - 1; i++) {
            int j = i + 1;
            if (j < s.length()) {
                String newS = s.substring(0, i) + s.substring(j, j + 1) + s.substring(i, i + 1) + s.substring(j + 1);
                possibleW.add(newS);
            }
            else if (j == s.length()) {
                String newS = s.substring(0, i) + s.substring(j, j + 1) + s.substring(i, i + 1);
                possibleW.add(newS);
            }
        }        
        
        //mistyped a letter
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < 26; j++) {
                char replaceChar = (char) ('a' + j); 
                String newS = s.substring(0, i) + replaceChar + s.substring(i + 1);
                possibleW.add(newS);
            }
        }
    }    
    
    public ArrayList<String> spellCheck(String s) {
        s = s.toLowerCase();
        
        for (int i = 0; i < list.size(); i++) {
            if (s.equals(list.get(i).word)) {
                System.out.println(s);
                ArrayList<String> word = new ArrayList<String>();
                word.add(s);
                return word;
            }
        }
        
        ArrayList<String> sCloseW = collectMistakes(s);
        int largestCount = 0;
        ArrayList<String> closestMatch = new ArrayList<String>();
        
        for (int i = 0; i < sCloseW.size(); i++) {
            if (indexOf(sCloseW.get(i)) != -1 && list.get(indexOf(sCloseW.get(i))).count > largestCount) {
                closestMatch.clear();
                closestMatch.add(list.get(indexOf(sCloseW.get(i))).word);
                largestCount = list.get(indexOf(sCloseW.get(i))).count;
            }
            else if (indexOf(sCloseW.get(i)) != -1 && list.get(indexOf(sCloseW.get(i))).count == largestCount) {
                boolean found = false;
                for (int j = 0; j < closestMatch.size(); j++) {
                    if ((closestMatch.get(j)).equals(list.get(indexOf(sCloseW.get(i))).word)) {
                        found = true;
                        break;
                    }                    
                }
                if (!found) {
                    closestMatch.add(list.get(indexOf(sCloseW.get(i))).word);
                }
                
            }
        }            
        
        if (closestMatch.isEmpty()) {
            possibleW.addAll(sCloseW);
            for (int i = 0; i < sCloseW.size(); i++) {
                collectMistakesNoReturn(sCloseW.get(i));
            }          
            
            for (int i = 0; i < possibleW.size(); i++) {
                if (indexOf(possibleW.get(i)) != -1 && list.get(indexOf(possibleW.get(i))).count > largestCount) {
                    closestMatch.clear();
                    closestMatch.add(list.get(indexOf(possibleW.get(i))).word);
                    largestCount = list.get(indexOf(possibleW.get(i))).count;
                }
                else if (indexOf(possibleW.get(i)) != -1 && list.get(indexOf(possibleW.get(i))).count == largestCount) {
                    boolean found = false;
                    for (int j = 0; j < closestMatch.size(); j++) {
                        if ((closestMatch.get(j)).equals(list.get(indexOf(possibleW.get(i))).word)) {
                            found = true;
                            break;
                        }                    
                    }
                    if (!found) {
                        closestMatch.add(list.get(indexOf(possibleW.get(i))).word);
                    }
                
                }
            }
        }
        
        for (int i = 0; i < closestMatch.size(); i++) {
            System.out.println("did you mean " + closestMatch.get(i) + " " + largestCount);            
        }
        
        if (!closestMatch.isEmpty()) {
            return closestMatch;
        }
        
        System.out.println("this word isn't on the list");
        ArrayList<String> message = new ArrayList<String>();
        message.add("this word isn't on the list");
        return message;
    }
}   
    