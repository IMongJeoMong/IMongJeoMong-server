package com.imongjeomong.imongjeomongserver.util;

import com.imongjeomong.imongjeomongserver.entity.Member;
import com.imongjeomong.imongjeomongserver.exception.CustomExceptionStatus;
import com.imongjeomong.imongjeomongserver.exception.UnAuthenticationException;
import com.imongjeomong.imongjeomongserver.member.model.service.MemberServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final MemberServiceImpl memberService;
    private static final String SALT = "songleejeonjeong";

    public String createAccessToken(Long memberId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(30, ChronoUnit.MINUTES);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return create(memberId, "access-token", expirationDate);
    }

    public String createRefreshToken(Long memberId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(1, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return create(memberId, "refresh-token", expirationDate);
    }

    public String create(Long memberId, String subject, Date date) {
        Member member = memberService.getMemberById(memberId).orElseThrow(
                () -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));
        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("regDate", System.currentTimeMillis())
                .setSubject(subject)
                .setExpiration(date)
                .claim("memberId", memberId)
                .claim("email", member.getEmail())
                .claim("nickname", member.getNickname())
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact();
        return jwt;
    }

    public Long getMemberId(String jwt) {
        return Long.parseLong(get(jwt).get("memberId").toString());
    }

    public Map<String, Object> get(String jwt) {
        Jws<Claims> claims = null;
        try {
            claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
        } catch (Exception e) {
            throw new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_GET_MEMBER_ID_FAILED);
        }
        Map<String, Object> value = claims.getBody();
        return value;
    }


    public Member getMemberInfo(HttpServletRequest request) {
        String accessToken = request.getHeader("access-token");
        checkToken(accessToken);

        return memberService.getMemberById(getMemberId(accessToken))
                     .orElseThrow(() -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));
    }

    public boolean checkToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwt);
            Date expiration = claims.getBody().getExpiration();
            return new Date(System.currentTimeMillis()).before(expiration);
        } catch (Exception e) {
            throw new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_TOKEN_VALIDATION_FAILED);
        }
    }
}