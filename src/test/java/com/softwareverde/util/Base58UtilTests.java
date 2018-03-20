package com.softwareverde.util;

import org.junit.Assert;
import org.junit.Test;

public class Base58UtilTests {
    @Test
    public void should_decode_base58_strings() {
        // Setup
        final String expectedOutput = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eu tempor ante. Suspendisse ultricies faucibus elementum. Vivamus id mi eget tortor pulvinar tristique nec nec lacus. Proin pharetra tellus id mollis tempus. Nullam sit amet tincidunt ex. Morbi purus massa, bibendum ornare arcu in, pharetra convallis mauris. Ut nulla nibh, malesuada id libero eget, placerat efficitur sapien. Maecenas malesuada maximus ipsum accumsan porttitor. Phasellus ullamcorper augue sodales ligula faucibus mattis. Vestibulum pellentesque tellus vel dui suscipit, sed pulvinar libero malesuada. Suspendisse potenti. Aliquam justo nibh, posuere nec pharetra sit amet, bibendum sit amet ligula. Proin dignissim malesuada orci.";
        final String input = "2ELfpZzpR9A8mdyiAbGnfWRGeoygkUe74cFULkjNCJJaAa7H2S7gqbfU8JDk3ZndPJABKYSPrk6aXBSb5WBdyFLCmZEDS83rp6L2tq24BksZWNEByMS15EcJVPSkXLUfCchMxnVzQA2ciVmpTxGPdVpBqgT4wjLc7Gtvs7ERWAQzhK4VRUKADp2wqxUoYNPcHi9EEEkc6RaZ7Ko3nnX6zuK9je8R9mUXzTXpevpE3g4JwzyRWuzKpuhPFTspoMEzw4vpAWFUj3YQshQL4ztPRd2zC719xsC131VsmD3mNwBVhduEa4z9i7nHmi81uwvwYNydCGJhtDKngvLBv4VgMDCQX89xN8etsZ7EF1euytKarRE13F6R1HZFSVP1sKkXK9ymn8JuhteXRdevRXZ8nRK79GDaMpyYFNzNCVx7L1Gh1dn5bbubD3gjywPEomee1Wp8G2pfZXnTqEDSqmA65CCoPYaUpAWi6Tzj4tjstbw4GD5cb8tz3RAZ7jUA8HxF1ZuoSedM38nAcRYEv9rfgDy47v1jRzgh7WscSP4LRBv8fxeCfASXKsNknZAnk458HBpERJravayC8EbuxNpAw6TP11WxoEow3y4h5xhNcQ4tNkToyjoriKUxAS9UCvt3vfxDqc5PuPuhyYBBZMyUyNe4KPLu8L1DhfEVtVJQrVbMYMn7Qg4iNhQ2kzyufTWspUZk1DBp1TWB5c4Lkn42JJtM4uUvCSARgF6VECQBKpXCTmX1ZerUMowVo8UxJ9dMUkogryP5auvn5XmdyuhhuykGAeH2F2gthsWBAMeEh63bMM6iaFkQNNvnGCoWSv9ohGREAvHunSP4uoygz7FouKz7TCd83G3Ex1SYgUsRNSvCmTpkmPNZGdjbc1aRZQnZY94e8weAJVMp4SRNseGUzVMX6YdohS7R6HTR8DHxpLShcEaZEqgYnrpZV4to9KuTURUG7XzHihxZb";

        // Action
        final byte[] decodedBytes = Base58Util.base58StringToByteArray(input);

        // Assert
        Assert.assertEquals(expectedOutput, new String(decodedBytes));
    }

    @Test
    public void should_decode_and_encode_the_same_base58_string() {
        // Setup
        final String input = "2ELfpZzpR9A8mdyiAbGnfWRGeoygkUe74cFULkjNCJJaAa7H2S7gqbfU8JDk3ZndPJABKYSPrk6aXBSb5WBdyFLCmZEDS83rp6L2tq24BksZWNEByMS15EcJVPSkXLUfCchMxnVzQA2ciVmpTxGPdVpBqgT4wjLc7Gtvs7ERWAQzhK4VRUKADp2wqxUoYNPcHi9EEEkc6RaZ7Ko3nnX6zuK9je8R9mUXzTXpevpE3g4JwzyRWuzKpuhPFTspoMEzw4vpAWFUj3YQshQL4ztPRd2zC719xsC131VsmD3mNwBVhduEa4z9i7nHmi81uwvwYNydCGJhtDKngvLBv4VgMDCQX89xN8etsZ7EF1euytKarRE13F6R1HZFSVP1sKkXK9ymn8JuhteXRdevRXZ8nRK79GDaMpyYFNzNCVx7L1Gh1dn5bbubD3gjywPEomee1Wp8G2pfZXnTqEDSqmA65CCoPYaUpAWi6Tzj4tjstbw4GD5cb8tz3RAZ7jUA8HxF1ZuoSedM38nAcRYEv9rfgDy47v1jRzgh7WscSP4LRBv8fxeCfASXKsNknZAnk458HBpERJravayC8EbuxNpAw6TP11WxoEow3y4h5xhNcQ4tNkToyjoriKUxAS9UCvt3vfxDqc5PuPuhyYBBZMyUyNe4KPLu8L1DhfEVtVJQrVbMYMn7Qg4iNhQ2kzyufTWspUZk1DBp1TWB5c4Lkn42JJtM4uUvCSARgF6VECQBKpXCTmX1ZerUMowVo8UxJ9dMUkogryP5auvn5XmdyuhhuykGAeH2F2gthsWBAMeEh63bMM6iaFkQNNvnGCoWSv9ohGREAvHunSP4uoygz7FouKz7TCd83G3Ex1SYgUsRNSvCmTpkmPNZGdjbc1aRZQnZY94e8weAJVMp4SRNseGUzVMX6YdohS7R6HTR8DHxpLShcEaZEqgYnrpZV4to9KuTURUG7XzHihxZb";

        // Action
        final byte[] decodedString = Base58Util.base58StringToByteArray(input);
        final String encodedBytes = Base58Util.toBase58String(decodedString);

        // Assert
        Assert.assertEquals(input, encodedBytes);
    }
}
