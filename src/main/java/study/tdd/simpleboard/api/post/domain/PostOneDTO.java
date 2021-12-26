package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.tdd.simpleboard.api.post.entity.Post;

@Getter
@RequiredArgsConstructor
public class PostOneDTO {
    private final Long postId;
    private final String postTitle;
    private final String postContent;
    private final String nickname;
    private final String image;

    public PostOneDTO(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.nickname = post.getMember().getNickname();
        this.image = post.getImage();
    }
}
