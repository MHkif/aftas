package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.HuntingRequest;
import yc.mhkif.aftas.dto.responses.HuntingResponse;
import yc.mhkif.aftas.entities.*;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.*;
import yc.mhkif.aftas.services.IHuntingService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HuntingServiceImpl implements IHuntingService {

    private final HuntingRepository repository;
    private final RankingRepository rankingRepository;
    private final UserRepository memberRepository;
    private final CompetitionRepository competitionRepository;
    private final FishRepository fishRepository;
    private final ModelMapper mapper;


    @Override
    public HuntingResponse getById(Integer id) {
        Optional<Hunting> optionalHunting = repository.findById(id);
        if (optionalHunting.isPresent()) {
            return mapper.map(optionalHunting.get(), HuntingResponse.class);
        } else {
            throw new NotFoundException("Hunting not found with the given Id.");
        }
    }

    @Override
    public Page<HuntingResponse> getAll(int page, int size) {
        return null;
    }

    @Override
    public HuntingResponse create(HuntingRequest request) {
        User user = memberRepository.findById(request.getUser().getNum())
                .orElseThrow(() -> new NotFoundException("Member not found with the given Id.")
                );

        Competition competition = competitionRepository.findById(request.getCompetition().getCode())
                .orElseThrow(() -> new NotFoundException("Competition not found with the given Id.")
                );

        Fish fish = fishRepository.findById(request.getFish().getName())
                .orElseThrow(() -> new NotFoundException("Fish not found with the given Name.")
                );

        Optional<Hunting> existingHunting = repository.findByFishAndUserAndCompetition(
                fish, user, competition);

        if (existingHunting.isPresent()) {

            // TODO : if Record Hunting is Existed
            Hunting hunting = existingHunting.get();
            int number = hunting.getNomberOfFish() + 1;
            hunting.setNomberOfFish(number);

            Hunting updatedHunting = repository.save(hunting);

            CompetitionUser id = new CompetitionUser();
            id.setUserId(updatedHunting.getUser().getNum());
            id.setCompetitionCode(updatedHunting.getCompetition().getCode());

            Ranking ranking = rankingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Ranking For this Competition & Member not found with the infos.")
                    );

            int scores = ranking.getScore()  + fish.getLevel().getPoints();

            ranking.setScore(scores);

            rankingRepository.save(ranking);
            return mapper.map(updatedHunting, HuntingResponse.class);

        } else {
            // TODO : if Record Hunting is Not Existed
            Hunting newHunting = mapper.map(request, Hunting.class);
            Hunting savedHunting = repository.save(newHunting);

            // # TODO : Retrieve Ranking Entity
            CompetitionUser id = new CompetitionUser();
            id.setUserId(savedHunting.getUser().getNum());
            id.setCompetitionCode(savedHunting.getCompetition().getCode());

            Ranking ranking = rankingRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Ranking For this Competition & Member not found with the infos.")
            );

            int scores = ranking.getScore() +  fish.getLevel().getPoints();

            ranking.setScore(scores);

            // Update Ranking Score
             rankingRepository.save(ranking);
             return mapper.map(savedHunting, HuntingResponse.class);
        }
    }

    @Override
    public HuntingResponse update(Integer id, Hunting hunting) {
        return null;
    }

}
