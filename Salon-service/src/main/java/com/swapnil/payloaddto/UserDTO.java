package com.swapnil.payloaddto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class UserDTO {


    private long id;

    private String fullname;

    private String email;
}
