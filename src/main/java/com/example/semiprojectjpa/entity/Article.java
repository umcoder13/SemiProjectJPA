package com.example.semiprojectjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = true)
    @NotEmpty
    private String author;

    @Column
    @NotEmpty
    private String username;

    @Column(length = 100, nullable = true)
    @NotEmpty(message = "제목을 입력하세요.")
    private String title;

    @Column(length = 255, nullable = true)
    @NotEmpty(message = "본문을 입력하세요")
    private String mainarticle;

    @Column(nullable = true, updatable = false)
    @CreatedDate
    private LocalDateTime time;

    @PrePersist
    public void time() {
        this.time = LocalDateTime.now();
    }
}
