package com.studycollaboproject.scope.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    PostRepository postRepository;
    @Mock
    BookmarkRepository bookmarkRepository;
    @Mock
    TechStackRepository techStackRepository;
    @Mock
    TeamRepository teamRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("새로운 Post 추가")
    void writePost() {

        //given
        String title = "제목입니다.";
        String summary = "자기소개입니다.";
        String contents = "컨텐츠입니다.";
        String techStack = "Spring;React;jQuery";
        Integer totalMember = 10;
        Integer recruitmentMember = 6;
        String projectStatus = "진행중";
        String startDate = "2021-10-27";
        String endDate = "2021-11-27";

        PostRequestDto postRequestDto = new PostRequestDto(
                title,
                summary,
                contents,
                techStack,
                totalMember,
                recruitmentMember,
                projectStatus,
                startDate,
                endDate
        );

        PostService postService = new PostService(postRepository, bookmarkRepository, techStackRepository, teamRepository, userRepository);

        //when
        ResponseDto result = postService.writePost(postRequestDto);
        Object data = result.getData();
        //then
        assertEquals(data,"");
    }

    @Test
    @DisplayName("Post 삭제")
    void deletePost(){
        //given

        String title = "제목입니다.";
        String summary = "자기소개입니다.";
        String contents = "컨텐츠입니다.";
        String techStack = "Spring;React;jQuery";
        Integer totalMember = 10;
        Integer recruitmentMember = 6;
        String projectStatus = "진행중";
        String startDate = "2021-10-27";
        String endDate = "2021-11-27";
        Long id = 1L;

        PostRequestDto postRequestDto = new PostRequestDto(
                title,
                summary,
                contents,
                techStack,
                totalMember,
                recruitmentMember,
                projectStatus,
                startDate,
                endDate


        );

        Post post = new Post(postRequestDto);
        PostService postService = new PostService(postRepository, bookmarkRepository, techStackRepository, teamRepository, userRepository);
        when(postRepository.findById(id))
                .thenReturn(Optional.of(post));


        // when
        ResponseDto result = postService.deletePost(id);
        Object data = result.getData();

        //then
        assertEquals(data,"");

    }
    @Test
    @DisplayName("Post 읽어오기")
    void readPost(){
        String title = "제목입니다.";
        String summary = "자기소개입니다.";
        String contents = "컨텐츠입니다.";
        String techStack = "Spring;React;jQuery";
        Integer totalMember = 10;
        Integer recruitmentMember = 6;
        String projectStatus = "진행중";
        String startDate = "2021-10-27";
        String endDate = "2021-11-27";
        Long id = 1L;


        PostRequestDto postRequestDto = new PostRequestDto(
                title,
                summary,
                contents,
                techStack,
                totalMember,
                recruitmentMember,
                projectStatus,
                startDate,
                endDate
        );

        Post post = new Post(postRequestDto);

    }
}