package searchengine;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

/**
 * The Ranking Class implements one of two types of scoring depending on what
 * the user chooses.
 * It is given a List<Page> that it ranks. It sorts the list after ranking,
 * so the highest ranked page comes first and so forth before.
 * Before finally returning the List<Page> sorted by rank,
 * it clears the ranking from all the pages that where rank to be ready for the
 * next search.
 */
public class Ranking {
    private ScoringMethod scoring;
    private InvertedIndex index;
    private int totalPageCount;

    /**
     * Creates a new Ranking object.
     * @param index an InvertedIndex object which is added to the field index.
     * The method getTotalPagecount() is used on the index and the result is added to the field totalPagecould.
     * @param complexScoring which is a Boolean, if it is set to false,
     * it creates an object of the simpler type of ScoringMethod TFsoring.
     * If it set to true et creates an object of the more complex ScoringMethod TfIdfScoring.
     * The chosen SoringMethod is added to the field scoring. 
     */
    public Ranking(InvertedIndex index, Boolean complexScoring) {
        this.index = index;
        totalPageCount = index.getTotalPageCount();
        if (complexScoring == true) {
            ScoringMethod scoring = new TfIdfScoring();
            this.scoring = scoring;
        } else {

            ScoringMethod scoring = new TfScoring();
            this.scoring = scoring;
        }
    }

    /**
     * The getPageListSortedByranking takes:
     * @param resultPage
     * @param termlist
     * It applies the methods rankPages(), sortListByRank(), and clearRankingScore(),
     * @return a list of pages sorted by the rankingscore, high to low.
     */
    public List<Page> getPageListSortedByranking(List<Page> resultPages, List<List<String>> termList) {

        rankPages(resultPages, termList);
        List<Page> listSortedByRank = sortListByRank(resultPages);
        clearRankingScore(resultPages);

        return listSortedByRank;
    }

     /**
     * The rankPages takes:
     * @param resultPage
     * @param termList
     * It rank each page according to the ranking score of the OR-group
     * If not all words in an OR-group is present in at page, no ranking is added for the OR-group.
     */
    public void rankPages(List<Page> resultPages, List<List<String>> termList) {

        for (int currentPage = 0; currentPage < resultPages.size(); currentPage++) {
            Page pageResult = resultPages.get(currentPage);

            for (int currentList = 0; currentList < termList.size(); currentList++) {
                List<String> listOfORGroup = termList.get(currentList);
                double scoreForORGroup = 0;

                for (int currentWord = 0; currentWord < listOfORGroup.size(); currentWord++) {
                    String searchTerm = listOfORGroup.get(currentWord);
                    double score = scoring.scoreSingleWord(searchTerm, pageResult, this.index, this.totalPageCount);
                    if (score > 0) {
                        scoreForORGroup += score;
                    } else {
                        scoreForORGroup = 0;
                        break;
                    }

                }

                if (scoreForORGroup > 0) {
                    pageResult.assignRankingScore(scoreForORGroup);
                }

            }

        }

    }

     /**
     * The mehod sortListByRank takes:
     * @param resultPages 
     * It sort the pages by rankingscore and then reverse that
     * @reurn List<Page> sorted by rankingScore from high to low.
     */
    public List<Page> sortListByRank(List<Page> resultPages) {
        resultPages.sort(Comparator
                .comparing(Page::getRankingScore));
        Collections.reverse(resultPages);
        return resultPages;
    }

     /**
     * The method clearRankingScore takes: 
     * @param resultpage
     * It clears the rankingScore for all pages in the list, by setting it to 0.
     */
    public void clearRankingScore(List<Page> resultPages) {
        for (int i = 0; i < resultPages.size(); i++) {
            resultPages.get(i).clearRankingScore();
        }
    }

    public ScoringMethod getScoringMethod() {

        return scoring;
    }

}
