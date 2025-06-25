package com.miray.BitirmeProjesiDeneme;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import zemberek.morphology.TurkishMorphology;
import zemberek.normalization.TurkishSpellChecker;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;


import zemberek.morphology.lexicon.RootLexicon;

import java.util.*;

@Service
public class SpellCheckService {

    private TurkishMorphology morphology;
    private TurkishSpellChecker spellChecker;


    @PostConstruct
    public void init() {
        try {
            RootLexicon lexicon = RootLexicon.fromResources("zemberek-data/master-dictionary.dict");

            morphology = TurkishMorphology.builder()
                    .setLexicon(lexicon)
                    .build();

            spellChecker = new TurkishSpellChecker(morphology);
        } catch (Exception e) {
            morphology = null;
            spellChecker = null;
            System.err.println("Zemberek başlatılamadı: " + e.getMessage());
        }
    }


    public List<Map<String, Object>> suggestWordsForText(String text) {
        if (spellChecker == null) {
            throw new IllegalStateException("SpellChecker başlatılamadı.");
        }

        List<Map<String, Object>> result = new ArrayList<>();
        String[] words = text.split("\\s+");

        for (int i = 0; i < words.length; i++) {
            String word = words[i].replaceAll("[^\\p{L}]", "");
            List<String> suggestions = spellChecker.suggestForWord(word);

            Map<String, Object> wordInfo = new LinkedHashMap<>();
            wordInfo.put("kelime_indexi", i);
            wordInfo.put("kelime", word);
            wordInfo.put("onerilen_kelimeler", suggestions);

            result.add(wordInfo);
        }

        return result;
    }
}
