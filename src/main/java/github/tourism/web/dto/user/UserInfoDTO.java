package github.tourism.web.dto.user;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDTO {
    private String email;
    private String category;
    private String roles;
}
