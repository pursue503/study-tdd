package study.tdd.simpleboard.api.post.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class PostPageDTO {
    private final String nickname;
    private final Long postId;
    private final String postTitle;
}
