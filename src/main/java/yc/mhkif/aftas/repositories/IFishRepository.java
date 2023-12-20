package yc.mhkif.aftas.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import yc.mhkif.aftas.dtos.FishesLevelResponse;
import yc.mhkif.aftas.entities.Fish;

import java.util.Optional;

public interface IFishRepository extends JpaRepository<Fish, String> {
    boolean existsByName(String name);
}
