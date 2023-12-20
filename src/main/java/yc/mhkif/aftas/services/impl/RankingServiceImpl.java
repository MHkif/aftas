package yc.mhkif.aftas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.requests.RankingRequest;
import yc.mhkif.aftas.dtos.responses.RankingResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.repositories.ICompetitionRepository;
import yc.mhkif.aftas.repositories.IMemberRepository;
import yc.mhkif.aftas.repositories.IRankingRepository;
import yc.mhkif.aftas.services.IRankingService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RankingServiceImpl implements IRankingService {

    private final IRankingRepository repository;
    private final ICompetitionRepository competitionRepository;
    private final IMemberRepository memberRepository;


    @Autowired
    public RankingServiceImpl(IRankingRepository repository, ICompetitionRepository competitionRepository, IMemberRepository memberRepository) {
        this.repository = repository;
        this.competitionRepository = competitionRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public ResponseEntity<RankingResponse> getById(CompetitionMember id) {
        Optional<Ranking> optionalRanking = repository.findById(id);
        if (optionalRanking.isPresent()) {
            RankingResponse response = toResponse(optionalRanking.get());
            return ResponseEntity.ok(response);
        } else {
            throw new EntityNotFoundException("Ranking not found with the given Code.");
        }
    }

    @Override
    public ResponseEntity<Page<RankingResponse>> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RankingResponse> rankings = repository.findAll(pageRequest).map(this::toResponse);
        return ResponseEntity.ok(rankings);
    }

    @Override
    public ResponseEntity<RankingResponse> create(RankingRequest request) {
        Optional<Competition> competition = this.competitionRepository.findById(request.getId().getCompetitionCode());
        Optional<Member> member = this.memberRepository.findById(request.getId().getMemberId());


        if (competition.isEmpty()){
            throw new EntityNotFoundException("Competition not found with the given Code.");
        }


        if (member.isEmpty()){
            throw new EntityNotFoundException("Member not found with the given num of memberShip.");
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

        Ranking savedRanking = repository.save(reqToEntity(request));
        return ResponseEntity.ok(toResponse(savedRanking));

    }

    @Override
    public ResponseEntity<List<Ranking>> getPodium(String id){

        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with the given Id.")
                );

        List<Ranking> top3 = repository.findTop3ByCompetitionOrderByScoreDesc(competition);
        List<Ranking> sortedList = IntStream.range(0, top3.size())
                .mapToObj(index -> {
                    Ranking ranking = top3.get(index);
                    ranking.setRank(index + 1);
                    return ranking;
                })
                .sorted(Comparator.comparingInt(Ranking::getScore).reversed())
                .toList();

        return ResponseEntity.ok(sortedList);

    }

    @Override
    public ResponseEntity<List<RankingResponse>> getByCompetition(String competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(() ->
                new EntityNotFoundException("Could not find a Competition with the given code"));
        List<RankingResponse> rankings = repository.findAllByCompetition(competition).stream().map(this::toResponse).toList();
        List<RankingResponse> sortedList = IntStream.range(0, rankings.size())
                .mapToObj(index -> {
                    RankingResponse ranking = rankings.get(index);
                    ranking.setRank(index + 1);
                    return ranking;
                })
                .sorted(Comparator.comparingInt(RankingResponse::getScore).reversed())
                .toList();
        return ResponseEntity.ok(sortedList);
    }

    @Override
    public ResponseEntity<RankingResponse> update(int id, RankingRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public Ranking resToEntity(RankingResponse response) {
        Ranking ranking = new Ranking();

        if (response != null) {
            ranking.setRank(response.getRank());
            ranking.setScore(response.getScore());
            ranking.setMember(response.getMember());
            ranking.setCompetition(response.getCompetition());
        }

        return ranking;
    }

    @Override
    public Ranking reqToEntity(RankingRequest request) {
        Ranking ranking = new Ranking();

        if (request != null) {
            ranking.setId(request.getId());
            ranking.setRank(request.getRank());
            ranking.setScore(request.getScore());
            ranking.setMember(request.getMember());
            ranking.setCompetition(request.getCompetition());
        }
        return ranking;
    }

    @Override
    public RankingRequest toRequest(Ranking ranking) {
        RankingRequest request = new RankingRequest();

        if (ranking != null) {
            request.setId(ranking.getId());
            request.setRank(ranking.getRank());
            request.setScore(ranking.getScore());
            request.setMember(ranking.getMember());
            request.setCompetition(ranking.getCompetition());
        }

        return request;
    }

    @Override
    public RankingResponse toResponse(Ranking ranking) {

        RankingResponse response = new RankingResponse();

        if (ranking != null) {
            response.setRank(ranking.getRank());
            response.setScore(ranking.getScore());
            response.setMember(ranking.getMember());
            response.setCompetition(ranking.getCompetition());
        }

        return response;
    }

    public  boolean checkCompetitionDate(LocalDate date, LocalTime start) {
        LocalDateTime competitionDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), start.getHour(), start.getMinute());
        LocalDateTime currentDatePlus24Hours = LocalDateTime.now().plusHours(24);

        return competitionDateTime.isAfter(currentDatePlus24Hours);

    }
}
