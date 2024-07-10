
drop table posts;
CREATE TABLE users
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(50)  NOT NULL UNIQUE,
    password          VARCHAR(100) NOT NULL,
    name              VARCHAR(100) NOT NULL,
    email             VARCHAR(100) NOT NULL,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);


# -----------------------

-- posts 테이블 생성 (main_image_id 필드는 나중에 추가)
CREATE TABLE posts
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(200) NOT NULL,
    context       TEXT         NOT NULL,
    tag           VARCHAR(100),
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- images 테이블 생성
CREATE TABLE images
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id   BIGINT       NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

-- posts 테이블에 main_image_id 필드 추가 및 외래 키 관계 설정
ALTER TABLE posts
    ADD COLUMN main_image_id BIGINT,
    ADD FOREIGN KEY (main_image_id) REFERENCES images (id);

ALTER TABLE posts
    ADD COLUMN update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- user_posts 테이블 생성 (users와 posts의 다대다 관계를 위해)
CREATE TABLE user_posts
(
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE
);

-- postlist 뷰 생성 (게시글 목록을 위한 뷰)
CREATE VIEW postlist AS
SELECT p.id            AS post_id,
       u.username      AS author,
       p.title,
       p.main_image_id AS main_image,
       p.tag
FROM posts p
         JOIN
     user_posts up ON p.id = up.post_id
         JOIN
     users u ON up.user_id = u.id;

INSERT INTO roles (name) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

# -- users 테이블에 데이터 삽입
# INSERT INTO users (username, password, name, email, registration_date) VALUES
#                                                                            ('testuser', 'Vkdlf123!', 'sehan', 'sehan@mail.com', NOW()),
#                                                                            ('testadmin', 'Vkdlf123!', 'admin', 'admin@mail.com', NOW());

# -- roles 테이블에서 역할 ID를 가져오기
# SELECT id INTO @ROLE_USER_ID FROM roles WHERE name = 'ROLE_USER';
# SELECT id INTO @ROLE_ADMIN_ID FROM roles WHERE name = 'ROLE_ADMIN';
#
# -- users 테이블에서 사용자 ID를 가져오기
# SELECT id INTO @USER_ID FROM users WHERE username = 'testuser';
# SELECT id INTO @ADMIN_ID FROM users WHERE username = 'testadmin';
#
# -- user_roles 테이블에 데이터 삽입
# INSERT INTO user_roles (user_id, role_id) VALUES
#                                               (@USER_ID, @ROLE_USER_ID),
#                                               (@ADMIN_ID, @ROLE_ADMIN_ID);

select * from user_roles;

select * from posts;

select * from user_posts;

SELECT * FROM postlist;