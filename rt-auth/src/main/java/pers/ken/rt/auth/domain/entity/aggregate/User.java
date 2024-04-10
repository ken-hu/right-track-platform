package pers.ken.rt.auth.domain.entity.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pers.ken.rt.auth.domain.entity.valueobj.*;

/**
 * @author Ken
 * @className: Authenticate
 * @createdTime: 2023/3/7 1:36
 * @desc:
 */
@Getter
@Builder
@AllArgsConstructor
public class User {
    private Long id;
    private Username username;
    private Account account;
    private Password password;
    private Email email;
    private PhoneNumber phone;
    private UserStatus userStatus;


    public User(PhoneNumber phone, Password password, Username username) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.userStatus = UserStatus.ENABLE;
    }

    public User(Email email, Password password, Username username) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userStatus = UserStatus.ENABLE;
    }

    public User(Account account, Password password, Username username) {
        this.username = username;
        this.account = account;
        this.password = password;
        this.userStatus = UserStatus.ENABLE;
    }

    public void disableUser() {
        this.userStatus = UserStatus.DISABLE;
    }

    public void enabledUser() {
        this.userStatus = UserStatus.DISABLE;
    }


    public boolean isEnabled(){
        if (UserStatus.ENABLE.equals(this.userStatus)) {
            return true;
        }
        return false;
    }
}
