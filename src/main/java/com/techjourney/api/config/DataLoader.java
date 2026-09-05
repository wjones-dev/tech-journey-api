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

        repository.save(new TimelineEvent(
                1983,
                TimelineCategory.GAMING,
                "Atari 2600",
                "My early exposure to technology came through video games.",
                "Atari 2600"
        ));

        repository.save(new TimelineEvent(
                1984,
                TimelineCategory.MUSIC,
                "Sony Walkman",
                "Music became personal and portable, changing how I experienced technology.",
                "Sony Walkman"
        ));

        repository.save(new TimelineEvent(
                1985,
                TimelineCategory.COMPUTING,
                "Home Computers",
                "My early exposure to computers showed me that technology could do much more than play games.",
                "Home Computers"
        ));

        repository.save(new TimelineEvent(
                1990,
                TimelineCategory.COMPUTING,
                "Windows / Personal Computing",
                "Computers became more approachable and started becoming part of everyday life.",
                "Windows"
        ));

        repository.save(new TimelineEvent(
                1993,
                TimelineCategory.WEB,
                "The Early Internet",
                "The internet introduced me to a world where computers were no longer standalone devices.",
                "Internet"
        ));

        repository.save(new TimelineEvent(
                1995,
                TimelineCategory.DEVELOPMENT,
                "Java",
                "Java became an important foundation of my professional development career.",
                "Java"
        ));

        repository.save(new TimelineEvent(
                1996,
                TimelineCategory.WEB,
                "HTML / Web Development",
                "Web development created a new way to build and interact with software.",
                "HTML"
        ));

        repository.save(new TimelineEvent(
                1999,
                TimelineCategory.MUSIC,
                "Napster / File Sharing",
                "Software and networking began changing how an entire industry distributed music.",
                "Napster"
        ));

        repository.save(new TimelineEvent(
                2001,
                TimelineCategory.MUSIC,
                "Apple iPod",
                "Portable digital experiences became a major part of everyday technology.",
                "iPod"
        ));

        repository.save(new TimelineEvent(
                2004,
                TimelineCategory.CONNECTIVITY,
                "Bluetooth",
                "Wireless communication between devices became increasingly common.",
                "Bluetooth"
        ));

        repository.save(new TimelineEvent(
                2007,
                TimelineCategory.MOBILE,
                "iPhone",
                "Smartphones transformed expectations around computing, applications, and connectivity.",
                "iPhone"
        ));

        repository.save(new TimelineEvent(
                2008,
                TimelineCategory.DEVELOPMENT,
                "Enterprise Java",
                "I moved deeper into real-world enterprise Java applications and development.",
                "Enterprise Java"
        ));

        repository.save(new TimelineEvent(
                2010,
                TimelineCategory.DEVELOPMENT,
                "Spring Framework",
                "Spring became an important toolkit for building enterprise Java applications and architectures.",
                "Spring Framework"
        ));

        repository.save(new TimelineEvent(
                2014,
                TimelineCategory.DEVELOPMENT,
                "Spring Boot",
                "Spring Boot simplified application configuration and helped modernize the way I built Java services.",
                "Spring Boot"
        ));

        repository.save(new TimelineEvent(
                2015,
                TimelineCategory.DEVELOPMENT,
                "REST APIs",
                "REST APIs became a core part of building service-oriented applications and connecting systems.",
                "REST"
        ));

        repository.save(new TimelineEvent(
                2016,
                TimelineCategory.DEVELOPMENT,
                "Angular",
                "Angular expanded my development experience into modern full-stack web applications.",
                "Angular"
        ));

        repository.save(new TimelineEvent(
                2018,
                TimelineCategory.CLOUD,
                "AWS / Cloud Computing",
                "My development work expanded into cloud-based and cloud-native applications.",
                "AWS"
        ));

        repository.save(new TimelineEvent(
                2020,
                TimelineCategory.CLOUD,
                "Docker / Kubernetes / OpenShift",
                "Containers and orchestration became an important part of deploying and operating applications.",
                "Docker / Kubernetes / OpenShift"
        ));

        repository.save(new TimelineEvent(
                2022,
                TimelineCategory.DEVOPS,
                "CI/CD & Cloud-Native Development",
                "Jenkins, pipelines, security scanning, deployment automation, and observability became key parts of modern development.",
                "Jenkins / CI/CD"
        ));

        repository.save(new TimelineEvent(
                2023,
                TimelineCategory.AI,
                "Generative AI",
                "Generative AI represents the latest evolution in my technology journey, from early computers and gaming to AI-assisted development.",
                "Generative AI"
        ));
    }
}