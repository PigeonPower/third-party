package com.thirdParty.entity;

import com.thirdParty.enums.BookStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "modified_date")
    private Date modifiedDate = new Date();
}
