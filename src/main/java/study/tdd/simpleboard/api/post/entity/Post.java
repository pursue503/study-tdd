package study.tdd.simpleboard.api.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Convert(converter = YNToBooleanConverter.class)
    private Boolean blocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ToString.Exclude
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    @Builder
    public Post(Long postId, String postTitle, String postContent, String image, Member member) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.image = image;
        this.member = member;
        this.blocked = false;
    }

    public Post updatePost(Post wantToChange) {
        this.postTitle = wantToChange.getPostTitle();
        this.postContent = wantToChange.getPostContent();
        this.image = wantToChange.getImage();
        return this;
    }

    private static class YNToBooleanConverter implements AttributeConverter<Boolean, String> {

        /**
         * Boolean 값을 Y 또는 N 으로 변환
         *
         * @param attribute boolean 값
         * @return String true 인 경우 Y 또는 false 인 경우 N
         */
        @Override
        public String convertToDatabaseColumn(Boolean attribute) {
            return (attribute != null && attribute) ? "Y" : "N";
        }

        /**
         * Y 또는 N 을 Boolean 으로 변환
         * @param yn Y 또는 N
         * @return Boolean 대소문자 상관없이 Y 인 경우 true, N 인 경우 false
         */
        @Override
        public Boolean convertToEntityAttribute(String yn) {
            return "Y".equalsIgnoreCase(yn);
        }
    }
}
