package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        Member member = new Member("memberV0", 10000);
        repository.save(member);
        Member memberV0 = repository.findById(member.getMemberId());
        assertThat(memberV0).isEqualTo(member);

        repository.update(member.getMemberId(), 8000);

        Member findMember = repository.findById(member.getMemberId());
        assertThat(findMember.getMoney()).isEqualTo(8000);

        repository.delete(findMember.getMemberId());
        assertThatThrownBy(()->
                repository.findById(findMember.getMemberId())).isInstanceOf(NoSuchElementException.class);

    }

}