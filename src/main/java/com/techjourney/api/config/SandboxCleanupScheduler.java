package com.techjourney.api.config;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techjourney.api.repository.SandboxEventRepository;

@Component
public class SandboxCleanupScheduler {

	private static final int RETENTION_DAYS = 5;

	private final SandboxEventRepository sandboxEventRepository;

	public SandboxCleanupScheduler(SandboxEventRepository sandboxEventRepository) {
		this.sandboxEventRepository = sandboxEventRepository;
	}

	@Scheduled(cron = "0 0 3 * * *")
	@Transactional
	public void cleanupExpiredSandboxEvents() {

		LocalDateTime cutoff = LocalDateTime.now().minusDays(RETENTION_DAYS);

		long deletedCount = sandboxEventRepository.deleteByCreatedAtBefore(cutoff);

		//Remove Syop
		if (deletedCount > 0) {
			System.out.println("Sandbox cleanup removed " + deletedCount + " expired event(s).");
		}
	}
}