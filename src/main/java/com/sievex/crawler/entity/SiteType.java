package com.sievex.crawler.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="m_site_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SiteType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private int typeId;
    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;
    @Column(name = "type_alias", nullable = false, unique = true)
    private String typeAlias;
    @Column(name = "type_description")
    private String typeDescription;
    @Column(name = "type_status")
    private String typeStatus;
    @JsonIgnore
    @Column(name = "inserted_time")
    private String insertedTime;
    @JsonIgnore
    @Column(name = "updated_time")
    private String updatedTime;

    public SiteType(String typeName, String typeAlias, String typeDescription, String typeStatus) {
        this.typeName = typeName;
        this.typeAlias = typeAlias;
        this.typeDescription = typeDescription;
        this.typeStatus = typeStatus;
    }
}
