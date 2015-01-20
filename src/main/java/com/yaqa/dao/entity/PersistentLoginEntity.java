package com.yaqa.dao.entity;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * This entity serves the only purpose: so hibernate will generate corresponding table on startup.
 */
@Entity
@Table(name = "persistent_logins")
public class PersistentLoginEntity {

    @Id
    @Column(name = "series")
    private String series;

    @NotNull
    @Size(max = 64)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(max = 64)
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "last_used")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime lastUsed;

}
