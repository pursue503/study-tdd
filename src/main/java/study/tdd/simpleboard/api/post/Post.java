package study.tdd.simpleboard.api.post;

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

    private String posttitle;

    private String postContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
