package com.backtoback.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class MemberLoginReq {

    @NotEmpty(message = "아이디를 입력 하세요.")
    private String memberId;
    @NotEmpty(message = "비밀번호를 입력 하세요.")
    private String memberPassword;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(memberId, memberPassword);
    }
    @Builder
    public MemberLoginReq(String memberId, String memberPassword){
        this.memberId = memberId;
        this.memberPassword = memberPassword;
    }
}
