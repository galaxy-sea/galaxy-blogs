package com.galaxy.validator.bean;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author galaxy
 * @date 2019/10/07 11:14
 */
@Data
public class FOO {
    @NotNull(message = "id不能为空")
    private String id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]*$")
    private String name;

    @NotBlank
    @Size(min = 1, max = 15)
    private String phone;

    @NotBlank
    @Email
    private String email;

    private Integer age;

}
