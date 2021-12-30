package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.tdd.simpleboard.api.post.domain.entity.Post;

@Getter
@RequiredArgsConstructor
public class UpdatedPostDTO {
    private final String postTitle;
    private final String postContent;
    private final String image;

    public UpdatedPostDTO(Post post) {
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.image = post.getImage();
    }
}
