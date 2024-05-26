package org.weathersensor.SpringRESTWeatherSensor.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.weathersensor.SpringRESTWeatherSensor.models.Operator;
import org.weathersensor.SpringRESTWeatherSensor.models.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OperatorDetails implements UserDetails {

    private final Operator operator;

    public OperatorDetails(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = operator.getRoles();

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.operator.getPassword();
    }

    @Override
    public String getUsername() {
        return this.operator.getUsername();
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

    public Operator getOperator() {
        return this.operator;
    }
}
