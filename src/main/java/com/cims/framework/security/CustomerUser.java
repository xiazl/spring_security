package com.cims.framework.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baidu
 * @date 2019-04-14 18:41
 * @description TODO
 **/
public class CustomerUser extends User {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(CustomerUser.class);

    // ~ Instance fields
    // ================================================================================================
    private final Long id;
    private final String nickname;

    // ~ Constructors
    // ===================================================================================================

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param username the username presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     * <code>DaoAuthenticationProvider</code>
     * @param authorities the authorities that should be granted to the caller if they
     * presented the correct username and password and the user is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     * a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public CustomerUser(Long id, String username, String nickname, String password, Collection<? extends GrantedAuthority> authorities) {

        super(username,password,authorities);
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public List<String> getRoles() {
        return this.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CustomerUser{" +
                super.toString()+
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
