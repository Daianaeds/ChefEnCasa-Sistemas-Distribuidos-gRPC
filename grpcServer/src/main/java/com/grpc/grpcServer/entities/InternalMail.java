package com.grpc.grpcServer.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "Internal_mail")
public class InternalMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //emisor
    @ManyToOne
    private User source;

    //asunto
    @Lob
    @Column(name="subject")
    private String subject;

    //receptor
    @ManyToOne
    private User destination;

    //respuesta
    @Lob
    @Column(name="subject_reply")
    private String subjectReply;

}
