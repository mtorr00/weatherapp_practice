package com.tts.weatherapp;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long> {
	List<Request> findTop10ByOrderByCreatedAt();
}
