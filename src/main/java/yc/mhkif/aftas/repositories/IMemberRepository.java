package yc.mhkif.aftas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByIdentityNumber(String idNum);

    boolean existsByIdentityNumber(String identity);
}
