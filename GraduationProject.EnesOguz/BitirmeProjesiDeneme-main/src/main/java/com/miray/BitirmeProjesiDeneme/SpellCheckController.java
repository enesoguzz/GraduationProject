package com.miray.BitirmeProjesiDeneme;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spell")
public class SpellCheckController {

    private final SpellCheckService spellCheckService;

    public SpellCheckController(SpellCheckService spellCheckService) {
        this.spellCheckService = spellCheckService;
    }

    @PostMapping
    public List<Map<String, Object>> getSuggestions(@RequestBody SpellRequest request) {
        return spellCheckService.suggestWordsForText(request.getText());
    }

}


