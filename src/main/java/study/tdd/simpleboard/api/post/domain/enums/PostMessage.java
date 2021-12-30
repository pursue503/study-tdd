package study.tdd.simpleboard.api.post.domain.enums;

import lombok.Getter;
import study.tdd.simpleboard.api.common.SuccessMessage;

@Getter
public enum PostMessage implements SuccessMessage {
    SAVE_POST_SUCCESS("게시물이 잘 저장되었습니다."),
    FIND_POST_ONE_SUCCESS("게시물이 잘 조회되었습니다."),
    FIND_POST_PAGE_SUCCESS("게시물 목록이 잘 조회되었습니다."),
    UPDATE_POST_SUCCESS("게시물이 수정되었습니다."),
    DELETE_POST_SUCCESS("게시물이 삭제되었습니다.");

    private final String successMsg;

    PostMessage(String successMsg) {
        this.successMsg = successMsg;
    }
}
