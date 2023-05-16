package com.backtoback.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

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
