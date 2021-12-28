package study.tdd.simpleboard.api.post.domain;

import lombok.Getter;
import org.springframework.data.domain.Page;
import study.tdd.simpleboard.api.post.domain.PostPageDTO;
import study.tdd.simpleboard.api.post.entity.Post;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageResponseDTO {

    private final int selectedPageNumber;
    private final List<Post> selectedPosts;
    private final int totalPages;

    public PageResponseDTO(int selectedPageNumber, Page<Post> selectedPosts) {
        this.selectedPageNumber = selectedPageNumber;
        this.selectedPosts = selectedPosts.getContent();
        this.totalPages = selectedPosts.getTotalPages();
    }
}
