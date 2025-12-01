package searchengine;

public interface ScoringMethod {

double scoreSingleWord(String word, Page page, InvertedIndex index, int totalPageCount);

}
