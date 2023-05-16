// package com.backtoback.member.dto.request;
//
//
// import com.backtoback.member.domain.Member;
// import com.backtoback.member.domain.MemberPrivilege;
// import com.backtoback.member.domain.Team;
// import com.backtoback.member.dto.TeamDto;
// import io.swagger.annotations.ApiModel;
// import io.swagger.annotations.ApiModelProperty;
// import lombok.*;
//
// import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Pattern;
// import java.util.Collections;
//
// @Getter
// @RequiredArgsConstructor
// @ApiModel("MemberUpdateReq")
// public class MemberUpdateReq {
//     @NotEmpty(message = "아이디는 필수 입력값입니다.")
// //    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
//     @ApiModelProperty(name = "memberId")
//     private String  memberId;
//     @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
//     @ApiModelProperty(name = "memberPassword")
//     private String memberPassword;
//     @NotEmpty(message = "닉네임은 필수 입력값입니다.")
//     @ApiModelProperty(name = "memberNickname")
//     private String nickname;
//     @NotNull(message = "선호팀은 필수 입력값입니다.")
//     @ApiModelProperty(name = "teamSeq")
//     private Team team;
//
//
//     public Member toEntity(String memberPassword){
//         return Member
//                 .builder()
//                 .memberId(memberId)
//                 .memberPassword(memberPassword)
//                 .nickname(nickname)
//                 .team(team)
//                 .privilege(Collections.singleton(MemberPrivilege.GENERAL.name())).build();
//     }
// }
