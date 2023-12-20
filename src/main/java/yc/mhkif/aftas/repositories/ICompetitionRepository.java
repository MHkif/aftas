package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;

import java.util.List;
import java.util.Optional;

public interface ICompetitionRepository extends JpaRepository<Competition, String> {
}
