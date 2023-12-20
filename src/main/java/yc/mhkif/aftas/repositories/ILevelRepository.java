package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Level;

import java.util.Optional;

public interface ILevelRepository extends JpaRepository<Level, Integer> {
    Optional<Level> findByDescription(String description);
}
