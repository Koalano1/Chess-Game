package com.cchess.game.cchess.matches;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DrawRequest {

    @JsonProperty("username")
    private String username;

}
