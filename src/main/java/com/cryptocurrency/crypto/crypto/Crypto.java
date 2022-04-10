package com.cryptocurrency.crypto.crypto;

import javax.persistence.*;
import java.time.LocalDate;

//Model of the cryptocurrency. This model creates the database table.
@Entity
@Table
public class Crypto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ticker")
    private String ticker;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "number_of_coins")
    private Integer numberOfCoins;
    @Column(name = "market_cap")
    private Long marketCap;


    public Crypto() {
    }

    public Crypto(Long id, String ticker, String coinName, Integer numberOfCoins, Long marketCap) {
        this.id = id;
        this.ticker = ticker;
        this.coinName = coinName;
        this.numberOfCoins = numberOfCoins;
        this.marketCap = marketCap;
    }

    public Crypto(String ticker, String coinName, Integer numberOfCoins, Long marketCap) {
        this.ticker = ticker;
        this.coinName = coinName;
        this.numberOfCoins = numberOfCoins;
        this.marketCap = marketCap;
    }

    //Getters and Setters. Could also use LOMBOK for this to reduce boilerplate code.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public Integer getNumberOfCoins() {
        return numberOfCoins;
    }

    public void setNumberOfCoins(Integer numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", coinName='" + coinName + '\'' +
                ", ticker='" + ticker + '\'' +
                ", numberOfCoins=" + numberOfCoins +
                ", marketCap=" + marketCap +
                '}';
    }

    //builder design pattern
    public static class Builder {

        @Id
        @GeneratedValue
        private Long id;
        private String ticker;
        private String coinName;
        private Integer numberOfCoins;
        private Long marketCap;

        public Builder(String ticker) {
            this.ticker = ticker;
        }

        public Builder withName(String coinName) {
            this.coinName = coinName;
            return this;
        }

        public Builder withNumberOfCoins(Integer numberOfCoins) {
            this.numberOfCoins = numberOfCoins;
            return this;
        }

        public Builder withMarketCap(Long marketCap) {
            this.marketCap = marketCap;
            return this;
        }

        public Crypto build() {
            Crypto crypto = new Crypto();
            crypto.coinName = this.coinName;
            crypto.ticker = this.ticker;
            crypto.numberOfCoins = this.numberOfCoins;
            crypto.marketCap = this.marketCap;
            return crypto;
        }
    }
}
