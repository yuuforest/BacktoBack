package com.backtoback.auth.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@DynamicInsert
@SuperBuilder(toBuilder = true)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="MEMBERS")
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Identity로 하면 디비엔진에 따라 오토 인크리먼트가 안먹는다.
    @Column(name = "member_seq")
    private Long memberSeq;

    @Column(name = "member_id", nullable = false, length = 50, unique = true)
    private String memberId;

    @Column(name = "member_password", nullable = false, length = 150)
    private String memberPassword;

    @Column(name = "nickname", nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(name = "point")
    @ColumnDefault(value = "0")
    private Integer point;

    @Column(name = "betting_total")
    @ColumnDefault(value = "0")
    private Integer bettingTotal;

    @Column(name = "betting_win")
    @ColumnDefault(value = "0")
    private Integer bettingWin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_team_seq")
    private Team team;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> privilege;



    @Builder
    public Member(Team team, String memberId, String nickname, Set<String> privilege){
        this.memberId = memberId;
        this.nickname = nickname;
        this.privilege = privilege;
        this.team = team;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.privilege.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }






}
