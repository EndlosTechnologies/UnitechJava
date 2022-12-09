package com.unitechApi.websecurity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unitechApi.user.model.PasswordEntity;
import com.unitechApi.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;



    private Long id;
    private String username;
    private String email;
    private String phoneno;
    private String address;
    private int status;
    //	private int pin;
    private Date datetime;
    private User user;

    private PasswordEntity passwordEntity;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user,PasswordEntity passwordEntity) {
        this.user = user;
        this.passwordEntity=passwordEntity;
    }


    public UserDetailsImpl(Long id, String username, String email, String password, String phoneno,
                           String address, Date datetime
            , Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
      //  this.authorities = authorities;
        this.username = username;
        this.phoneno = phoneno;
        this.email = email;
        this.password = password;
        this.address = address;
        this.authorities = authorities;
        this.datetime = datetime;

    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPasswordEntity().getPassword(),
                user.getPhoneno(),
                user.getAddress(),
                user.getDatetime(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return this.email ;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.phoneno;

    }

    public String getPhoneno() {
        return this.phoneno;
    }


    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }


    public String getAddress() {
        return address;
    }


    public void setAddress(String address) {
        this.address = address;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public Date getDatetime() {
        return datetime;
    }


    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
