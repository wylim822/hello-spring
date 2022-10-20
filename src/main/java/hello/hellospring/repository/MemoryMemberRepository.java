package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.*;

// 회원 리포지토리 메모리 구현체
public class MemoryMemberRepository implements  MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null값이 반환될 가능성이 있는 경우 optional 사용
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // member의 name이 일치할 때 반환 (반복)
        return store.values().stream()
                .filter(member -> member.getName().equals(name)) // 람다식 사용
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // store를 list로 반환
        return new ArrayList<>(store.values());
    }

    // 다음 테스트 수행을 위하여 이전 저장소 초기화
    public void clearStore(){
        store.clear();
    }
}
