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

### ✏️게시판
- 쓰기
- 수정
- 삭제
- 조회(제목조회, 개별게시글조회)
- 페이징

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

### ✏️회원
- 회원 가입
- 회원정보 수정
- 회원탈퇴
- 조회
- 로그인
- 회원 로그인 유지
- 회원 아이디 찾기
- 비밀번호 찾기


## 프로젝트 기능 요구 사항

### ✏️회원
- [ ] 닉네임, 비밀번호, 비밀번호 확인을 입력하기 #validateSignUpParam
- [ ] 닉네임 검증: #validateNickname(String username)
  - [ ] 최소 3자 이상
  - [ ] 알파벳 대소문자(a~z, A~Z)
  - [ ] 숫자(0~9)로 구성하기
- [ ] 비밀번호 규칙검증: #validatePassword(String password)
  - [ ] 최소 8자 이상
  - [ ] 대문자 하나이상 포함
  - [ ] 특수문자 하나이상 포함
  - [ ] 숫자 하나이상 포함
- [ ] 닉네임 중복 체크 #checkDuplicatedNickname(String nickname)
- [ ] 로그인 검사 - JWT
- [ ] 권한 분할 (일반유저, 관리자, 밴유저) (USER, ADMIN, BLOCK)

### ✏️게시판
- [ ] 게시판 제목은 30글자 까지만 허용
- [ ] 게시판 내용이 비어있으면 "게시판 내용은 비어있을수 없습니다" 메시지 보내주기
- [ ] 게시판 순서는 시간순서로 최근작성된거로 보여주기
- [ ] 게시물 본문 태그 함께 저장하기
- [ ] 본인이 작성한 게시글인지 서버에서 검증로직 추가하기


### ✏️댓글
- [ ] 제일 최근 작성된 댓글을 맨 위에 띄우기
- [ ] 댓글 내용이 비어서 요청이 들어오면 댓글 "내용을 입력해주세요" 라는 메세지를 보내주기
- [ ] 본인이 작성한 댓글인지 서버에서 검증로직 추가하기
  - [ ] 댓글 작성자 본인이 아닌 유저가 삭제 요청을 했을 경우 "댓글 작성자 본인만 삭제 가능합니다" 메시지 보내주기
- [ ] 수정된 댓글 내용이 비어있을 경우 "댓글 내용은 비어 있을 수 없습니다" 메시지 보내주기
- [ ] 댓글 태그 함께 저장하기
- [ ] 기존 댓글의 내용을 수정했을 경우 새로 입력한 댓글 내용으로 전달하기


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

<br> 예시는 아래와 같다.


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
