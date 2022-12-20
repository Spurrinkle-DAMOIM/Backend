package com.example.spurrinkleteam.repository.Moim.Announcement;

import com.example.spurrinkleteam.entity.Moim.Announcement.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    List<Announcement> findAllByMeetName(String meetName);
}
