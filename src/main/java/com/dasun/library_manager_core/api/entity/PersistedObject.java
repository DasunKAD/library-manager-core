package com.dasun.library_manager_core.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
@MappedSuperclass
public abstract class PersistedObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date lastUpdate;

    public PersistedObject() {

    }

    public PersistedObject(long id) {
        this.id = id;
    }

    public PersistedObject(long version, Date createDate, Date lastUpdate) {
        this.version = version;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    @PrePersist
    protected void onCreate() {
        createDate = lastUpdate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistedObject)) return false;
        PersistedObject that = (PersistedObject) o;
        return id == that.id &&
                version == that.version &&
                Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, createDate);
    }
}
