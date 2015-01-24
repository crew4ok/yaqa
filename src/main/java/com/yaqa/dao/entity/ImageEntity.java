package com.yaqa.dao.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagesIdSequence")
    @SequenceGenerator(name = "imagesIdSequence", sequenceName = "IMAGES_ID_SEQ")
    private Long id;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotNull
    private byte[] content;

    /**
     * Supposed to be used only by hibernate.
     */
    @Deprecated
    public ImageEntity() {
    }

    public ImageEntity(byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
