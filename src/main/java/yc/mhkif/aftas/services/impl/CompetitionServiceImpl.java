package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.CompetitionRequest;
import yc.mhkif.aftas.dto.responses.CompetitionResponse;
import yc.mhkif.aftas.entities.Competition;
import yc.mhkif.aftas.exceptions.BadRequestException;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.CompetitionRepository;
import yc.mhkif.aftas.repositories.UserRepository;
import yc.mhkif.aftas.services.ICompetitionService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements ICompetitionService {

    private final CompetitionRepository repository;
    private final ModelMapper mapper;


    @Override
    public CompetitionResponse getById(String code) {
        Optional<Competition> optionalCompetition = repository.findById(code);
        if (optionalCompetition.isPresent()) {
            return mapper.map(optionalCompetition.get(), CompetitionResponse.class);
        } else {
            throw new NotFoundException("Competition not found with the given Code.");
        }
    }

    @Override
    public Page<CompetitionResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(
                competition -> mapper.map(competition, CompetitionResponse.class)
        );

    }

    @Override
    public CompetitionResponse create(CompetitionRequest request) {
        var code = generateCode(request.getLocation(), request.getDate());
        if (repository.existsById(code)) {
            throw new EntityAlreadyExistsException("Competition already exists with the given Code.");
        }
        boolean isValidTime = validateCompetitionTime(request.getStartTime(), request.getEndTime());

        if (!isValidTime) {
            throw new BadRequestException("End Time must be higher than Start time at least by one Hour .");
        }

        Competition competition = mapper.map(request, Competition.class);
        competition.setCode(code);
        Competition savedCompetition = repository.save(competition);
        return mapper.map(savedCompetition, CompetitionResponse.class);
    }

    @Override
    public CompetitionResponse update(String id, Competition competition) {
        return null;
    }

    public String generateCode(String location, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
        location = location.substring(0, Math.min(location.length(), 3)).toLowerCase();
        return location + "-" + formattedDate;
    }

    private boolean validateCompetitionTime(LocalTime start, LocalTime end) {
        LocalTime startTime = LocalTime.of(start.getHour(), start.getMinute());
        LocalTime endTime = LocalTime.of(end.getHour(), end.getMinute());
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours() >= 1;

    }
}
