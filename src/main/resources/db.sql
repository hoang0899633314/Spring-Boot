create database student_management_c06;
use student_management_c06;

create table student(
                        id int primary key auto_increment,
                        name char(45) not null,
                        score double
);

insert into student (name, score) VALUES ('Lợi', 2.0);
insert into student (name, score) VALUES ('Linh', 4.0);

select id, name, score from student;

select id, name, score from student where id = ?;

insert into student(name, score) VALUES (?, ?);



