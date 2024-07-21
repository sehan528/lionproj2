package org.example.lionproj2.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.SeriesDTO;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.entity.*;
import org.example.lionproj2.exception.UserNotFoundException;
import org.example.lionproj2.mapper.UserProfileMapper;
import org.example.lionproj2.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final SeriesRepository seriesRepository;
    private final PostSeriesRepository postSeriesRepository;
    private final AboutMeRepository aboutMeRepository;
    private final UserProfileMapper userProfileMapper;

    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(String username) {
        log.info("Fetching user profile for username: {}", username);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new UserNotFoundException("User not found: " + username);
                });
        log.info("User found: {}", user);

        List<Post> posts = postRepository.findByAuthorOrderByCreationDateDesc(user);
        List<PostSummaryDTO> postSummaries = posts.stream()
                .map(userProfileMapper::toPostSummaryDTO)
                .collect(Collectors.toList());

        List<Series> seriesList = seriesRepository.findByPostSeries_Post_AuthorOrderByName(user);
        List<SeriesDTO> seriesDTOs = seriesList.stream()
                .map(series -> {
                    List<PostSummaryDTO> seriesPosts = postSeriesRepository.findBySeriesOrderByPostCreationDateDesc(series)
                            .stream()
                            .map(PostSeries::getPost)
                            .map(userProfileMapper::toPostSummaryDTO)
                            .collect(Collectors.toList());
                    return userProfileMapper.toSeriesDTO(series, seriesPosts);
                })
                .collect(Collectors.toList());
        String aboutMe = getUserAboutMe(username);
        return userProfileMapper.toUserProfileDTO(user, postSummaries, seriesDTOs, aboutMe);
    }

    @Transactional(readOnly = true)
    public String getUserAboutMe(String username) {
        log.info("Fetching about me for username: {}", username);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new UserNotFoundException("User not found: " + username);
                });

        AboutMe aboutMe = aboutMeRepository.findByUser(user)
                .orElse(null);

        return aboutMe != null ? aboutMe.getContext() : "";
    }

    @Transactional(readOnly = true)
    public String getUsernameById(Long userId) {
        log.info("Fetching username for userId: {}", userId);
        return userRepository.findById(userId)
                .map(User::getName)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new UserNotFoundException("User not found with id: " + userId);
                });
    }

    @Transactional(readOnly = true)
    public String getUserProfileImgById(Long userId) {
        return userRepository.findById(userId)
                .map(User::getProfileImg)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Transactional
    public void updateAboutMe(Long userId, String aboutMeContent) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AboutMe aboutMe = aboutMeRepository.findByUser(user)
                .orElse(new AboutMe());

        aboutMe.setUser(user);
        aboutMe.setContext(aboutMeContent);

        aboutMeRepository.save(aboutMe);
    }

    public String getUserIdByUsername(String username) {
        log.info("유저 UserId로 Username 찾는 단계 [Service]");
        return userRepository.findByName(username)
                .map(User::getUserId)
                .orElse(null);
    }


}