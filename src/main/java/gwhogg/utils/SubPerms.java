package gwhogg.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

// This differs from Heap's algorithm as it allows repetition of chars in the provided set
public class SubPerms {
    List<List<Character>> perms = new ArrayList<>();

    public List<List<Character>> getPerms() {
        return perms;
    }

    public void setPerms(List<List<Character>> perms) {
        this.perms = perms;
    }

    // Generate all possible strings of length k
    public void permute(char[] set, String prefix, int n, int k) {
        // Base case: k is 0,
        // print prefix
        if (k == 0) {
            perms.add(convertStringToCharList(prefix));
            return;
        }

        // One by one add all characters
        // from set and recursively
        // call for k equals to k-1
        for (int i = 0; i < n; ++i) {

            // Next character of input added
            String newPrefix = prefix + set[i];

            // k is decreased, because
            // we have added a new character
            permute(set, newPrefix,
                    n, k - 1);
        }
    }

    public static List<Character>
    convertStringToCharList(String str) {
        return new AbstractList<Character>() {

            @Override
            public Character get(int index) {
                return str.charAt(index);
            }

            @Override
            public int size() {
                return str.length();
            }
        };
    }
}

