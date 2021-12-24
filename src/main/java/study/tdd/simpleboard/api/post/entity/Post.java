package study.tdd.simpleboard.api.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tdd.simpleboard.api.member.entity.Member;

import javax.persistence.*;

/**
 * 게시물 엔티티
 *
 * @author Informix
 * @create 2021-12-23
 * @since 2.6.1 spring boot
 * @since 0.0.1 dev
 */
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
