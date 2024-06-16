package com.l2i.siteL2I.controller.chat;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.l2i.siteL2I.dto.chat.MessageRequest;
import com.l2i.siteL2I.dto.chat.MessageResponse;
import com.l2i.siteL2I.service.chat.MessageService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponse>> getAll() {
        return messageService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<MessageResponse> getById(@PathVariable("id") Integer id) {
        return messageService.getById(id);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody MessageRequest requestItem) {
        return messageService.create(requestItem);
    }

    @PutMapping("{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable("id") Integer id, @RequestBody MessageRequest requestItem) {
        return messageService.update(id, requestItem);
    }
    

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        return messageService.delete(id);
    }
}

