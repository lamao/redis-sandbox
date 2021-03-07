package org.example.sandbox.repository;

import org.example.sandbox.repository.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, String> {

    Optional<MessageEntity> findLast1ByPublishDate();
}
