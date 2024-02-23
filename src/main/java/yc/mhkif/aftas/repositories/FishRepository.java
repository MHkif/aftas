package yc.mhkif.aftas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.entities.Fish;

public interface FishRepository extends JpaRepository<Fish, String> {
    boolean existsByName(String name);
}
