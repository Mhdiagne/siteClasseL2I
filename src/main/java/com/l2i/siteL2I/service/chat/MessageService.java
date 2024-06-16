package com.l2i.siteL2I.service.chat;

import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.l2i.siteL2I.dto.chat.MessageRequest;
import com.l2i.siteL2I.dto.chat.MessageResponse;
import com.l2i.siteL2I.model.chat.Message;
import com.l2i.siteL2I.repository.chat.MessageRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@Transactional
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public ResponseEntity<List<MessageResponse>> getAll() {
        try {
            List<Message> items = new ArrayList<>();
            messageRepository.findAll().forEach(items::add);
    
            if (items.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    
            List<MessageResponse> responseItems = items.stream().map(this::mapToMessageResponse).collect(Collectors.toList());
            return new ResponseEntity<>(responseItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<MessageResponse> getById(Integer id) {
        Optional<Message> existingItemOptional = messageRepository.findById(id);

        if (existingItemOptional.isPresent()) {
            return new ResponseEntity<>(mapToMessageResponse(existingItemOptional.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<MessageResponse> create(MessageRequest requestItem) {
        try {
            MessageResponse saveResponseItem = mapToMessageResponse(messageRepository.save(mapToMessage(requestItem)));
            return new ResponseEntity<>(saveResponseItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<MessageResponse> update(Integer id, MessageRequest requestItem) {
        Optional<Message> existingItemOptional = messageRepository.findById(id);
        if (existingItemOptional.isPresent()) {
            Message existingItem = existingItemOptional.get();
            
            existingItem.setContent(requestItem.getContent());
            existingItem.setForum(requestItem.getForum());
            existingItem.setUser(requestItem.getAuthor());
            existingItem.setLastModifiedAt(LocalDateTime.now());

            return new ResponseEntity<>(mapToMessageResponse(messageRepository.save(existingItem)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> delete(Integer id) {
        try {
            messageRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    private MessageResponse mapToMessageResponse(Message message) {
        return MessageResponse.builder()
        .id(message.getId())
        .content(message.getContent())
        .forum(message.getForum())
        .author(message.getUser())
        .creatAt(message.getCreatAt())
        .lastModifiedAt(message.getLastModifiedAt())
        .createdBy(message.getCreatedBy())
        .lastModifiedBy(message.getLastModifiedBy())
        .build();
    }

    private Message mapToMessage(MessageRequest messageRequest) {
        return Message.builder()
        .content(messageRequest.getContent())
        .forum(messageRequest.getForum())
        .user(messageRequest.getAuthor())
        .creatAt(LocalDateTime.now())
        .lastModifiedAt(LocalDateTime.now())
        .build();
    }
}
