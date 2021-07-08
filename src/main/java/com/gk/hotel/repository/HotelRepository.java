package com.gk.hotel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gk.hotel.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
	Hotel findHotelByCity(String city);

	Hotel findHotelByName(String name);

	Page findAll(Pageable pageable);
}
