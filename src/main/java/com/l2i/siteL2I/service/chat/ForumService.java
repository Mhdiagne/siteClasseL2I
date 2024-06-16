package com.l2i.siteL2I.service.chat;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.chat.ForumRequest;
import com.l2i.siteL2I.dto.chat.ForumResponse;
import com.l2i.siteL2I.model.chat.Forum;
import com.l2i.siteL2I.repository.chat.ForumRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class ForumService {

    private final ForumRepository forumRepository;

    public ResponseEntity<List<ForumResponse>> getAll() {
        try {
            List<Forum> items = new ArrayList<>();
            forumRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<ForumResponse> responseItems = items.stream().map(this::mapToForumResponse).collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ForumResponse> getById(Integer id) {
        Optional<Forum> existingItemOptional = forumRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToForumResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ForumResponse> create(ForumRequest requestItem) {
        try {
            ForumResponse saveResponseItem = mapToForumResponse(forumRepository.save(mapToForum(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<ForumResponse> update(Integer id, ForumRequest requestItem) {
        Optional<Forum> existingItemOptional = forumRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Forum existingItem = existingItemOptional.get();
            
            existingItem.setName(requestItem.getName());
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToForumResponse(forumRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            forumRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private ForumResponse mapToForumResponse(Forum forum) {
        return ForumResponse.builder()
        .id(forum.getId())
        .name(forum.getName())
        .lastModifiedAt(forum.getLastModifiedAt())
        .messages(forum.getMessages())
        .creatAt(forum.getCreatAt())
        .createdBy(forum.getCreatedBy())
        .build();
    }

    private Forum mapToForum(ForumRequest forumRequest) {
        return Forum.builder()
        .name(forumRequest.getName())
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}