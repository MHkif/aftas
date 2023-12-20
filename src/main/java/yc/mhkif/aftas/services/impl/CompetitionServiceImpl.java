package yc.mhkif.aftas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.requests.CompetitionRequest;
import yc.mhkif.aftas.dtos.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.repositories.ICompetitionRepository;
import yc.mhkif.aftas.repositories.IMemberRepository;
import yc.mhkif.aftas.services.ICompetitionService;

import java.util.List;
import java.util.Optional;


@Service
public class CompetitionServiceImpl implements ICompetitionService {

    private final ICompetitionRepository repository;
    private final IMemberRepository memberRepository;

    @Autowired
    public CompetitionServiceImpl(ICompetitionRepository repository, IMemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    @Override
    public ResponseEntity<CompetitionResponse> getById(String code) {
        Optional<Competition> optionalCompetition= repository.findById(code);
        if (optionalCompetition.isPresent()) {
            CompetitionResponse response = toResponse(optionalCompetition.get());
            return ResponseEntity.ok(response);
        } else {
            throw new EntityNotFoundException("Competition not found with the given Code.");
        }
    }

    @Override
    public ResponseEntity<Page<CompetitionResponse>> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CompetitionResponse> competitions = repository.findAll(pageRequest).map(this::toResponse);
        return ResponseEntity.ok(competitions);
    }

    @Override
    public ResponseEntity<CompetitionResponse> create(CompetitionRequest request) {
        if (repository.existsById(request.getCode())) {
            throw new EntityAlreadyExistsException("Competition already exists with the given Code.");
        }

        Competition savedRequest = repository.save(reqToEntity(request));
        return ResponseEntity.ok(toResponse(savedRequest));
    }



    @Override
    public ResponseEntity<CompetitionResponse> update(int id, CompetitionRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public Competition resToEntity(CompetitionResponse response) {
        Competition competition = new Competition();

        if (response != null) {
            competition.setCode(response.getCode());
            competition.setDate(response.getDate());
            competition.setLocation(response.getLocation());
            competition.setStartTime(response.getStartTime());
            competition.setEndTime(response.getEndTime());
            competition.setAmount(response.getAmount());
            competition.setMembers(response.getMembers());
            competition.setStatus(response.getStatus());
            competition.setNumberOfParticipants(response.getNumberOfParticipants());
        }

        return competition;
    }

    @Override
    public Competition reqToEntity(CompetitionRequest request) {
        Competition competition = new Competition();

        if (request != null) {
            competition.setCode(request.getCode());
            competition.setDate(request.getDate());
            competition.setLocation(request.getLocation());
            competition.setStartTime(request.getStartTime());
            competition.setEndTime(request.getEndTime());
            competition.setAmount(request.getAmount());
            competition.setNumberOfParticipants(request.getNumberOfParticipants());
        }

        return competition;
    }

    @Override
    public CompetitionRequest toRequest(Competition competition) {
        CompetitionRequest request = new CompetitionRequest();

        if (competition != null) {
            request.setCode(competition.getCode());
            request.setDate(competition.getDate());
            request.setLocation(competition.getLocation());
            request.setStartTime(competition.getStartTime());
            request.setEndTime(competition.getEndTime());
            request.setAmount(competition.getAmount());
            request.setNumberOfParticipants(competition.getNumberOfParticipants());
        }

        return request;
    }

    @Override
    public CompetitionResponse toResponse(Competition competition) {
        CompetitionResponse response = new CompetitionResponse();

        if (competition != null) {
            response.setCode(competition.getCode());
            response.setDate(competition.getDate());
            response.setLocation(competition.getLocation());
            response.setStartTime(competition.getStartTime());
            response.setEndTime(competition.getEndTime());
            response.setAmount(competition.getAmount());
            response.setStatus(competition.getStatus());
            response.setMembers(competition.getMembers());
            response.setNumberOfParticipants(competition.getNumberOfParticipants());
        }

        return response;
    }
}
