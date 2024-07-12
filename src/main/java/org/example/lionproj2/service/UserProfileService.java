package org.example.lionproj2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.PostSummaryDTO;
import org.example.lionproj2.dto.SeriesDTO;
import org.example.lionproj2.dto.UserProfileDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.entity.Series;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.entity.PostSeries;
import org.example.lionproj2.exception.UserNotFoundException;
import org.example.lionproj2.mapper.UserProfileMapper;
import org.example.lionproj2.repository.PostRepository;
import org.example.lionproj2.repository.PostSeriesRepository;
import org.example.lionproj2.repository.SeriesRepository;
import org.example.lionproj2.repository.UserRepository;
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

        return userProfileMapper.toUserProfileDTO(user, postSummaries, seriesDTOs);
    }
}