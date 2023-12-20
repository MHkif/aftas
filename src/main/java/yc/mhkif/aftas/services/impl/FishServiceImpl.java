package yc.mhkif.aftas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.FishesLevelResponse;
import yc.mhkif.aftas.dtos.requests.FishRequest;
import yc.mhkif.aftas.dtos.responses.FishResponse;
import yc.mhkif.aftas.dtos.responses.LevelResponse;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Level;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.repositories.IFishRepository;
import yc.mhkif.aftas.repositories.ILevelRepository;
import yc.mhkif.aftas.services.IFishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FishServiceImpl implements IFishService {

    private final IFishRepository repository;
    private final ILevelRepository levelRepository;

    @Autowired
    public FishServiceImpl(IFishRepository repository, ILevelRepository levelRepository) {
        this.repository = repository;
        this.levelRepository = levelRepository;
    }

    @Override
    public ResponseEntity<FishResponse> getById(String name) {
        Optional<Fish> optionalFish = repository.findById(name);
        if (optionalFish.isPresent()) {
            FishResponse response = toResponse(optionalFish.get());
            return ResponseEntity.ok(response);
        } else {
            throw new EntityNotFoundException("Fish not found with the given Name.");
        }
    }

    @Override
    public ResponseEntity<Page<FishResponse>> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FishResponse> fishes = repository.findAll(pageRequest).map(this::toResponse);
        return ResponseEntity.ok(fishes);
    }

    @Override
    public ResponseEntity<Page<FishesLevelResponse>> getFishesByLevel(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Level> allLevels = levelRepository.findAll();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allLevels.size());

        List<Level> paginatedLevels = allLevels.subList(start, end);

        Page<Level> pageResponse = new PageImpl<>(paginatedLevels, pageRequest, allLevels.size());

        Page<FishesLevelResponse> fishesLevelResponses = pageResponse.map(level -> FishesLevelResponse.builder()
                .level(level)
                .fishResponses(level.getFish().stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()))
                .build());

        return ResponseEntity.ok(fishesLevelResponses);


    }

    @Override
    public ResponseEntity<FishResponse> create(FishRequest request) {
        levelRepository.findById(request.getLevel().getCode())
                .orElseThrow(() -> new EntityNotFoundException("Level not found with the given code."));

        if (repository.existsByName(request.getName())) {
            throw new EntityAlreadyExistsException("Fish already exists with the given name.");
        }

        Fish savedFish = repository.save(reqToEntity(request));
        return ResponseEntity.ok(toResponse(savedFish));
    }

    @Override
    public ResponseEntity<FishResponse> update(int id, FishRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public Fish resToEntity(FishResponse response) {
        Fish fish = new Fish();

        if (response != null) {
            fish.setName(response.getName());
            fish.setAverageWeight(response.getAverageWeight());
            fish.setLevel(response.getLevel());
        }

        return fish;
    }

    @Override
    public Fish reqToEntity(FishRequest request) {
        Fish fish = new Fish();

        if (request != null) {
            fish.setName(request.getName());
            fish.setAverageWeight(request.getAverageWeight());
            fish.setLevel(request.getLevel());
        }

        return fish;
    }

    @Override
    public FishRequest toRequest(Fish fish) {
        FishRequest request = new FishRequest();

        if (fish != null) {
            request.setName(fish.getName());
            request.setAverageWeight(fish.getAverageWeight());
            request.setLevel(fish.getLevel());
        }

        return request;
    }

    @Override
    public FishResponse toResponse(Fish fish) {
        FishResponse response = new FishResponse();

        if (fish != null) {
            response.setName(fish.getName());
            response.setAverageWeight(fish.getAverageWeight());
            response.setLevel(fish.getLevel());
        }

        return response;
    }
}
