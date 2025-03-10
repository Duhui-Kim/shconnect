package com.shinhan.connector.service;

import com.shinhan.connector.config.jwt.UserDetailsImpl;
import com.shinhan.connector.dto.*;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.repository.FriendRepository;
import com.shinhan.connector.repository.MemberRepository;
import com.shinhan.connector.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    // 새로운 일정을 추가하는 메서드
    public ScheduleAddResponse addSchedule(ScheduleAddRequest request, UserDetailsImpl user) {
        log.info("[일정 등록] 일정등록 요청. {}, {}", request.toString(), user.getUserId());

        // 저장할 엔티티 생성
        Schedule schedule = request.toScheduleEntity();
        schedule.setFriend(friendRepository.findById(request.getFriendNo()).orElseThrow(NoSuchElementException::new));
        schedule.setMember(memberRepository.findMemberById(user.getUserId()).orElseThrow(NoSuchElementException::new));

        // 생성한 엔티티 저장
        scheduleRepository.save(schedule);
        scheduleRepository.flush();

        // API 응답 생성
        return new ScheduleAddResponse(schedule);
    }

    public ResponseMessage deleteSchedule(Integer scheduleNo) {
        scheduleRepository.deleteById(scheduleNo);

        return new ResponseMessage("삭제가 완료되었습니다.");
    }

    // 일정을 상세조회하는 메서드
    public ScheduleResponse selectSchedule(Integer scheduleNo) {
        return new ScheduleResponse(scheduleRepository.findById(scheduleNo).orElseThrow(NoSuchElementException::new));
    }

    // 일정 목록을 조회하는 메서드
    public List<ScheduleListResponse> selectAllSchedule(UserDetailsImpl user) {
        return scheduleRepository.findByMember(user.getId()).stream()
                .map(ScheduleListResponse::new)
                .collect(Collectors.toList());
    }
}
