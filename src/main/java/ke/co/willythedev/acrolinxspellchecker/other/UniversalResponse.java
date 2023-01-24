package ke.co.willythedev.acrolinxspellchecker.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UniversalResponse {

    private int statusCode;
    private String message;
    private Object data;
    private Date timestamp;
}
