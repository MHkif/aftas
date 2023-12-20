package yc.mhkif.aftas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.requests.FishRequest;
import yc.mhkif.aftas.dtos.requests.LevelRequest;
import yc.mhkif.aftas.dtos.responses.FishResponse;
import yc.mhkif.aftas.dtos.responses.LevelResponse;
import yc.mhkif.aftas.entities.Fish;
import yc.mhkif.aftas.entities.Level;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.repositories.ILevelRepository;
import yc.mhkif.aftas.services.ILevelService;

import java.util.List;
import java.util.Optional;

@Service
public class LevelServiceImpl implements ILevelService {

    private final ILevelRepository repository;

    @Autowired
    public LevelServiceImpl(ILevelRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<LevelResponse> getById(Integer code) {
        Optional<Level> optionalLevel = repository.findById(code);
        if (optionalLevel.isPresent()) {
            LevelResponse response = toResponse(optionalLevel.get());
            return ResponseEntity.ok(response);
        } else {
            throw  new EntityNotFoundException("Level not found with the given Code.");
        }
    }

    @Override
    public ResponseEntity<Page<LevelResponse>> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LevelResponse> levels = repository.findAll(pageRequest).map(this::toResponse);
        return ResponseEntity.ok(levels);
    }


    @Override
    public ResponseEntity<LevelResponse> create(LevelRequest request) {
        Level existingLevel = repository.findByDescription(request.getDescription()).orElse(null);
        if(existingLevel != null){
            throw new EntityAlreadyExistsException("Level already exists with the given Description.");
        }


        Level savedLevel = repository.save(reqToEntity(request));
        return ResponseEntity.ok(toResponse(savedLevel));
    }

    @Override
    public ResponseEntity<LevelResponse> update(int id, LevelRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public Level resToEntity(LevelResponse response) {

        Level level = new Level();

        if (response != null) {
            level.setCode(response.getCode());
            level.setDescription(response.getDescription());
            level.setPoints(response.getPoints());
        }

        return level;
    }

    @Override
    public Level reqToEntity(LevelRequest request) {

        Level level = new Level();

        if (request != null) {
            level.setDescription(request.getDescription());
            level.setPoints(request.getPoints());
        }

        return level;
    }

    @Override
    public LevelRequest toRequest(Level level) {
        LevelRequest request = new LevelRequest();

        if (level != null) {
            request.setDescription(level.getDescription());
            request.setPoints(level.getPoints());
        }

        return request;
    }

    @Override
    public LevelResponse toResponse(Level level) {

        LevelResponse response = new LevelResponse();

        if (response != null) {
            response.setCode(level.getCode());
            response.setDescription(level.getDescription());
            response.setPoints(level.getPoints());
        }

        return response;
    }
}
