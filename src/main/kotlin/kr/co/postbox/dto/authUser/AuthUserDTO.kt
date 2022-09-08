package kr.co.postbox.dto.authUser

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthUserDTO (var phoneNumber:String,var pwd:String ,var memberKey:Long?,var nickName:String?,var role:String):UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities= mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(role))
        return authorities
    }

    override fun getPassword(): String = pwd

    override fun getUsername(): String = phoneNumber

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean  = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

}