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
     *
     * @param index an InvertedIndex object which is added to the field index.
     *                       The method getTotalPageCount() is used on the index and the result is added to the field totalPageCount.
     * @param complexScoring which is a Boolean, if it is set to false,
     *                       it creates an object of the simpler type of ScoringMethod TFscoring.
     *                       If it set to true it creates an object of the more complex ScoringMethod TfIdfScoring.
     *                       The chosen SoringMethod is added to the field scoring.
     */
    public Ranking(InvertedIndex index, Boolean complexScoring) {
        this.index = index;
        totalPageCount = index.getTotalPageCount();
        if (complexScoring == true) {
            scoring = new TfIdfScoring();
        } else {
            scoring = new TfScoring();
        }
    }

    /**
     * The getPageListSortedByRanking method ranks a list of pages,
     * sorts it based on the ranking, and finally clears the ranking score for the pages.
     *
     * @param resultPages the pages to sort by ranking
     * @param termList the OR-groups of search terms to base the ranking socre of for each page
     * @return a list of pages sorted by the rankingscore, high to low.
     */
    public List<Page> getPageListSortedByRanking(List<Page> resultPages, List<List<String>> termList) {

        rankPages(resultPages, termList);
        List<Page> listSortedByRank = sortListByRank(resultPages);
        clearRankingScore(resultPages);

        return listSortedByRank;
    }

    /**
     * The rankPages method ranks each page according to the ranking score of the OR-group
     * If not all words in an OR-group is present in a page, no ranking is added for the OR-group.
     * @param resultPages the pages to rank
     * @param termList the OR-groups of search terms to base the ranking socre of for each page
     */
    public void rankPages(List<Page> resultPages, List<List<String>> termList) {

        for (Page pageResult : resultPages) {
            for (List<String> wordsInORGroup : termList) {
                double scoreForORGroup = 0;

                for (String searchTerm : wordsInORGroup) {
                    double score = scoring.scoreSingleWord(searchTerm, pageResult, this.index, this.totalPageCount);
                    if (score > 0) {
                        scoreForORGroup += score;
                    } else {
                        scoreForORGroup = 0;
                        break;
                    }
                }

                if (scoreForORGroup > 0) {
                    pageResult.assignRankingScoreIfGreaterThanCurrent(scoreForORGroup);
                }

            }

        }

    }

    /**
     * The method sortListByRank takes:
     *
     * @param resultPages It sorts the pages by rankingscore and then reverses that
     * @return List<Page> sorted by rankingScore from high to low.
     */
    public List<Page> sortListByRank(List<Page> resultPages) {
        resultPages.sort(Comparator
                .comparing(Page::getRankingScore));
        Collections.reverse(resultPages);
        return resultPages;
    }

    /**
     * The method clearRankingScore takes a list of pages,
     * and clears the rankingScore for all pages in the list, by setting it to 0.
     *
     * @param resultPages the list of pages to clear the ranking score of.
     */
    public void clearRankingScore(List<Page> resultPages) {
        for (Page resultPage : resultPages) {
            resultPage.clearRankingScore();
        }
    }

    public ScoringMethod getScoringMethod() {
        return scoring;
    }

}
