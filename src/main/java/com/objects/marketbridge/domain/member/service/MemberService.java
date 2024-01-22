package com.objects.marketbridge.domain.member.service;

import com.objects.marketbridge.domain.member.dto.FindPointDto;
import com.objects.marketbridge.domain.member.dto.IsCheckedDto;
import com.objects.marketbridge.domain.member.dto.SignInDto;
import com.objects.marketbridge.domain.member.dto.SignUpDto;
import com.objects.marketbridge.domain.model.Member;
import com.objects.marketbridge.domain.member.repository.MemberRepository;
import com.objects.marketbridge.global.security.dto.JwtTokenDto;
import com.objects.marketbridge.global.security.jwt.JwtTokenProvider;
import com.objects.marketbridge.domain.model.Membership;
import com.objects.marketbridge.domain.model.Point;
import com.objects.marketbridge.global.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public IsCheckedDto isDuplicateEmail(String email){
        boolean isDuplicateEmail = memberRepository.findByEmail(email).isPresent();
        return IsCheckedDto.builder().isChecked(isDuplicateEmail).build();
    }

    @Transactional
    public void save(SignUpDto signUpDto) throws BadRequestException {
        boolean isDuplicateEmail = isDuplicateEmail(signUpDto.getEmail()).isChecked();

        if (isDuplicateEmail) throw new BadRequestException("이미 존재하는 이메일 입니다.");

        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());
        Member member = signUpDto.toEntity(encodedPassword);
        memberRepository.save(member);
    }

    public JwtTokenDto signIn(SignInDto signInDto) {

        String username = signInDto.getEmail();
        String password = signInDto.getPassword();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return jwtTokenProvider.generateToken(principal);
    }

    public void signOut(Long memberId) {
        jwtTokenProvider.deleteToken(memberId);
    }

    public JwtTokenDto reIssueToken(CustomUserDetails principal) {
        return jwtTokenProvider.generateToken(principal);
    }

    @Transactional
    public void changeMemberShip(Long id){
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id)); // id 를 통한 조회실패 예외발생

        if(findMember.getMembership().equals("BASIC")){//멤버십 WOW 등록
            findMember.setMembership(Membership.WOW.toString());
            memberRepository.save(findMember);
        }else {// 멤버십 BASIC으로 해제
            findMember.setMembership(Membership.BASIC.toString());
            memberRepository.save(findMember);
        }
    }

    public FindPointDto findPointById(Long id){
        Member findMemberWithPoint=memberRepository.findByIdWithPoint(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + id));

        return Point.toDto(findMemberWithPoint);
    }
}
