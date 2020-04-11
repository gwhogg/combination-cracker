package gwhogg.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HeapsAlgorithm {
    List<List<Character>> permutations = new ArrayList<>();

    public List<List<Character>> getPermutations() {
        return permutations;
    }

    public void setPermutations(List<List<Character>> permutations) {
        this.permutations = permutations;
    }

    private void swap(char[] v, int i, int j) {
        char t = v[i];
        v[i] = v[j];
        v[j] = t;
    }

    public void permute(char[] v, int n) {
        if (n == 1) {
            List<Character> characterList = IntStream
                    .range(0, v.length)
                    .mapToObj(i -> v[i])
                    .collect(Collectors.toList());

            permutations.add(characterList);
        } else {
            for (int i = 0; i < n; i++) {
                permute(v, n - 1);
                if (n % 2 == 1) {
                    swap(v, 0, n - 1);
                } else {
                    swap(v, i, n - 1);
                }
            }
        }
    }
}
