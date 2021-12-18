package study.tdd.simpleboard.api.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tdd.simpleboard.api.member.entity.Member;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String boardtitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
