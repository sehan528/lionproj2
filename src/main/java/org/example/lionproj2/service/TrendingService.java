package org.example.lionproj2.service;

import org.example.lionproj2.dto.TrendingPostDTO;
import org.example.lionproj2.exception.InvalidPeriodException;
import org.example.lionproj2.mapper.PostMapper;
import org.example.lionproj2.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendingService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public List<TrendingPostDTO> getTrendingPosts(String period, int page, int size) {
        LocalDateTime startDate = getStartDate(period);
        PageRequest pageRequest = PageRequest.of(page, size);

        return postRepository.findByIsPrivateFalseAndUpdateDateGreaterThanEqualOrderByLikesDesc(startDate, pageRequest)
                .stream()
                .map(postMapper::postToTrendingPostDTO)
                .collect(Collectors.toList());
    }

    private LocalDateTime getStartDate(String period) {
        LocalDateTime now = LocalDateTime.now();
        switch (period) {
            case "week":
                return now.minusWeeks(1);
            case "month":
                return now.minusMonths(1);
            default:
                throw new InvalidPeriodException("Invalid period: " + period);
        }
    }
}