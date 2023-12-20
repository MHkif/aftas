package yc.mhkif.aftas.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import yc.mhkif.aftas.dtos.requests.MemberRequest;
import yc.mhkif.aftas.dtos.responses.MemberResponse;
import yc.mhkif.aftas.entities.Member;
import yc.mhkif.aftas.exceptions.EntityAlreadyExistsException;
import yc.mhkif.aftas.exceptions.EntityNotFoundException;
import yc.mhkif.aftas.repositories.IMemberRepository;
import yc.mhkif.aftas.services.IMemberService;

import java.util.Optional;

@Service
@Slf4j
public class MemberServiceImpl implements IMemberService {
    private final IMemberRepository repository; // Assuming MemberRepository is your JPA repository

    @Autowired
    public MemberServiceImpl(IMemberRepository memberRepository) {
        this.repository = memberRepository;
    }


    @Override
    public ResponseEntity<MemberResponse> getById(Integer id) {

        Optional<Member> optionalMember = repository.findById(id);
        if (optionalMember.isPresent()) {
            MemberResponse response = toResponse(optionalMember.get());
            return ResponseEntity.ok(response);
        } else {
           throw  new EntityNotFoundException("Member not found with the given ID.");
        }
    }
    @Override
    public ResponseEntity<MemberResponse> checkExistence(String identity) {
        boolean isExisted = repository.existsByIdentityNumber(identity);

        if(isExisted){
            Optional<Member> optionalMember = repository.findByIdentityNumber(identity);
            if (optionalMember.isPresent()) {
                MemberResponse response = toResponse(optionalMember.get());
                return ResponseEntity.ok(response);
            } else {
                throw  new EntityNotFoundException("Member not found with the given Identity.");
            }
        }else {
            throw  new EntityNotFoundException("Member not found with the given Identity.");
        }

    }

    @Override
    public ResponseEntity<Page<MemberResponse>> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<MemberResponse> members = repository.findAll(pageRequest).map(this::toResponse);
        return ResponseEntity.ok(members);
    }

    @Override
    public ResponseEntity<MemberResponse> create(MemberRequest request) {
        Member existingMember = repository.findByIdentityNumber(request.getIdentityNumber()).orElse(null);
        if(existingMember != null){
            throw new EntityAlreadyExistsException("Member already exists with the given Identity Number.");
        }
        Member savedMember = repository.save(reqToEntity(request));
        return ResponseEntity.ok(toResponse(savedMember));
    }

    @Override
    public ResponseEntity<MemberResponse> update(int id, MemberRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        return null;
    }

    @Override
    public MemberResponse toResponse(Member member) {
        MemberResponse response = new MemberResponse();

        if (member != null) {
            response.setNum(member.getNum());
            response.setName(member.getName());
            response.setFamiltyName(member.getFamiltyName());
            response.setAccessionDate(member.getAccessionDate());
            response.setAccessionDate(member.getAccessionDate());
            response.setNationality(member.getNationality());
            response.setIdentityDocument(member.getIdentityDocument());
            response.setIdentityNumber(member.getIdentityNumber());

        }

        return response;
    }

    @Override
    public MemberRequest toRequest(Member member) {
        MemberRequest request = new MemberRequest();

        if (member != null) {
            request.setName(member.getName());
            request.setFamiltyName(member.getFamiltyName());
            request.setAccessionDate(member.getAccessionDate());
            request.setAccessionDate(member.getAccessionDate());
            request.setNationality(member.getNationality());
            request.setIdentityDocument(member.getIdentityDocument());
            request.setIdentityNumber(member.getIdentityNumber());

        }

        return request;
    }

    @Override
    public Member resToEntity(MemberResponse response) {
        Member member = new Member();

        if (response != null) {
            member.setNum(response.getNum());
            member.setName(response.getName());
            member.setFamiltyName(response.getFamiltyName());
            member.setAccessionDate(response.getAccessionDate());
            member.setAccessionDate(response.getAccessionDate());
            member.setNationality(response.getNationality());
            member.setIdentityDocument(response.getIdentityDocument());
            member.setIdentityNumber(response.getIdentityNumber());

        }

        return member;
    }
    @Override
    public Member reqToEntity(MemberRequest request) {
        Member member = new Member();

        if (request != null) {
            member.setName(request.getName());
            member.setFamiltyName(request.getFamiltyName());
            member.setAccessionDate(request.getAccessionDate());
            member.setAccessionDate(request.getAccessionDate());
            member.setNationality(request.getNationality());
            member.setIdentityDocument(request.getIdentityDocument());
            member.setIdentityNumber(request.getIdentityNumber());
        }

        return member;
    }
}
