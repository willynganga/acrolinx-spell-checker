package ke.co.willythedev.acrolinxspellchecker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Info {
    private int words;
    private String time;
}
