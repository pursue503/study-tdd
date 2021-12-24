package study.tdd.simpleboard.api.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String postTitle;

    private String postContent;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Post(String postTitle, String postContent, String image, Member member) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.image = image;
        this.member = member;
    }
}
