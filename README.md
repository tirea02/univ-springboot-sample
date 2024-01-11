intelij에서 작성한 프로젝트로 intelij 사용시 바로 연동가능.


eclipse 에서 사용할 경우 git clone 후 import gradle project 통해 import.
lombok 다운후에 eclipse 추가.
gradle refresh -> project clean -> refresh 후 실행.

eclipse의 경우 build.gradle을 통해 lombok을 추가해도 따로 공식 홈페이지에서 다운, 실행 후에 eclipse를 등록해 줘야 적용가능한 현상 확인.

아래는 mysql DB 테이블 스키마 구조 및 샘플 데이터 sql 코드
application.properties 파일에서 DB 관련 사항 본인에 맞게 수정 후 사용
```java
spring.datasource.url=jdbc:mysql://localhost:3306/{your database name}
spring.datasource.username={your database user name default : root }
spring.datasource.password={your user password default : 1234 or 12345678}


```



 ```sql
create database board;

//사용자 만들고 권한 주는 코드 root 사용시 불필요
CREATE USER 'board_user'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL PRIVILEGES ON board.* TO ' board_user'@'localhost';
FLUSH PRIVILEGES;

show databases;

use board;

drop TABLE  postReply;
drop TABLE  post;
drop TABLE userAccount ;

CREATE TABLE userAccount (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    phone VARCHAR(20)
);

drop TABLE  post;
CREATE TABLE post (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    viewCount INT DEFAULT 0,
    img_file VARCHAR(255),
    userId INT,
    FOREIGN KEY (userId) REFERENCES userAccount(id)
);

userId -> userAccountId 로 수정함
drop TABLE  postReply;
CREATE TABLE postReply (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userAccountId INT,  
    postId INT,
    content TEXT NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES userAccount(id),
    FOREIGN KEY (postId) REFERENCES post(id)
);

commit ;

sample data 
INSERT INTO userAccount (userId, password, name, phone) VALUES
('user1', '1234', 'Alice', '123-456-7890'),
('user2', '1234', 'Bob', '234-567-8901'),
('user3', '1234', 'Charlie', '345-678-9012');


INSERT INTO post (title, content, date, viewCount, img_file, userId) VALUES
('First Post', 'Content of the first post', NOW(), 0, 'image1.jpg', 1),
('Second Post', 'Content of the second post', NOW(), 0, 'image2.jpg', 2),
('Third Post', 'Content of the third post', NOW(), 0, 'image3.jpg', 3),
('Fourth Post', 'Content of the fourth post', NOW(), 0, 'image4.jpg', 1),
('Fifth Post', 'Content of the fifth post', NOW(), 0, 'image5.jpg', 2),
('Sixth Post', 'Content of the sixth post', NOW(), 0, 'image6.jpg', 3),
('Seventh Post', 'Content of the seventh post', NOW(), 0, 'image7.jpg', 1),
('Eighth Post', 'Content of the eighth post', NOW(), 0, 'image8.jpg', 2),
('Ninth Post', 'Content of the ninth post', NOW(), 0, 'image9.jpg', 3),
('Tenth Post', 'Content of the tenth post', NOW(), 0, 'image10.jpg', 1);
```



