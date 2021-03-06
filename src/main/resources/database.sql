-- Table: users
CREATE TABLE IF NOT EXISTS users (
                                     id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     full_name VARCHAR(255) NOT NULL,
                                     username VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     role_id INT NOT NULL,
                                     status_id INT NOT NULL
)
    ENGINE = InnoDB;

-- Table: roles
CREATE TABLE IF NOT EXISTS roles (
                                     id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table: statuses
CREATE TABLE IF NOT EXISTS statuses (
                                     id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table: courses
CREATE TABLE IF NOT EXISTS courses (
                                       id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       theme VARCHAR(255) NOT NULL,
                                       duration INT NOT NULL,
                                       teacher_id INT NOT NULL,
                                       condition_id INT NOT NULL,
                                       number_of_students INT NOT NULL
)
    ENGINE = InnoDB;

-- Table: conditions
CREATE TABLE IF NOT EXISTS conditions (
                                          id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table: marks
CREATE TABLE IF NOT EXISTS marks (
                                          id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(100) NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping course, students and marks: course_students_marks
CREATE TABLE IF NOT EXISTS course_students_marks (
                                                     course_id INT NOT NULL,
                                                     student_id INT NOT NULL,
                                                     mark_id INT NOT NULL ,

                                                     FOREIGN KEY (course_id) REFERENCES courses (id),
                                                     FOREIGN KEY (student_id) REFERENCES users (id),
                                                     FOREIGN KEY (mark_id) REFERENCES marks (id),

                                                     UNIQUE (course_id, student_id, mark_id)
)
    ENGINE = InnoDB;

-- Insert data
INSERT INTO roles VALUES (1, 'ROLE_STUDENT');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO roles VALUE (3, 'ROLE_TEACHER');

INSERT INTO statuses VALUES (1, 'UNLOCK');
INSERT INTO statuses VALUES (2, 'BLOCKED');

INSERT INTO conditions VALUES (1, 'NOT_STARTED');
INSERT INTO conditions VALUES (2, 'IN_PROGRESS');
INSERT INTO conditions VALUES (3, 'COMPLETED');

INSERT INTO marks VALUES (1, 'A');
INSERT INTO marks VALUES (2, 'B');
INSERT INTO marks VALUES (3, 'C');
INSERT INTO marks VALUES (4, 'D');
INSERT INTO marks VALUES (5, 'E');
INSERT INTO marks VALUES (6, '0');