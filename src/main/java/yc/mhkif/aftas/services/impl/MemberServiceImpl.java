package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.MemberRequest;
import yc.mhkif.aftas.dto.responses.MemberResponse;
import yc.mhkif.aftas.entities.User;
import yc.mhkif.aftas.exceptions.BadRequestException;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.NotFoundException;
import yc.mhkif.aftas.repositories.UserRepository;
import yc.mhkif.aftas.services.IMemberService;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements IMemberService {
    private final UserRepository repository;
    private final ModelMapper mapper;


    @Override
    public MemberResponse getById(Integer id) {

        Optional<User> optionalMember = repository.findById(id);
        if (optionalMember.isPresent()) {
            return mapper.map(optionalMember.get(), MemberResponse.class);
        } else {
            throw new NotFoundException("Member not found with the given ID.");
        }
    }

    @Override
    public MemberResponse checkExistence(String identity) {
        boolean isExisted = repository.existsByIdentityNumber(identity);

        if (isExisted) {
            Optional<User> optionalMember = repository.findByIdentityNumber(identity);
            if (optionalMember.isPresent()) {
                return mapper.map(optionalMember.get(), MemberResponse.class);
            } else {
                throw new NotFoundException("Member not found with the given Identity.");
            }
        } else {
            throw new NotFoundException("Member not found with the given Identity.");
        }

    }

    @Override
    public Page<MemberResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(e -> mapper.map(e, MemberResponse.class));
    }

    @Override
    public MemberResponse create(MemberRequest request) {
        User existingUser = repository.findByIdentityNumber(request.getIdentityNumber()).orElse(null);
        if (existingUser != null) {
            throw new EntityAlreadyExistsException("Member already exists with the given Identity Number.");
        }
        User savedUser = repository.save(mapper.map(request, User.class));
        return mapper.map(savedUser, MemberResponse.class);
    }

    @Override
    public MemberResponse update(Integer id, User user) {
        return null;
    }

    @Override
    public User auth(String email, String password) {

        if (repository.existsByEmail(email)) {
            User user = repository.findByEmail(email);
            if (!Objects.equals(user.getPassword(), password)) {
                throw new BadRequestException("Incorrect Password");
            }

            return user;

        } else {
            throw new NotFoundException("No User Found with this Email");
        }
    }

}
