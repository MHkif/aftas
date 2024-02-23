package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.RankingRequest;
import yc.mhkif.aftas.dto.responses.RankingResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionUser;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.CompetitionRepository;
import yc.mhkif.aftas.repositories.UserRepository;
import yc.mhkif.aftas.repositories.RankingRepository;
import yc.mhkif.aftas.services.IRankingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements IRankingService {

    private final RankingRepository repository;
    private final CompetitionRepository competitionRepository;
    private final UserRepository memberRepository;
    private final ModelMapper mapper;


    @Override
    public RankingResponse getById(CompetitionUser id) {
        Optional<Ranking> optionalRanking = repository.findById(id);
        if (optionalRanking.isPresent()) {
            return mapper.map(optionalRanking.get(), RankingResponse.class);
        } else {
            throw new NotFoundException("Ranking not found with the given Code.");
        }
    }

    @Override
    public Page<RankingResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(ranking -> mapper.map(ranking, RankingResponse.class));
    }

    @Override
    public RankingResponse create(RankingRequest request) {
        Optional<Competition> competition = this.competitionRepository.findById(request.getId().getCompetitionCode());
        Optional<User> member = this.memberRepository.findById(request.getId().getUserId());


        if (competition.isEmpty()){
            throw new NotFoundException("Competition not found with the given Code.");
        }


        if (member.isEmpty()){
            throw new NotFoundException("Member not found with the given num of memberShip.");
        }

        if (competition.get().getNumberOfParticipants() > 10){
            throw new IllegalStateException("Competition Reaches The Limit Number Of Participants");
        }


        boolean isBefore24Hours = this.checkCompetitionDate(competition.get().getDate(), competition.get().getStartTime());

        if (repository.existsById(request.getId())) {
            throw new EntityAlreadyExistsException("This Member is already a participant in this Competition.");
        }

        if(!isBefore24Hours){
            throw new IllegalStateException("You cannot register a member to a Competition that will Start before 24 Hours");
        }

        Ranking savedRanking = repository.save(mapper.map(request, Ranking.class));
        return mapper.map(savedRanking, RankingResponse.class);

    }

    @Override
    public RankingResponse update(CompetitionUser id, Ranking ranking) {
        return null;
    }

    @Override
    public List<Ranking> getPodium(String id){

        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competition not found with the given Id.")
                );

        List<Ranking> top3 = repository.findTop3ByCompetitionOrderByScoreDesc(competition);
        return IntStream.range(0, top3.size())
                .mapToObj(index -> {
                    Ranking ranking = top3.get(index);
                    ranking.setRank(index + 1);
                    return ranking;
                })
                .sorted(Comparator.comparingInt(Ranking::getScore).reversed())
                .toList();


    }

    @Override
    public List<RankingResponse> getByCompetition(String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(() ->
                new NotFoundException("Could not find a Competition with the given code"));
        List<RankingResponse> rankings = repository.findAllByCompetition(competition).stream().map(
                ranking -> mapper.map(ranking, RankingResponse.class)
        ).toList();
        return IntStream.range(0, rankings.size())
                .mapToObj(index -> {
                    RankingResponse ranking = rankings.get(index);
                    ranking.setRank(index + 1);
                    return ranking;
                })
                .sorted(Comparator.comparingInt(RankingResponse::getScore).reversed())
                .toList();
    }


    public  boolean checkCompetitionDate(LocalDate date, LocalTime start) {
        LocalDateTime competitionDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), start.getHour(), start.getMinute());
        LocalDateTime currentDatePlus24Hours = LocalDateTime.now().plusHours(24);

        return competitionDateTime.isAfter(currentDatePlus24Hours);

    }
}
