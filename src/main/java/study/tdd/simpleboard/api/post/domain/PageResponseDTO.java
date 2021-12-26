package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import org.springframework.data.domain.Page;
import study.tdd.simpleboard.api.post.domain.PostPageDTO;
import study.tdd.simpleboard.api.post.entity.Post;

import java.util.List;

@Getter
public class PageResponseDTO {

    private final int selectedPageNumber;
    private final Page<Post> selectedPosts;

    public PageResponseDTO(int selectedPageNumber, Page<Post> selectedPosts) {
        this.selectedPageNumber = selectedPageNumber;
        this.selectedPosts = selectedPosts;
    }
}
