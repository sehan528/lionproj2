-- 트렌딩 포스트 뷰 (a-1)
CREATE VIEW trending_posts_view AS
SELECT p.id, p.title, p.thumbnail_url, u.name AS author_name, p.update_date, COUNT(l.id) AS like_count
FROM posts p
         JOIN users u ON p.author_id = u.id
         LEFT JOIN likes l ON p.id = l.post_id
WHERE p.is_private = FALSE
GROUP BY p.id
ORDER BY like_count DESC, p.update_date DESC;

-- 최근 포스트 뷰 (a-2)
-- CREATE VIEW recent_posts_view AS
-- SELECT p.id, p.title, p.thumbnail_url, u.name AS author_name, p.creation_date
-- FROM posts p
--          JOIN users u ON p.author_id = u.id
-- WHERE p.is_private = FALSE
-- ORDER BY p.creation_date DESC;

CREATE OR REPLACE VIEW recent_posts_view AS
SELECT rv.id,
       rv.user_id,
       rv.post_id,
       p.title,
       p.thumbnail_url,
       p.context,
       u.name AS author_name,
       u.profile_img,
       p.creation_date,
       rv.view_date
FROM recent_views rv
         JOIN posts p ON rv.post_id = p.id
         JOIN users u ON p.author_id = u.id
ORDER BY rv.view_date DESC;


-- 사용자 프로필 뷰 (b-1, b-2, b-3)
CREATE VIEW user_profile_view AS
SELECT u.id, u.name, u.profile_img,
       COUNT(DISTINCT p.id) AS post_count,
       COUNT(DISTINCT s.id) AS series_count,
       a.context AS about_me
FROM users u
         LEFT JOIN posts p ON u.id = p.author_id AND p.is_private = FALSE
         LEFT JOIN post_series ps ON p.id = ps.post_id
         LEFT JOIN series s ON ps.series_id = s.id
         LEFT JOIN about_me a ON u.id = a.user_id
GROUP BY u.id;

-- 포스트 상세 뷰 (b-4)
CREATE VIEW post_detail_view AS
SELECT
    p.id,
    p.title,
    p.context,
    p.thumbnail_url,
    u.name AS author_name,
    u.user_id AS author_id,  -- 추가된 부분
    u.profile_img AS author_profile_img,
    p.creation_date,
    p.update_date,
    COUNT(DISTINCT l.id) AS like_count,
    GROUP_CONCAT(DISTINCT t.name) AS tags,
    MAX(s.name) AS series_name
FROM
    posts p
        JOIN users u ON p.author_id = u.id
        LEFT JOIN likes l ON p.id = l.post_id
        LEFT JOIN post_tags pt ON p.id = pt.post_id
        LEFT JOIN tags t ON pt.tag_id = t.id
        LEFT JOIN post_series ps ON p.id = ps.post_id
        LEFT JOIN series s ON ps.series_id = s.id
GROUP BY
    p.id, u.name, u.user_id, u.profile_img;  -- u.user_id 추가



-- 좋아요 목록 뷰 (C-1)
-- CREATE VIEW liked_posts_view AS
-- SELECT p.id, p.title, p.thumbnail_url, u.name AS author_name, p.creation_date, l.created_at AS liked_at
-- FROM likes l
--          JOIN posts p ON l.post_id = p.id
--          JOIN users u ON p.author_id = u.id
-- ORDER BY l.created_at DESC;

CREATE OR REPLACE VIEW liked_posts_view AS
SELECT
    l.id AS id,  -- 좋아요 테이블의 id를 뷰의 고유 식별자로 사용
    l.user_id,
    p.id AS post_id,
    p.title,
    p.thumbnail_url,
    u.name AS author_name,
    u.profile_img,       -- 추가된 컬럼
    p.creation_date,
    l.created_at AS liked_at
FROM
    likes l
        JOIN posts p ON l.post_id = p.id
        JOIN users u ON p.author_id = u.id
ORDER BY
    l.created_at DESC;


-- 최근 읽은 글 뷰 (C-2)
CREATE VIEW recent_read_posts_view AS
SELECT p.id, p.title, p.thumbnail_url, u.name AS author_name, p.creation_date, rv.view_date
FROM recent_views rv
         JOIN posts p ON rv.post_id = p.id
         JOIN users u ON p.author_id = u.id
ORDER BY rv.view_date DESC;

-- 검색 결과 뷰 (E-1)
CREATE VIEW search_posts_view AS
SELECT DISTINCT p.id, p.title, p.thumbnail_url, u.name AS author_name, p.creation_date,
                COUNT(DISTINCT l.id) AS like_count
FROM posts p
         JOIN users u ON p.author_id = u.id
         LEFT JOIN likes l ON p.id = l.post_id
         LEFT JOIN post_tags pt ON p.id = pt.post_id
         LEFT JOIN tags t ON pt.tag_id = t.id
WHERE p.is_private = FALSE
GROUP BY p.id;