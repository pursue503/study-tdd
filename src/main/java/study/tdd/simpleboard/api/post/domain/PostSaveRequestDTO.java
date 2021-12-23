package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;

@Getter
public class PostSaveRequestDTO {

    @NotNull(message = "{post.title.not_null}")
    @NotEmpty(message = "{post.title.not_empty}")
    @Length(max = 30, message = "{post.title.max_length_exceeded}")
    private final String postTitle;

    @NotNull(message = "{post.content.not_null}")
    @NotEmpty(message = "{post.content.not_null}")
    @Length(max = 2000, message = "{post.content.not_null}")
    private final String postContent;

    @ConstructorProperties({"postTitle", "postContent"})
    public PostSaveRequestDTO(String postTitle, String postContent) {
        this.postTitle = postTitle == null ? null : postTitle.trim();
        this.postContent = postContent == null ? null : postContent.trim();
    }
}
