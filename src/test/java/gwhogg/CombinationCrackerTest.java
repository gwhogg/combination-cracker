package gwhogg;

import gwhogg.models.Condition;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

public class CombinationCrackerTest {
    CombinationCracker combinationCracker = new CombinationCracker();

    @Test
    public void verifyKnownCombination() {
        Condition condition1 = new Condition(new char[]{'3', '5', '4', '8'}, 1, 0);
        Condition condition2 = new Condition(new char[]{'4', '6', '7', '1'}, 2, 1);
        Condition condition3 = new Condition(new char[]{'3', '7', '8', '1'}, 2, 0);
        Condition condition4 = new Condition(new char[]{'8', '3', '9', '7'}, 3, 0);
        Condition condition5 = new Condition(new char[]{'5', '1', '2', '4'}, 0, 0);
        Condition condition6 = new Condition(new char[]{'2', '9', '3', '4'}, 1, 0);
        Condition condition7 = new Condition(new char[]{'5', '1', '3', '6'}, 1, 1);

        List<Condition> conditions = new ArrayList<>(Arrays.asList(condition1, condition2, condition3, condition4, condition5, condition6, condition7));

        combinationCracker = new CombinationCracker();
        List<String> solutions = combinationCracker.solve(conditions);

        assertThat(solutions, hasItems("9 8 7 6"));
    }

    @Test
    public void verifySecondKnownCombination() {
        Condition condition1 = new Condition(new char[]{'1', '2', '3', '4'}, 1, 0);
        Condition condition2 = new Condition(new char[]{'7', '9', '8', '6'}, 0, 0);
        Condition condition3 = new Condition(new char[]{'3', '5', '1', '6'}, 1, 1);
        Condition condition4 = new Condition(new char[]{'1', '7', '9', '0'}, 2, 0);
        Condition condition5 = new Condition(new char[]{'5', '1', '3', '1'}, 2, 2);

        List<Condition> conditions = new ArrayList<>(Arrays.asList(condition1, condition2, condition3, condition4, condition5));

        combinationCracker = new CombinationCracker();
        List<String> solutions = combinationCracker.solve(conditions);

        assertThat(solutions, hasItems("0 1 1 1"));
    }

    @Test
    public void verifyThirdKnownCombination() {
        Condition condition1 = new Condition(new char[]{'0', '1', '1', '1'}, 1, 0);
        Condition condition2 = new Condition(new char[]{'7', '9', '8', '6'}, 2, 0);
        Condition condition3 = new Condition(new char[]{'7', '9', '3', '4'}, 0, 0);
        Condition condition4 = new Condition(new char[]{'2', '7', '6', '2'}, 2, 1);

        List<Condition> conditions = new ArrayList<>(Arrays.asList(condition1, condition2, condition3, condition4));

        combinationCracker = new CombinationCracker();
        List<String> solutions = combinationCracker.solve(conditions);

        assertThat(solutions, hasItems("2 6 0 8"));
    }

    @Test
    public void verifyFourthKnownCombination() {
        Condition condition1 = new Condition(new char[]{'1', '9', '6', '3'}, 1, 0);
        Condition condition2 = new Condition(new char[]{'6', '7', '9', '8'}, 0, 0);
        Condition condition3 = new Condition(new char[]{'1', '2', '3', '4'}, 3, 1);
        Condition condition4 = new Condition(new char[]{'9', '2', '4', '0'}, 3, 0);

        List<Condition> conditions = new ArrayList<>(Arrays.asList(condition1, condition2, condition3, condition4));

        combinationCracker = new CombinationCracker();
        List<String> solutions = combinationCracker.solve(conditions);

        assertThat(solutions, hasItems("2 0 1 4"));
    }

    @Test
    public void verifyFifthKnownCombination() {
        Condition condition1 = new Condition(new char[]{'1', '2', '3', '4'}, 1, 0);
        Condition condition2 = new Condition(new char[]{'2', '9', '8', '6'}, 1, 1);
        Condition condition3 = new Condition(new char[]{'7', '9', '8', '6'}, 0, 0);
        Condition condition4 = new Condition(new char[]{'1', '0', '9', '3'}, 1, 1);

        List<Condition> conditions = new ArrayList<>(Arrays.asList(condition1, condition2, condition3, condition4));

        combinationCracker = new CombinationCracker();
        List<String> solutions = combinationCracker.solve(conditions);

        assertThat(solutions, hasItems("2 0 2 0"));
    }
}
