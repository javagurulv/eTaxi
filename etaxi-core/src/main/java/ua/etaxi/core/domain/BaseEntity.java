package ua.etaxi.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Created by Viktor on 10/10/2014.
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Version
    @Column(name = "OPTIMISTIC_LOCK", nullable = false)
    private Long version;


}
