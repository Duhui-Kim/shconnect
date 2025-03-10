package com.shinhan.connector.controller;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.GiftAddRequest;
import com.shinhan.connector.dto.GiftAddResponse;
import com.shinhan.connector.dto.GiftSendResponse;
import com.shinhan.connector.dto.ResponseMessage;
import com.shinhan.connector.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift")
public class GiftController {
    private final GiftService giftService;

    @PostMapping
    public ResponseEntity<GiftAddResponse> createGift(@RequestBody GiftAddRequest giftAddRequest,
                                                      @RequestParam String option,
                                                      @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftService.createGift(giftAddRequest, option, user));
    }

    @DeleteMapping("/{giftNo}")
    public ResponseEntity<ResponseMessage> deleteGift(@PathVariable Integer giftNo, @RequestParam String option) {
        return ResponseEntity.ok(giftService.deleteGift(giftNo, option));
    }

    @GetMapping("/{giftNo}")
    public ResponseEntity<GiftSendResponse> getGift(@PathVariable Integer giftNo, @RequestParam String option) {
        return ResponseEntity.ok(giftService.getGift(giftNo, option));
    }

    @GetMapping("/list")
    public ResponseEntity<List<GiftSendResponse>> getAllGift(@RequestParam String option,
                                                             @RequestParam(required = false) Integer friendNo,
                                                             @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok(giftService.getAllGift(option, friendNo, user));
    }

}
