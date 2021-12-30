package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import study.tdd.simpleboard.api.post.domain.entity.Post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

/**
 * 게시물 저장 시도시 클라이언트 측에서 전달한 값을 검증하고
 * 유효한 값을 저장하는 용도로 사용됩니다.
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
@Getter
public class PostPatchRequestDTO {

    @NotNull(message = "게시물을 수정하려면 게시물 번호를 함께 보내주셔야 합니다.")
    private final Long postId;

    @NotNull(message = "게시물 제목이 반드시 전달되어야 합니다.")
    @NotEmpty(message = "게시물 제목이 비어 있으면 안됩니다.")
    @Length(max = 30, message = "게시물 제목은 30 글자를 초과할 수 없습니다.")
    private final String postTitle;

    @NotNull(message = "게시물 내용이 반드시 전달되어야 합니다.")
    @NotEmpty(message = "게시물 내용이 비어 있으면 안됩니다.")
    @Length(max = 2000, message = "게시물 내용은 2000 글자를 초과할 수 없습니다.")
    private final String postContent;

    private final String image;

    @ConstructorProperties({ "postId", "postTitle", "postContent", "image"})
    public PostPatchRequestDTO(Long postId, String postTitle, String postContent, String image) {
        this.postId = postId;
        this.postTitle = postTitle == null ? null : postTitle.trim();
        this.postContent = postContent == null ? null : postContent.trim();
        this.image = image;
    }

    public Post toEntity() {
        return Post.builder()
                .postId(getPostId())
                .postTitle(getPostTitle())
                .postContent(getPostContent())
                .image(getImage())
                .build();
    }
}
