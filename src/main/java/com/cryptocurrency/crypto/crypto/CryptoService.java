package com.cryptocurrency.crypto.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//The service class is used to write business logic in a different layer, separated from @RestController class file
@Service
public class CryptoService {

    @Autowired
    CryptoRepository cryptoRepository;

    public List<Crypto> getAllCrypto(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Crypto> result = cryptoRepository.findAll(pageable);
        if (result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Crypto>();
        }
    }
}