package es.udc.tfg.backend.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.udc.tfg.backend.model.entities.Tweet;

public interface TweetDao extends PagingAndSortingRepository<Tweet, Long>, ExtensionTweetDao {

}
