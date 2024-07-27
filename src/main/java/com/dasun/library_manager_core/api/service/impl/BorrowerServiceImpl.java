package com.dasun.library_manager_core.api.service.impl;

import com.dasun.library_manager_core.api.dto.BorrowerDto;
import com.dasun.library_manager_core.api.entity.Borrower;
import com.dasun.library_manager_core.api.exception.DataNotFoundException;
import com.dasun.library_manager_core.api.mapper.BorrowerMapper;
import com.dasun.library_manager_core.api.repository.BorrowerRepository;
import com.dasun.library_manager_core.api.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BorrowerServiceImpl implements BorrowerService {
    private final BorrowerRepository borrowerRepository;

    @Override
    public BorrowerDto addBorrower(BorrowerDto borrowerDto) {
        log.info("Starting the process to create a new borrower.");

        // Convert BorrowerDto to Borrower entity
        Borrower newBorrower = BorrowerMapper.INSTANCE.borrowerDtoToBorrower(borrowerDto);
        log.debug("Converted BorrowerDto to Borrower entity: {}", newBorrower);

        // Save the Borrower entity to the repository
        Borrower savedBorrower = borrowerRepository.save(newBorrower);
        log.info("Saved new borrower to the repository with ID: {}", savedBorrower.getId());

        // Convert the saved Borrower entity back to BorrowerDto
        BorrowerDto savedBorrowerDto = BorrowerMapper.INSTANCE.borrowerToBorrowerDto(savedBorrower);
        log.debug("Converted saved Borrower entity to BorrowerDto: {}", savedBorrowerDto);

        return savedBorrowerDto;
    }

    @Override
    public BorrowerDto updateBorrower(BorrowerDto borrower) {
        return null;
    }

    @Override
    public void deleteBorrower(Long borrowerId) {

    }

    @Override
    public BorrowerDto findBorrower(Long borrowerId) {
        log.info("Attempting to find borrower with ID: {}", borrowerId);

        // Find the borrower by ID from the repository
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> {
                    // Log the exception before throwing
                    String errorMessage = String.format("Borrower with ID %d not found.", borrowerId);
                    log.error(errorMessage);
                    return new DataNotFoundException("Borrower", "ID", borrowerId.toString());
                });

        // Log successful retrieval
        log.info("Found borrower with ID: {}", borrowerId);
        log.debug("Retrieved Borrower entity: {}", borrower);

        // Convert the Borrower entity to BorrowerDto
        BorrowerDto borrowerDto = BorrowerMapper.INSTANCE.borrowerToBorrowerDto(borrower);
        log.debug("Converted Borrower entity to BorrowerDto: {}", borrowerDto);

        return borrowerDto;
    }

    @Override
    public List<BorrowerDto> findAllBorrowers() {
        return List.of();
    }
}
