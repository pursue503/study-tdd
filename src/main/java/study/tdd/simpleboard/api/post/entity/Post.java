package study.tdd.simpleboard.api.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tdd.simpleboard.api.member.entity.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String postTitle;

    private String postContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(Long postId, String postTitle, String postContent, Member member) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.member = member;
    }
}
