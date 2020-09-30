package com.urcodebin.backend.repository;

import com.urcodebin.backend.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

}