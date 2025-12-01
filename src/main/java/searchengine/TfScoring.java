package searchengine;

import java.util.List;

//TF: term frequency

public class TfScoring implements ScoringMethod {

    @Override
    public double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount) {
        List<String> wordsOnPage = page.getWords();
        double count = 0;
        for(String w : wordsOnPage){
            if(w.equals(word)){
                count++;
            }
        }
        return count;
    }
    
}
