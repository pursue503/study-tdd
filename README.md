# TDD(Test Driven Development) 실습 프로젝트

## 👋️️️️️개요
간단한 커뮤니티를 제작하며 TDD 학습을 위한 레포지토리.

## ⚒️사용 기술

- Java 11
- SpringBoot
- Jpa
- QueryDSL
- H2


## ✨ 학습 목표

**브랜치 관리 전략**

많은 깃 브랜치 관리 전략 중 하나인 git-flow 를 도입하여 개발을 진행합니다.

<br>

**코드 리뷰 기반 개발**

하나의 기능은 하나의 pull_request 를 생성합니다.

해당 pull_request 에 대하여 다른 개발자가 리뷰를 하여 병합하는 습관을 연습합니다.

<br>


**페어 프로그래밍**

code with me 를 활용하여 어렵거나 힘든 기능을
의견을 주고 받으며 페어 프로그래밍을 진행합니다.




## 📋 프로젝트 기능 요구 사항

---



### ✏️회원
- 회원 가입
  - POST /members MemberController#signUp(@RequestBody MemberSignUpDTO)
- 회원정보 수정
  - PUT /members MemberController#updateMember(@RequestBody MemberInfoUpdateDTO)
- 비밀번호 수정
  - PATCH /members MemberController#updatePassword(@RequestBody MemberChangePasswordDTO)
- 회원탈퇴
  - DELETE /members MemberController#quitMember()
- 조회
  - GET /members MemberController#fetchMember()
- 로그인
  - POST /members/sign-in MemberController#signIn()
- 회원 아이디 찾기
  - GET /members/find-id MemberController#findId()
- 비밀번호 찾기
  - GET /members/find-pw MemberController#findPw()

### ✏️게시판

- 쓰기
  - POST /posts PostController#posts(@Valid @RequestBody PostSaveRequestDTO)
- 수정
  - PATCH /posts PostController#updatePosts(@Valid @RequestBody PostUpdateRequestDTO)
- 삭제
  - DELETE /posts/{id}/{postId} PostController#deletePost(@Valid @RequestBody PostDeleteRequestDTO)
- 조회(제목조회(페이징목록), 개별게시글조회)
  - 개별게시글조회
    - GET /posts/{id} PostController#posts/{id}(@PathVariable Long id)
  - 제목조회(페이징목록)
    - GET /posts/page={number} PostController#posts(@PathVariable Long page)

### ✏️댓글
- 쓰기
- 수정
- 삭제
- 조회
- 추천
- 조회수
- 대댓글

### ✏️게시판
- 파일(이미지)
- 업다운로드
- 삭제

## 프로젝트 기능 요구 사항
"*" : 변경이 필요한 요구사항입니다.

### ✏️회원
- [x] 닉네임, 비밀번호, 비밀번호 확인을 입력하기 #validateSignUpParam
- [x] 닉네임 검증: #validateNickname(String username)
  - [x] 최소 3자 이상
  - [x] 알파벳 대소문자(a~z, A~Z)
  - [x] 숫자(0~9)로 구성하기
- [x] 비밀번호 규칙검증: #validatePassword(String password)
  - [x] 최소 8자 이상
  - [x] 대문자 하나이상 포함
  - [x] 특수문자 하나이상 포함
  - [x] 숫자 하나이상 포함
- [x] 닉네임 중복 체크 #checkDuplicatedNickname(String nickname)
- [x] 로그인 검사 - JWT (필터)
  - [x] 토큰 디코딩(복호화) JwtUtil#decode(String token)
  - [x] 토큰 검증 JwtUtil#validateAccessToken(String token),
- [ ] 권한 설명 (일반유저, 관리자, 밴유저) (USER, ADMIN, BLOCK)
  - [ ] 일반유저
    - 회원 기본 기능(정보 수정, 개인정보 조회, 로그인, 회원 탈퇴)
    - 게시물(작성, 수정, 삭제, 조회)
    - 댓글 및 대댓글(작성, 수정, 삭제, 조회)
  - [ ] 관리자 (일반유저가 할 수 있는 기능을 전부 수행 가능)
    - 회원 관리(특정 회원 정보 조회, 회원 권한 수정)
    - 게시판 관리
      - 게시글 관리(특정 게시물 숨김/보임 처리)
      - 댓글 및 대댓글 관리(특정 게시물 댓글 및 대댓글 작성 비활성화/활성화 처리)
  - [ ] 밴유저
    - 일반유저 및 관리자의 기능을 모두 사용할 수 없음
    - 관리자에 의해서만 강등된 역할(Role)

### ✏️게시판
- [x] 게시물 제목은 1글자 이상 30글자 이하 허용
  - [x] 30글자가 넘어가는 경우 "게시글 제목 길이가 초과되었습니다."(400 Bad Request, *-1) 메세지를 보내주기
- [x] 게시물 내용은 1글자 이상 2000글자 이상 허용
  - [x] 게시판 내용이 비어있으면 "게시판 내용은 비어있을 수 없습니다."(400 Bad Request, *-2) 메시지를 보내주기
  - [x] 게시물 내용으로 작성될 수 있는 컨텐츠 타입은 이미지, 텍스트를 지원
  - [ ] *(특정 주기)마다 자동 임시 저장 (프론트엔드 서버에서 제어)
- [x] 게시판의 게시물 조회 순서는 시간 순서로 최근 작성된 것을 먼저 보여주기
  - [ ] Block 처리된 게시물 제외
- [x] 게시물 저장 기능 구현
  - [x] 게시물 본문 태그 함께 저장하기 (프론트엔드 서버에서 제어)
- [x] 게시물 조회 기능 구현
- [ ] 수정/삭제 시도시 본인이 작성한 게시글인지 서버에서 검증로직 추가하기
  - [ ] 본인이 작성하지 않은 게시글을 삭제 또는 수정 시도시 (403 FORBIDDEN, *-1) 메시지를 보내주기


### ✏️댓글
- [ ] 제일 최근 작성된 댓글을 맨 위에 띄우기
  - [ ] 삭제된 댓글에 대댓글이 존재할 경우 해당 댓글의 내용을 "댓글이 삭제되었습니다." 메세지로 변경해서 보내주기
- [ ] 댓글 내용이 비어서 요청이 들어오면 댓글 "내용을 입력해주세요."(400 Bad Request, *-4) 메시지를 보내주기
- [ ] 본인이 작성한 댓글인지 서버에서 검증로직 추가하기
  - [ ] 댓글 작성자 본인이 아닌 유저가 삭제 요청을 했을 경우 "댓글 작성자 본인만 삭제 가능합니다" (403 FORBIDDEN, *-2) 메시지를 보내주기
- [ ] 댓글 태그 함께 저장하기
- [ ] 기존 댓글의 내용을 수정했을 경우 새로 입력한 댓글 내용으로 전달하기
- [ ] 댓글 삭제 기능 구현
- [ ] 대댓글 삭제 기능 구현
---

## ⚔ Project Coding Convention && Resource Convention

### ✋ Class && Method Convention

- 하나의 클래스는 하나의 책임만 담당하게 만든다.
- else 예약어를 사용하지 않는다.
- 한줄에 점 하나만 찍는다 ( 체이닝 )
- 최대한 단위를 쪼개어 메소드를 만든다.
- 최대한 Lambda Stream 을 이용하기
- 구분자는 탭(스페이스 4칸)을 사용한다.


- 예약어(if, switch, for, while 등) 작성 후 스페이스로 한 칸 띄운다. 괄호를 닫은 후 다시 스페이스를 한 칸 띄운다.
- 필드변수는 모두 메서드 바로 아래에 함께 작성하여 모아둔다.

### ✋ class 작성 방식
- 필드 변수
- 생성자
- override 메소드
- public 메소드
- private 메소드
- 
<br> 

예시는 아래와 같다.

```java
public class Main {
    public static void main(String... args) {
        int n = 0;

        while (n-- > 0) {
            log.info(n);
        }
    }
}
```
- 클래스 / 메서드 추가시마다 javadoc 작성.
- javadoc은 아래와 같은 포맷을 가진다.

```java

/**
 * 애플리케이션 실행시 진입 지점으로 사용되는 클래스
 *
 * @author 작업자1
 * @author 작업자2
 *
 */
public class Main {

  /**
   * 0부터 사용자 입력값(정수)까지 더하고 해당 결과값을 출력합니다.
   *
   * @param args 사용자 입력값 (문자열 배열)
   * @author 작업자1
   */
  public static void main(String... args) {
    int result = sum(Integer.parseInt(args[1]));
    System.out.println(result);
  }

  /**
   * 0부터 n까지 더한 값을 반환합니다.
   *
   * @param n 사용자 입력값 (정수 변환)
   * @return 0부터 n까지의 합산값
   * @author 작업자1
   */
  private static int sum(int n) {
    int sum = 0;
    
    while (n-- > 0) {
      sum += n;
    }

    return sum;
  }

}

```
