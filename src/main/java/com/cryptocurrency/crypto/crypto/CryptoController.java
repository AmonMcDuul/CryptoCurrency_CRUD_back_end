package com.cryptocurrency.crypto.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/currencies")
// added CrossOrigin for support for CORS (Cross-Origin Resource Sharing)
@CrossOrigin
public class CryptoController {
    private final CryptoRepository cryptoRepository;
    private static final Logger log = LoggerFactory.getLogger(CryptoController.class);

    @Autowired
    CryptoService cryptoService;
    CryptoController(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
    }

    // CRUD REQUESTS - GET - POST - PUT - DELETE
    // Get all coins
    @GetMapping()
    public List<Crypto> getAllCrypto(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort){
        log.info("GET request: Returned all coins. parameters: page: "+page+ " size: "+size+" sort: "+sort);
        return cryptoService.getAllCrypto(page, size, sort);
    }

    // Get one coin
    @GetMapping("/{id}")
    public Optional<Crypto> returnOneCoin(@PathVariable Long id) {
        log.info("GET request: Returned one coin: " + cryptoRepository.findById(id));
        return cryptoRepository.findById(id);
    }

    // Create one coin
    @PostMapping
    public ResponseEntity newCryptoCurrency(@RequestBody Crypto newCrypto){
        try{
            Crypto saveCrypto = new Crypto.Builder(newCrypto.getTicker()).withName(newCrypto.getCoinName()).withNumberOfCoins(newCrypto.getNumberOfCoins()).withMarketCap(newCrypto.getMarketCap()).build();
            cryptoRepository.save(saveCrypto);
            log.info("POST request: Made one coin: " + saveCrypto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Edit one coin
    @PutMapping("/{id}")
    Crypto editCryptoCoin(@RequestBody Crypto newCryptoCoin, @PathVariable long id) {
        return cryptoRepository.findById(id).map(crypto -> {
            if (newCryptoCoin.getTicker() != null) {
                crypto.setTicker(newCryptoCoin.getTicker());
            }
            if (newCryptoCoin.getCoinName() != null) {
                crypto.setCoinName(newCryptoCoin.getCoinName());
            }
            if (newCryptoCoin.getNumberOfCoins() != null) {
                crypto.setNumberOfCoins(newCryptoCoin.getNumberOfCoins());
            }
            if (newCryptoCoin.getMarketCap() != null) {
                crypto.setMarketCap(newCryptoCoin.getMarketCap());
            }
            log.info("PUT request: Edited one coin: " + crypto);
            return cryptoRepository.save(crypto);
        })
        .orElseGet(() -> {
            log.info("PUT request failed. Nothing has been edited");
            return null;
        });
    }

    // Delete one coin
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCoin(@PathVariable long id) {
        try{
            log.info("DELETE request: Deleted one coin: " + cryptoRepository.findById(id));
            cryptoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

