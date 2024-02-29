package yc.mhkif.aftas.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dto.requests.UserRequest;
import yc.mhkif.aftas.dto.responses.UserResponse;
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
    public UserResponse getById(Integer id) {

        Optional<User> optionalMember = repository.findById(id);
        if (optionalMember.isPresent()) {
            return mapper.map(optionalMember.get(), UserResponse.class);
        } else {
            throw new NotFoundException("Member not found with the given ID.");
        }
    }



    @Override
    public Page<UserResponse> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return repository.findAll(pageRequest).map(e -> mapper.map(e, UserResponse.class));
    }

    @Override
    public UserResponse create(UserRequest request) {
        User existingUserEmail = repository.findByEmail(request.getEmail());
        User existingUserIdentity = repository.findByIdentityNumber(request.getIdentityNumber());

        if (Objects.nonNull(existingUserEmail)) {
            throw new EntityAlreadyExistsException("User already exists with the given Email.");
        } else if (Objects.nonNull(existingUserIdentity)) {
            throw new EntityAlreadyExistsException("User already exists with the given Identity Number.");
        }
        User savedUser = repository.save(mapper.map(request, User.class));
      //  savedUser.setActivate(false);
        return mapper.map(savedUser, UserResponse.class);
    }

    @Override
    public UserResponse update(Integer id, User user) {
        return null;
    }

    @Override
    public User auth(String email, String password) {
            User user = repository.findByEmail(email);
            if(Objects.isNull(user)){
                throw new NotFoundException("No User Found with this Email");
            }
            else if (!Objects.equals(user.getPassword(), password)) {
                throw new BadRequestException("Incorrect Password");
            }

            return user;

    }
    @Override
    public UserResponse checkExistenceByIdentity(String identity) {
        User user = repository.findByIdentityNumber(identity);
        if (Objects.nonNull(user)) {
            return mapper.map(user, UserResponse.class);
        } else {
            throw new NotFoundException("User not found with the given Identity.");
        }
    }

    @Override
    public UserResponse checkExistenceByEmail(String email) {
        User user = repository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return mapper.map(user, UserResponse.class);
        } else {
            throw new NotFoundException("User not found with the given Email.");
        }
    }

    @Override
    public Boolean activateAccount(String identity) {
        User user = repository.findByIdentityNumber(identity);
        if (Objects.nonNull(user)) {
            user.setActivate(true);
            repository.save(user);
            return true;
        } else {
            throw new NotFoundException("User not found with the given Identity.");
        }
    }
}
