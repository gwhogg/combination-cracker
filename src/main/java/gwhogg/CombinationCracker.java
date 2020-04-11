package gwhogg;

import gwhogg.models.Condition;
import gwhogg.utils.HeapsAlgorithm;
import gwhogg.utils.SubPerms;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// 4 positions with 10 digits equates to 10^4 different possibilities: 0000-9999
public class CombinationCracker {
    long startTime;

    List<Character> possibleDigits = "0123456789".chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toList());

    List<Character> impossibleDigits = new ArrayList<>();

    Set<List<Character>> possibleCombinations = new HashSet<>();

    public List<String> solve(List<Condition> conditions) {
        startTime = System.currentTimeMillis();

        // Throw away any digits we know aren't in the correct combination
        discardImpossibleDigits(conditions);

        // Identify condition which has the most correct digit(s)
        Condition bestCondition = conditions
                .stream()
                .max(Comparator.comparing(Condition::getCorrectDigits))
                .orElseThrow(NoSuchElementException::new);

        int numSwitches = 4 - bestCondition.getCorrectDigits();

        HeapsAlgorithm ha = new HeapsAlgorithm();
        char[] conditionDigits = bestCondition.getDigits();
        ha.permute(conditionDigits, conditionDigits.length);

        // Replace impossible digits from our best guess permutations with those in the possible set
        List<List<Character>> permutations = correctPermutations(ha.getPermutations());

        for (List<Character> perm : permutations) {
            createPossibleCombinations(perm, numSwitches);
        }

        System.out.println(String.format("Whittled down combinations to %s possibilities", possibleCombinations.size()));
        System.out.println("Testing these against the provided criteria...");

        return deduceCombinations(conditions);
    }

    private List<Condition> discardImpossibleDigits(List<Condition> conditions) {
        List<Condition> conditionsWithInvalidDigits = conditions.stream().filter(c -> c.getCorrectDigits() == 0).collect(Collectors.toList());
        impossibleDigits.addAll(conditionsWithInvalidDigits.stream().flatMap(c -> c.getDigitsList().stream()).collect(Collectors.toList()));
        possibleDigits.removeAll(impossibleDigits);
        conditions.removeAll(conditionsWithInvalidDigits);
        return conditions;
    }

    private List<String> deduceCombinations(List<Condition> conditions) {
        List<String> solutions = new ArrayList<>();

        for (List<Character> possibleCombination : possibleCombinations) {
            boolean conditionsMet = true;
            for (Condition condition : conditions) {
                if (!testCondition(possibleCombination, condition)) {
                    conditionsMet = false;
                    break;
                }
            }
            if (conditionsMet) {
                long endTime = System.currentTimeMillis();

                String solution = possibleCombination.stream().
                        map(Object::toString).
                        collect(Collectors.joining(" "));
                solutions.add(solution);

                long timeElapsed = endTime - startTime;

                System.out.println(String.format("Identified possible solution %s in %s milliseconds", solution, timeElapsed));
            }
        }

        return solutions;
    }

    private boolean testCondition(List<Character> combination, Condition condition) {
        int matchingDigits = countMatchingDigits(combination, condition.getDigitsList());
        int matchingPositions = countMatchingPositions(combination, condition.getDigitsList());
        return condition.getCorrectDigits() == matchingDigits && condition.getCorrectPositions() == matchingPositions;
    }

    private int countMatchingDigits(List<Character> combination, List<Character> conditionCombination) {
        int matchingDigits = 0;

        Map<Character, Long> combinationDigitCounts = combination.stream()
                .collect(Collectors.groupingBy(c -> c,
                        Collectors.counting()));

        Map<Character, Long> conditionDigitCounts = conditionCombination.stream()
                .collect(Collectors.groupingBy(c -> c,
                        Collectors.counting()));

        for (char c : combinationDigitCounts.keySet()) {
            Long conditionDigitCount = conditionDigitCounts.get(c);
            if (null != conditionDigitCount) {
                Long combinationDigitCount = combinationDigitCounts.get(c);
                matchingDigits += Math.min(combinationDigitCount, conditionDigitCount);
            }
        }

        return matchingDigits;
    }

    static int countMatchingPositions(List<Character> combination, List<Character> conditionCombination) {
        int matchingPositions = 0;

        for (int i = 0; i < combination.size(); i++) {
            for (int j = 0; j < conditionCombination.size(); j++) {
                if (combination.get(i) == conditionCombination.get(j) && (i == j)) {
                    matchingPositions++;
                }
            }
        }

        return matchingPositions;
    }

    private void createPossibleCombinations(List<Character> combination, int numSwitches) {
        switch (numSwitches) {
            case 1:
                for (char c : possibleDigits) {
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(c, combination.get(1), combination.get(2), combination.get(3))));
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), c, combination.get(2), combination.get(3))));
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), combination.get(1), c, combination.get(3))));
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), combination.get(1), combination.get(2), c)));
                }
                break;
            case 2:
                for (int i = 0; i < 100; i++) {
                    Character a;
                    Character b;

                    if (i < 10) {
                        a = '0';
                        b = (char) (i + '0');
                    } else {
                        a = (char) ((i / 10) + '0');
                        b = (char) ((i % 10) + '0');
                    }

                    if (impossibleDigits.contains(a) || impossibleDigits.contains(b)) {
                        continue;
                    }

                    // _ _ 2 3
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, b, combination.get(2), combination.get(3))));
                    // _ 1 _ 3
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, combination.get(1), b, combination.get(3))));
                    // _ 1 2 _
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, combination.get(1), combination.get(2), b)));
                    // 0 _ _ 3
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), a, b, combination.get(3))));
                    // 0 _ 2 _
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), a, combination.get(2), b)));
                    // 0 1 _ _
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), combination.get(1), a, b)));
                }
                break;
            case 3:
                for (int i = 0; i < 1000; i++) {
                    char a;
                    char b;
                    char c;

                    char[] chars = Integer.toString(i).toCharArray();

                    if (i < 10) {
                        a = '0';
                        b = '0';
                        c = chars[0];
                    } else if (i < 100) {
                        a = '0';
                        b = chars[0];
                        c = chars[1];
                    } else {
                        a = chars[0];
                        b = chars[1];
                        c = chars[2];
                    }

                    if (impossibleDigits.contains(a) || impossibleDigits.contains(b) || impossibleDigits.contains(c)) {
                        continue;
                    }

                    // 0 _ _ _
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(combination.get(0), a, b, c)));
                    // _ 1 _ _
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, combination.get(1), b, c)));
                    // - - 2 -
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, b, combination.get(2), c)));
                    // _ _ _ 3
                    possibleCombinations.add(new ArrayList<>(Arrays.asList(a, b, c, combination.get(3))));
                }
                break;
        }
    }

    private List<List<Character>> correctPermutations(List<List<Character>> perms) {
        List<List<Character>> updatedPermutations = new ArrayList<>();
        for (List<Character> perm : perms) {
            boolean corrected = false;
            if (permContainsInvalidDigits(perm)) {
                corrected = true;
                List<Integer> invalidIndices = new ArrayList<>();
                for (Character c : impossibleDigits) {
                    invalidIndices.addAll(
                            IntStream.range(0, perm.size())
                                    .filter(i -> c == perm.get(i))
                                    .boxed()
                                    .collect(Collectors.toList())
                    );
                }

                char[] possibleDigitsArray = convertPossibleDigitsList();
                SubPerms sp = new SubPerms();
                sp.permute(possibleDigitsArray, "", possibleDigitsArray.length, invalidIndices.size());
                List<List<Character>> validDigitPerms = sp.getPerms();
                for (List<Character> validDigitPerm : validDigitPerms) {
                    List<Character> updatedPermutation = new ArrayList<>(perm);
                    for (int i = 0; i < invalidIndices.size(); i++) {
                        updatedPermutation.set(invalidIndices.get(i), validDigitPerm.get(i));
                    }
                    updatedPermutations.add(updatedPermutation);
                }
            }
            // Only add original permutation if it doesn't contain any impossible digits
            if (!corrected) {
                updatedPermutations.add(perm);
            }
        }
        return updatedPermutations;
    }

    private char[] convertPossibleDigitsList() {
        char[] possibleDigitsArray = new char[possibleDigits.size()];
        for (int i = 0; i < possibleDigits.size(); i++) {
            possibleDigitsArray[i] = possibleDigits.get(i);
        }
        return possibleDigitsArray;
    }

    private boolean permContainsInvalidDigits(List<Character> perm) {
        for (Character c : perm) {
            if (impossibleDigits.contains(c)) {
                return true;
            }
        }
        return false;
    }
}
