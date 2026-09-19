package com.techjourney.api.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techjourney.api.entity.SandboxEvent;

public interface SandboxEventRepository
        extends JpaRepository<SandboxEvent, Long> {
	
    long deleteByCreatedAtBefore(LocalDateTime cutoff);


}