package com.gk.hotel.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.gk.hotel.AbstractRestHandler;
import com.gk.hotel.exception.DataFormatException;
import com.gk.hotel.model.Hotel;
import com.gk.hotel.service.HotelService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/gk/hotels")
@Api(tags = { "hotels" })
public class HotelController extends AbstractRestHandler {

	@Autowired
	private HotelService hotelService;

	@PostMapping(value = "", consumes = { "application/json", "application/xml" }, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Add a new hotel.")
	public void createHotel(@RequestBody Hotel hotel, HttpServletRequest request, HttpServletResponse response) {
		Hotel addedHotel = hotelService.createHotel(hotel);
		response.setHeader("Location", request.getRequestURL().append("/").append(addedHotel.getId()).toString());
	}

	@GetMapping(value = "/{id}/{rating}", produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "update rating by id")
	public void getHotel(@ApiParam(value = "hotel id.", required = true) @PathVariable("id") Long id,
			@ApiParam(value = "rating", required = true) @PathVariable("rating") int rating, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Hotel hotel = hotelService.getHotel(id);
		checkResourceFound(hotel);
		hotelService.updateRating(hotel, rating);
	}

	@GetMapping(value = "", produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List of all hotels.")
	public @ResponseBody Page<Hotel> getAllHotel(
			@ApiParam(value = "page number", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@ApiParam(value = "page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return hotelService.getAllHotels(page, size);
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get hotel by id.")
	public @ResponseBody Hotel getHotel(@ApiParam(value = "hotel id.", required = true) @PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Hotel hotel = hotelService.getHotel(id);
		checkResourceFound(hotel);
		return hotel;
	}

	@PutMapping(value = "/{id}", consumes = { "application/json", "application/xml" }, produces = { "application/json",
			"application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Update hotel")
	public void updateHotel(@ApiParam(value = "hotel id", required = true) @PathVariable("id") Long id,
			@RequestBody Hotel hotel, HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(hotelService.getHotel(id));
		if (id != hotel.getId())
			throw new DataFormatException("ID doesn't match!");
		hotelService.updateHotel(hotel);
	}

	@DeleteMapping(value = "/{id}", produces = { "application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete hotel")
	public void deleteHotel(@ApiParam(value = "hotel id", required = true) @PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(hotelService.getHotel(id));
		hotelService.deleteHotel(id);
	}
}
