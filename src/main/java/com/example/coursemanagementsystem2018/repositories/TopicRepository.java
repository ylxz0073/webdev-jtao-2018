package com.example.coursemanagementsystem2018.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.coursemanagementsystem2018.models.Lesson;
import com.example.coursemanagementsystem2018.models.Topic;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

}