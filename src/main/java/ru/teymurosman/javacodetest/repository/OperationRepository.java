package ru.teymurosman.javacodetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.teymurosman.javacodetest.model.Operation;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
}
