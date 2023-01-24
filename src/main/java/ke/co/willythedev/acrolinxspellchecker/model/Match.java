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
public class Match{
    private String surface;
    private int beginOffset;
    private int endOffset;
    private List<String> replacement;
}
