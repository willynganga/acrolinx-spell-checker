package ke.co.willythedev.acrolinxspellchecker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SpellCheckResponse {
    private String id;
    private Info info;
    private List<Issue> issues;
}


