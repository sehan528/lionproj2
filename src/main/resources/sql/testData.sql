-- 사용자 추가
INSERT INTO users (user_id, password, name, email, profile_img, is_ban, registration_date) VALUES
                                                                                               ('user1', 'password1', 'John Doe', 'john@example.com', 'profile1.jpg', false, NOW()),
                                                                                               ('user2', 'password2', 'Jane Smith', 'jane@example.com', 'profile2.jpg', false, NOW()),
                                                                                               ('user3', 'password3', 'Bob Johnson', 'bob@example.com', 'profile3.jpg', false, NOW());

-- 게시물 추가
INSERT INTO posts (title, context, is_private, creation_date, update_date, thumbnail_url, author_id) VALUES
                                                                                                         ('First Post', 'This is the content of the first post', false, NOW() - INTERVAL 5 DAY, NOW() - INTERVAL 2 DAY, 'thumb1.jpg', 1),
                                                                                                         ('Second Post', 'Content of the second post', false, NOW() - INTERVAL 4 DAY, NOW() - INTERVAL 1 DAY, 'thumb2.jpg', 2),
                                                                                                         ('Third Post', 'Content of the third post', false, NOW() - INTERVAL 3 DAY, NOW(), 'thumb3.jpg', 3),
                                                                                                         ('Fourth Post', 'Content of the fourth post', false, NOW() - INTERVAL 2 DAY, NOW(), 'thumb4.jpg', 1),
                                                                                                         ('Fifth Post', 'Content of the fifth post', false, NOW() - INTERVAL 1 DAY, NOW(), 'thumb5.jpg', 2);

-- 좋아요 추가
INSERT INTO likes (user_id, post_id, created_at) VALUES
                                                     (1, 2, NOW() - INTERVAL 1 DAY),
                                                     (1, 3, NOW() - INTERVAL 2 DAY),
                                                     (2, 1, NOW() - INTERVAL 3 DAY),
                                                     (2, 3, NOW() - INTERVAL 4 DAY),
                                                     (3, 1, NOW() - INTERVAL 5 DAY),
                                                     (3, 2, NOW() - INTERVAL 6 DAY);

-- 태그 추가
INSERT INTO tags (name) VALUES
                            ('Technology'),
                            ('Programming'),
                            ('Web Development'),
                            ('Java'),
                            ('Spring Boot');

-- 게시물에 태그 연결
INSERT INTO post_tags (post_id, tag_id) VALUES
                                            (1, 1), (1, 2), (1, 4),
                                            (2, 2), (2, 3), (2, 5),
                                            (3, 1), (3, 3), (3, 5),
                                            (4, 2), (4, 4), (4, 5),
                                            (5, 1), (5, 2), (5, 3);

-- 최근 조회 추가
INSERT INTO recent_views (user_id, post_id, view_date) VALUES
                                                           (1, 3, NOW() - INTERVAL 1 HOUR),
                                                           (1, 4, NOW() - INTERVAL 2 HOUR),
                                                           (2, 1, NOW() - INTERVAL 3 HOUR),
                                                           (2, 5, NOW() - INTERVAL 4 HOUR),
                                                           (3, 2, NOW() - INTERVAL 5 HOUR);