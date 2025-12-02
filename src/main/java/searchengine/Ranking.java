package searchengine;

import java.util.List;
import java.util.Comparator;

public class Ranking {
    private ScoringMethod scoring;
    private InvertedIndex index;
    private int totalPageCount;

    public Ranking(InvertedIndex index, Boolean complexScoring) {
        this.index = index;
        totalPageCount = index.getTotalPageCount();
        if (complexScoring = true) {
            ScoringMethod scoring = new TfIdfScoring();
            this.scoring = scoring;
        } else {
            ScoringMethod scoring = new TfScoring();
            this.scoring = scoring;
        }
    }

    public List<Page> rankPages(List<Page> resultPages, List<List<String>> termList) {

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

        resultPages.sort(Comparator
            .comparing(Page::getRankingScore)
        );

        for(int i = 0; i< resultPages.size(); i++ ){
            resultPages.get(i).clearRankingScore();
        }

        return resultPages;
        

    }

}
