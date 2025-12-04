package searchengine;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RankingTest {

    static InvertedIndex index;
    static Page pageA, pageB, pageC;
    static List<Page> allPages;
    static List<List<String>> termList;

    @BeforeAll
    static void setUp() {
        pageA = new Page(List.of(
                "*PAGE:http://example.com/A",
                "Title A",
                "dog", "dog", "laugh"));
        pageB = new Page(List.of(
                "*PAGE:http://example.com/B",
                "Title B",
                "king", "denmark", "dog"));
        pageC = new Page(List.of(
                "*PAGE:http://example.com/C",
                "Title C",
                "fun", "laugh"));

        allPages = Arrays.asList(pageA, pageB, pageC);
        index = new InvertedIndex(allPages);

        List<List<String>> termList = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("dog");
        list1.add("laugh");
        list2.add("king");
        list2.add("denmark");
        
        termList.add(list1);
        termList.add(list2);
    }

    @Test 
    void createsComplexScoringMethodWhenTrue() {

    Ranking ranker = new Ranking(index, true);
    assertTrue(ranker.getScoringMethod() instanceof TfIdfScoring); }

    @Test 
    void createsSimpleScoringMethodWhenFalse() {

    Ranking ranker = new Ranking(index, false);
    assertTrue(ranker.getScoringMethod() instanceof TfScoring); }

    @Test
    void sortsListByRank() {

        pageA.assignRankingScore(3.0);
        pageB.assignRankingScore(2.0);
        pageC.assignRankingScore(5.0);

        List<Page> correctOrderList = new ArrayList<>();
        correctOrderList.add(pageC);
        correctOrderList.add(pageA);
        correctOrderList.add(pageB);

        Ranking ranker = new Ranking(index, true);
        assertEquals(correctOrderList, ranker.sortListByRank(allPages));
    }

    @Test
    void clearsRankingScore() {

        pageA.assignRankingScore(3.0);
        pageB.assignRankingScore(2.0);
        pageC.assignRankingScore(5.0);

        Ranking ranker = new Ranking(index, false);

        ranker.clearRankingScore(allPages);

        assertEquals(0.0, pageA.getRankingScore());
        assertEquals(0.0, pageB.getRankingScore());
        assertEquals(0.0, pageC.getRankingScore());
    }

    @Test 
    void assignsRankingScoresCorrectly() {

        Ranking ranker = new Ranking(index, false);
        ranker.rankPages(allPages, termList);

        assertEquals(3.0, pageA.getRankingScore());
        assertEquals(2.0, pageB.getRankingScore());
        assertEquals(0.0, pageC.getRankingScore());
    }

    @Test 
    void returnsSortedPages() {
        Ranking ranker = new Ranking(index, false);
        List<Page> correctPageList = new ArrayList();
        correctPageList.add(pageA);
        correctPageList.add(pageB);
        correctPageList.add(pageC);

        assertEquals(correctPageList, ranker.getPageListSortedByranking(allPages, termList));}


}
