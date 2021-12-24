package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

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
public class PostSaveRequestDTO {

    @NotNull(message = "게시물 제목이 반드시 전달되어야 합니다.")
    @NotEmpty(message = "게시물 제목이 비어 있으면 안됩니다.")
    @Length(max = 30, message = "게시물 제목은 30 글자를 초과할 수 없습니다.")
    private final String postTitle;

    @NotNull(message = "게시물 내용이 반드시 전달되어야 합니다.")
    @NotEmpty(message = "게시물 내용이 비어 있으면 안됩니다.")
    @Length(max = 2000, message = "게시물 내용은 2000 글자를 초과할 수 없습니다.")
    private final String postContent;

    @ConstructorProperties({"postTitle", "postContent"})
    public PostSaveRequestDTO(String postTitle, String postContent) {
        this.postTitle = postTitle == null ? null : postTitle.trim();
        this.postContent = postContent == null ? null : postContent.trim();
    }
}
