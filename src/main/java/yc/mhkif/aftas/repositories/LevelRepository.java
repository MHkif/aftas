package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Level;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Integer> {
    Optional<Level> findByDescription(String description);

    Optional<Level> findByCode(int i);
}
