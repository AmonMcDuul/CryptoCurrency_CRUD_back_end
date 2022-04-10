package com.cryptocurrency.crypto;

import com.cryptocurrency.crypto.crypto.CryptoController;
import com.cryptocurrency.crypto.crypto.Crypto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CryptoApiApplicationTests {

	@Autowired
	private CryptoController cryptoController;

	@Autowired
	private MockMvc mockMvc;

	private String toJson(final Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Test
	void contextLoads() {
		assertThat(cryptoController).isNotNull();
		assertThat(mockMvc).isNotNull();
	}


	// Test the post request. file is added with the builder class. it should return a 200 message.
	@Test
	void newCryptoCurrency() throws Exception {
		Crypto crypto = new Crypto.Builder("TEST").withName("TestCoin").withNumberOfCoins(123456).withMarketCap(987654321L).build();
		this.mockMvc.perform(post("/api/currencies")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(crypto)))
				.andExpect(status().is2xxSuccessful());
	}

	// Test the get request, checks if status 200 gets returned and checks if the sum of all coins equals 4
	// (3 cryptos on init mockMvc + 1 crypto from the test newCryptoCurrency).
	@Test
	void getAllCrypto() throws Exception {
		this.mockMvc.perform(get("/api/currencies")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$", hasSize(4)));
	}

	// Test get one coin request. Returns the coin with id:1. It should return Bitcoin.
	@Test
	void returnOneCoin() throws Exception {
		this.mockMvc.perform(get("/api/currencies/1")).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.coinName", Matchers.is("Bitcoin")));
	}

	// Test the delete request. Deleting coin with id 2. it should return a 200 message
	@Test
	void deleteCoin() throws Exception {
		this.mockMvc.perform(delete("/api/currencies/2"))
				.andExpect(status().is2xxSuccessful());
	}

	// Test the delete request. Trying to delete a coin with a non-existing id. it should return a 400 message.
	@Test
	void deleteCoinFalse() throws Exception {
		this.mockMvc.perform(delete("/api/currencies/11"))
				.andExpect(status().is4xxClientError());
	}
	// Test put request yet to be made.

}
