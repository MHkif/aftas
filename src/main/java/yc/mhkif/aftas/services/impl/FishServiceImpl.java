package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.FishesLevelResponse;
import yc.mhkif.aftas.dto.requests.FishRequest;
import yc.mhkif.aftas.dto.responses.FishResponse;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Level;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.FishRepository;
import yc.mhkif.aftas.repositories.LevelRepository;
import yc.mhkif.aftas.services.IFishService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FishServiceImpl implements IFishService {

    private final FishRepository repository;
    private final LevelRepository levelRepository;
    private final ModelMapper mapper;


    @Override
    public FishResponse getById(String name) {
        Optional<Fish> optionalFish = repository.findById(name);
        if (optionalFish.isPresent()) {
            return mapper.map(optionalFish.get(), FishResponse.class);
        } else {
            throw new NotFoundException("Fish not found with the given Name.");
        }
    }

    @Override
    public Page<FishResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                fish -> mapper.map(fish, FishResponse.class)
        );
    }

    @Override
    public Page<FishesLevelResponse> getFishesByLevel(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Level> allLevels = levelRepository.findAll();

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), allLevels.size());

        List<Level> paginatedLevels = allLevels.subList(start, end);


        return null;
                /*
                new PageImpl<>(paginatedLevels, pageRequest, allLevels.size())
                .map(level -> FishesLevelResponse.builder()
                        .level(level)
                        .fishResponses(level.getFish().stream()
                                .map(
                                        fish -> mapper.map(fish, FishResponse.class)
                                )
                                .collect(Collectors.toList()))
                        .build());


                 */

    }

    @Override
    public FishResponse create(FishRequest request) {
        levelRepository.findById(request.getLevel().getCode())
                .orElseThrow(() -> new NotFoundException("Level not found with the given code."));

        if (repository.existsByName(request.getName())) {
            throw new EntityAlreadyExistsException("Fish already exists with the given name.");
        }

        Fish savedFish = repository.save(mapper.map(request, Fish.class));
        return mapper.map(savedFish, FishResponse.class);
    }

    @Override
    public FishResponse update(String id, Fish fish) {
        return null;
    }


}
