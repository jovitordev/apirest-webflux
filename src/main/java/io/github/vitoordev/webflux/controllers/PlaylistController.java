package io.github.vitoordev.webflux.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.github.vitoordev.webflux.document.Playlist;
import io.github.vitoordev.webflux.services.PlaylistService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import java.util.concurrent.TimeUnit;

@RestController
public class PlaylistController {

	@Autowired
	PlaylistService service;

	@GetMapping(value = "/playlist")
	public Flux<Playlist> getPlaylist() {
		return service.findAll();
	}

	@GetMapping(value = "/playlist/{id}")
	public Mono<Playlist> getPlaylistId(@PathVariable String id) {
		return service.findById(id);
	}

	@PostMapping(value = "/playlist")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Playlist> save(@RequestBody Playlist playlist) {
		return service.save(playlist);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable String id, @RequestBody Playlist playlistAtualizado) {
		service.findById(id).map(playlist -> {
			playlist.setNome(playlistAtualizado.getNome());
			return service.save(playlist);
		});

	}

	@GetMapping(value = "/playlist/webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Tuple2<Long, Playlist>> getPlaylistByWebflux() {

		System.out.println("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now());
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
		Flux<Playlist> playlistFlux = service.findAll();

		return Flux.zip(interval, playlistFlux);

	}

	@GetMapping(value = "/playlist/mvc")
	public List<String> getPlaylistByMvc() throws InterruptedException {

		System.out.println("---Start get Playlists by MVC--- " + LocalDateTime.now());

		List<String> playlistList = new ArrayList<>();
		playlistList.add("Java 8");
		playlistList.add("Spring Security");
		playlistList.add("Github");
		playlistList.add("Deploy de uma aplicação java no IBM Cloud");
		playlistList.add("Bean no Spring Framework");
		TimeUnit.SECONDS.sleep(15);

		return playlistList;

	}

}
