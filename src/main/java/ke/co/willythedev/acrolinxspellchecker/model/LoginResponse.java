package ke.co.willythedev.acrolinxspellchecker.model;

import lombok.*;

/**
 * LoginResponse data class.
 * Returned when a user logs in.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private String responseCode;
    private String authToken;

}
