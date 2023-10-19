package learnb.yoga.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class AppUser implements UserDetails {

    private int userId;

    private String emailAddress;
    private String firstName;
    private String lastName;

    private LocalDate dob;
    private String phoneNumber;

    private UserType userType;

    private String password;

    private ArrayList<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities.clear();
        if (authorities == null || authorities.size() == 0) {
            return;
        }
        this.authorities.addAll(authorities.stream()
                .map(a -> new SimpleGrantedAuthority(a))
                .toList());
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    @Override
    public String getUsername() {return emailAddress;}

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return userId == appUser.userId && Objects.equals(emailAddress, appUser.emailAddress) && Objects.equals(firstName, appUser.firstName) && Objects.equals(lastName, appUser.lastName) && Objects.equals(dob, appUser.dob) && Objects.equals(phoneNumber, appUser.phoneNumber) && userType == appUser.userType && Objects.equals(password, appUser.password) && Objects.equals(authorities, appUser.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, emailAddress, firstName, lastName, dob, phoneNumber, userType, password, authorities);
    }
}