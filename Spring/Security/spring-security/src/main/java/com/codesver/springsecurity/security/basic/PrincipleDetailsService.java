package com.codesver.springsecurity.security.basic;

import com.codesver.springsecurity.mvc.entity.Member;
import com.codesver.springsecurity.mvc.repsoitory.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleDetailsService implements UserDetailsService {

    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = repository.findMemberByUserId(username);
        return optionalMember.map(PrincipleDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
