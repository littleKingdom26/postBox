package kr.co.postbox.utils


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*



class JWTUtils {
    companion object {

        private val issuer = "ATS"
        private val subject = "Auth"
        private val secretKey = "postBox"
        private val algorithm: Algorithm = Algorithm.HMAC512(secretKey)
        private val expiration = 60L * 60 * 24 * 30 * 1000 // 임시로 한달 토큰 발행

        val tokenPrefix = "Bearer "
        val headerString = "Authorization"
        val refreshString = "RefreshAuthorization"

        object JWTClaims {
            const val MEMBER_ID = "memberId"
        }

        /**
         * 토큰 생성
         */
        fun createToken(memberId: String?): String = JWT.create()
            .withIssuer(issuer)
            .withSubject(subject)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + expiration))
            .withClaim(JWTClaims.MEMBER_ID, memberId)
            .sign(algorithm)

        /**
         * 코튼 확인
         */
        fun verity(token: String): DecodedJWT =
            JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token)

        fun extractEmail(jwt: DecodedJWT): String =
            jwt.getClaim(JWTClaims.MEMBER_ID).asString()

    }
}