package com.techjourney.api.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.techjourney.api.entity.TimelineCategory;
import com.techjourney.api.entity.TimelineEvent;
import com.techjourney.api.repository.TimelineEventRepository;

@Component
public class DataLoader {

    private final TimelineEventRepository repository;

    public DataLoader(TimelineEventRepository repository) {
        this.repository = repository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
    	
    	 if (repository.count() > 0) {
    	        return;
    	    }

        repository.save(new TimelineEvent(
                1983,
                TimelineCategory.GAMING,
                "Atari 2600",
                "My early exposure to technology came through video games and sparked a curiosity that would continue throughout my technology journey.",
                "Atari 2600"
        ));

        repository.save(new TimelineEvent(
                1985,
                TimelineCategory.COMPUTING,
                "Home Computers",
                "Home computers showed me that technology could do much more than play games and opened a new world of possibilities.",
                "Home Computers"
        ));

        repository.save(new TimelineEvent(
                1993,
                TimelineCategory.WEB,
                "The Early Internet",
                "The early internet introduced me to a world where computers were no longer standalone devices and information could travel across networks.",
                "Internet"
        ));

        repository.save(new TimelineEvent(
                1995,
                TimelineCategory.DEVELOPMENT,
                "Java",
                "Java became an important foundation of my professional development career and eventually a core technology for building enterprise applications.",
                "Java"
        ));

        repository.save(new TimelineEvent(
                1996,
                TimelineCategory.WEB,
                "HTML / Web Development",
                "Web development created a new way to build interactive experiences and introduced me to the technologies behind the growing World Wide Web.",
                "HTML / CSS / JavaScript"
        ));

        repository.save(new TimelineEvent(
                1999,
                TimelineCategory.MUSIC,
                "Napster / File Sharing",
                "Napster demonstrated how software, networking, and peer-to-peer technology could disrupt an entire industry and change how digital content was shared.",
                "Napster / P2P"
        ));

        repository.save(new TimelineEvent(
                2002,
                TimelineCategory.MOBILE,
                "Mobile Phones / Flip Phones",
                "Mobile phones changed how I communicated and made technology something that could travel everywhere with me.",
                "Mobile Phones"
        ));

        repository.save(new TimelineEvent(
                2008,
                TimelineCategory.DEVELOPMENT,
                "Databases & Persistence",
                "Relational databases, SQL, JDBC, and Hibernate became essential tools for persisting, retrieving, and managing data in enterprise Java applications.",
                "Oracle / SQL / JDBC / Hibernate"
        ));

        repository.save(new TimelineEvent(
                2014,
                TimelineCategory.DEVELOPMENT,
                "Spring Boot",
                "Spring Boot simplified Java application configuration while Maven provided the build and dependency management foundation for modern Java development.",
                "Spring Boot / Maven"
        ));

        repository.save(new TimelineEvent(
                2015,
                TimelineCategory.DEVELOPMENT,
                "REST APIs",
                "REST APIs became a core part of my development work, allowing applications and services to communicate through clean and reusable interfaces.",
                "REST / JSON / HTTP"
        ));

        repository.save(new TimelineEvent(
                2016,
                TimelineCategory.DEVELOPMENT,
                "Single-Page Applications (SPA)",
                "Modern frontend frameworks expanded my work into full-stack development, creating dynamic applications that communicate with backend REST services.",
                "Angular / TypeScript / React / Vue"
        ));

        repository.save(new TimelineEvent(
                2018,
                TimelineCategory.CLOUD,
                "AWS / Cloud Computing",
                "Cloud computing expanded my development experience beyond traditional infrastructure into scalable, distributed, and cloud-native applications.",
                "AWS"
        ));

        repository.save(new TimelineEvent(
                2020,
                TimelineCategory.CLOUD,
                "Containers & Orchestration",
                "Docker changed how applications were packaged and deployed, while Kubernetes and OpenShift provided platforms for orchestrating and scaling containers.",
                "Docker / Kubernetes / OpenShift"
        ));

        repository.save(new TimelineEvent(
                2022,
                TimelineCategory.DEVOPS,
                "CI/CD & Cloud-Native Development",
                "Automated builds, testing, security scanning, deployment pipelines, and observability became essential parts of delivering reliable software.",
                "Jenkins / Maven / CI/CD / DevOps"
        ));

        repository.save(new TimelineEvent(
                2023,
                TimelineCategory.AI,
                "Generative AI",
                "Generative AI introduced a new way to learn, create, write code, solve problems, and accelerate software development.",
                "Generative AI"
        ));

        repository.save(new TimelineEvent(
                2026,
                TimelineCategory.AI,
                "Agentic AI / AI Engineering",
                "AI evolved from generating content into agents capable of planning tasks, using tools, working across codebases, running tests, and collaborating throughout the development lifecycle.",
                "AI Agents / MCP / Tool Use / Coding Agents"
        ));
    }
}