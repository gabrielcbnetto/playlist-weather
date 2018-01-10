package br.com.playlist.weather.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.playlist.weather.model.PlayList;

@RestController
public class PlayListController {

	@RequestMapping("/playlist/city/")
	public PlayList playListCity(@RequestParam(value="city") String city) {
		return null;
	}
}
