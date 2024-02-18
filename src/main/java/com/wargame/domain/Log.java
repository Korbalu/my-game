package com.wargame.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Log")
@Data
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String header;
    @Column
    private String log;
    @Column
    private LocalDateTime createdAt;
    @ManyToOne
    private CustomUser owner;

    public Log(String header, String log) {
        this.header = header;
        this.log = log;
        this.createdAt = LocalDateTime.now();
    }
}
