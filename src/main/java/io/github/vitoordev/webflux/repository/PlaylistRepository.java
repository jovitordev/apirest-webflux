package io.github.vitoordev.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.github.vitoordev.webflux.document.Playlist;

public interface PlaylistRepository extends ReactiveMongoRepository<Playlist, String>{

}
