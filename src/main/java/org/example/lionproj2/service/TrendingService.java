package org.example.lionproj2.service;

import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.entity.Post;
import org.example.lionproj2.exception.InvalidPeriodException;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrendingService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private static final Logger logger = LoggerFactory.getLogger(TrendingService.class);




    @Transactional(readOnly = true)
    public List<TrendingPostDTO> getTrendingPosts(String period, int page, int size) {
        LocalDateTime startDate = getStartDate(period);
        PageRequest pageRequest = PageRequest.of(page, size);

        log.info("Fetching trending posts from {} with page: {}, size: {}", startDate, page, size);

        List<Post> posts = postRepository.findTrendingPosts(startDate, pageRequest);
        log.info("Found {} posts for period: {} starting from date: {}", posts.size(), period, startDate);

        List<TrendingPostDTO> dtos = posts.stream()
                .map(postMapper::postToTrendingPostDTO)
                .collect(Collectors.toList());
        log.info("Mapped {} DTOs", dtos.size());

        return dtos;
    }

    private LocalDateTime getStartDate(String period) {
        return switch (period) {
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            default -> throw new IllegalArgumentException("Invalid period: " + period);
        };
    }
}


//    @Transactional(readOnly = true)
//    public List<TrendingPostDTO> getTrendingPosts(String period, int page, int size) {
//        LocalDateTime startDate = getStartDate(period);
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        return postRepository.findByIsPrivateFalseAndUpdateDateGreaterThanEqualOrderByLikesDesc(startDate, pageRequest)
//                .stream()
//                .map(postMapper::postToTrendingPostDTO)
//                .collect(Collectors.toList());
//    }