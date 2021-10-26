package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.TeamRequestDto;
import com.studycollaboproject.scope.dto.TeamResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.TeamService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Team Controller", description = "팀원 조회 및 팀원 승인")
public class TeamRestController {

    private final TeamService teamService;
    private final UserService userService;
    private final PostService postService;

    @Operation(summary = "팀원 승인/거절")
    @PostMapping("/api/team/{postId}")
    public ResponseDto acceptMember(@PathVariable Long postId, @RequestBody TeamRequestDto requestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("POST, [{}], /api/team/{}, userId={}, accept={}", MDC.get("UUID"), postId, requestDto.getUserId(), requestDto.getAccept());

        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        //로그인 사용자 불러오기
        Post post = postService.loadPostIfOwner(postId, requestDto.getUserId());  //로그인 사용자가 해당 게시글의 생성자 인지 확인
        User applyUser = userService.loadUserByUserId(requestDto.getUserId());    //지원자 정보 확인
        teamService.acceptMember(post, applyUser, requestDto.getAccept());        //지원자 승인/거절
        List<TeamResponseDto> responseDto = teamService.getMember(postId);
        return new ResponseDto("200", "", responseDto);
    }

    @Operation(summary = "팀원 조회")
    @GetMapping("/api/team/{postId}")
    public ResponseDto getMember(@PathVariable Long postId) {
        log.info("GET, [{}], /api/team/{}", MDC.get("UUID"), postId);
        List<TeamResponseDto> responseDto = teamService.getMember(postId);
        return new ResponseDto("200", "", responseDto);
    }
}