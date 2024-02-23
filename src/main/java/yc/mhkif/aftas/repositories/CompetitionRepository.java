package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, String> {
}
