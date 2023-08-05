package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach(){
        //기본 드라이버매니저데이터소스를 사용한 getConnection
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        //커넥션풀을 사용한 getConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repository = new MemberRepositoryV1(dataSource);
    }

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