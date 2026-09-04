package no.jlwcrews;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AnagramFinder {

    public List<List<String>> findAnagrams(List<String> words) {
        var anagrams = new HashMap<String, List<String>>();

        words.forEach( word -> {
            var key = alphabetizeWord(word.trim());
            var existing = anagrams.get(key);
            if (existing == null) {
                anagrams.put(key, List.of(word.trim()));
            } else {
                var newList = new ArrayList<>(existing);
                newList.add(word.trim());
               anagrams.put(key, newList);
            }
        });
        return anagrams.values().stream().toList();
    }

    public String alphabetizeWord(String word) {
        char [] letters = word.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
