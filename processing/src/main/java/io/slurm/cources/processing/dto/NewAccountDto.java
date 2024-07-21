package io.slurm.cources.processing.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class NewAccountDto {

    @JsonAlias("currency")
    private String currencyCode;
    @JsonAlias("user")
    private Long userId;

}
