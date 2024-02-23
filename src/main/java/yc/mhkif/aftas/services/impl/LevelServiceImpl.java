package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.LevelRequest;
import yc.mhkif.aftas.dto.responses.LevelResponse;
import yc.mhkif.aftas.entities.Level;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.LevelRepository;
import yc.mhkif.aftas.services.ILevelService;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LevelServiceImpl implements ILevelService {

    private final LevelRepository repository;
    private final ModelMapper mapper;


    @Override
    public LevelResponse getById(Integer code) {
        Optional<Level> optionalLevel = repository.findById(code);
        if (optionalLevel.isPresent()) {
            return mapper.map(optionalLevel.get(), LevelResponse.class);
        } else {
            throw  new NotFoundException("Level not found with the given Code.");
        }
    }

    @Override
    public Page<LevelResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(level -> mapper.map(level, LevelResponse.class));
    }


    @Override
    public LevelResponse create(LevelRequest request) {
        Level existingLevel = repository.findByDescription(request.getDescription()).orElse(null);
        if(Objects.nonNull(existingLevel)){
            throw new EntityAlreadyExistsException("Level already exists with the given Description.");
        }
        Level savedLevel = repository.save(mapper.map(request, Level.class));
        return mapper.map(savedLevel, LevelResponse.class);
    }

    @Override
    public LevelResponse update(Integer id, Level request) {
        return null;
    }



}
