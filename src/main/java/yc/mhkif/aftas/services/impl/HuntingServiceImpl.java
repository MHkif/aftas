package yc.mhkif.aftas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.requests.HuntingRequest;
import yc.mhkif.aftas.dtos.responses.HuntingResponse;
import yc.mhkif.aftas.entities.*;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.repositories.*;
import yc.mhkif.aftas.services.IHuntingService;

import java.util.Optional;

@Service
public class HuntingServiceImpl implements IHuntingService {

    private final IHuntingRepository repository;
    private final IRankingRepository rankingRepository;
    private final IMemberRepository memberRepository;
    private final ICompetitionRepository competitionRepository;
    private final IFishRepository fishRepository;

    @Autowired
    public HuntingServiceImpl(IHuntingRepository repository, IRankingRepository rankingRepository, IMemberRepository memberRepository, ICompetitionRepository competitionRepository, IFishRepository fishRepository) {
        this.repository = repository;
        this.rankingRepository = rankingRepository;
        this.memberRepository = memberRepository;
        this.competitionRepository = competitionRepository;
        this.fishRepository = fishRepository;
    }

    @Override
    public ResponseEntity<HuntingResponse> getById(Integer id) {
        Optional<Hunting> optionalHunting = repository.findById(id);
        if (optionalHunting.isPresent()) {
            HuntingResponse response = toResponse(optionalHunting.get());
            return ResponseEntity.ok(response);
        } else {
            throw new EntityNotFoundException("Hunting not found with the given Id.");
        }
    }

    @Override
    public ResponseEntity<Page<HuntingResponse>> getAll(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<HuntingResponse> create(HuntingRequest request) {
        Member member = memberRepository.findById(request.getMember().getNum())
                .orElseThrow(() -> new EntityNotFoundException("Member not found with the given Id.")
                );

        Competition competition = competitionRepository.findById(request.getCompetition().getCode())
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with the given Id.")
                );

        Fish fish = fishRepository.findById(request.getFish().getName())
                .orElseThrow(() -> new EntityNotFoundException("Fish not found with the given Name.")
                );

        Optional<Hunting> existingHunting = repository.findByFishAndMemberAndCompetition(
                fish, member, competition);

        if (existingHunting.isPresent()) {

            // TODO : if Record Hunting is Existed
            Hunting hunting = existingHunting.get();
            int number = hunting.getNomberOfFish() + 1;
            hunting.setNomberOfFish(number);

            Hunting updatedHunting = repository.save(hunting);

            CompetitionMember id = new CompetitionMember();
            id.setMemberId(updatedHunting.getMember().getNum());
            id.setCompetitionCode(updatedHunting.getCompetition().getCode());

            Ranking ranking = rankingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ranking For this Competition & Member not found with the infos.")
                    );

            int scores = ranking.getScore()  + fish.getLevel().getPoints();

            ranking.setScore(scores);

            rankingRepository.save(ranking);
            return ResponseEntity.ok(toResponse(updatedHunting));

        } else {
            // TODO : if Record Hunting is Not Existed
            Hunting newHunting = reqToEntity(request);
            Hunting savedHunting = repository.save(newHunting);

            // # TODO : Retrieve Ranking Entity
            CompetitionMember id = new CompetitionMember();
            id.setMemberId(savedHunting.getMember().getNum());
            id.setCompetitionCode(savedHunting.getCompetition().getCode());

            Ranking ranking = rankingRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ranking For this Competition & Member not found with the infos.")
            );

            int scores = ranking.getScore() +  fish.getLevel().getPoints();

            ranking.setScore(scores);

            // Update Ranking Score
             rankingRepository.save(ranking);

            return ResponseEntity.ok(toResponse(savedHunting));
        }
    }

    @Override
    public ResponseEntity<HuntingResponse> update(int id, HuntingRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public Hunting resToEntity(HuntingResponse response) {
        Hunting hunting = new Hunting();

        if (response != null) {
            hunting.setId(response.getId());
            hunting.setNomberOfFish(response.getNomberOfFish());
            hunting.setFish(response.getFish());
            hunting.setCompetition(response.getCompetition());
            hunting.setMember(response.getMember());
        }

        return hunting;
    }

    @Override
    public Hunting reqToEntity(HuntingRequest request) {
        Hunting hunting = new Hunting();

        if (request != null) {
            hunting.setNomberOfFish(request.getNomberOfFish());
            hunting.setFish(request.getFish());
            hunting.setCompetition(request.getCompetition());
            hunting.setMember(request.getMember());
        }

        return hunting;
    }

    @Override
    public HuntingRequest toRequest(Hunting hunting) {
        HuntingRequest request = new HuntingRequest();

        if (hunting != null) {
            request.setNomberOfFish(hunting.getNomberOfFish());
            request.setFish(hunting.getFish());
            request.setCompetition(hunting.getCompetition());
            request.setMember(hunting.getMember());
        }

        return request;
    }

    @Override
    public HuntingResponse toResponse(Hunting hunting) {
        HuntingResponse response = new HuntingResponse();

        if (hunting != null) {
            response.setNomberOfFish(hunting.getNomberOfFish());
            response.setFish(hunting.getFish());
            response.setCompetition(hunting.getCompetition());
            response.setMember(hunting.getMember());
        }

        return response;
    }
}
