package com.deepak.expense_tracker_api.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;

import com.deepak.expense_tracker_api.io.Categoryresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO {
	private String expenseId;
    private String name;
    private String description;
    private BigDecimal amount;
    private Date date;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private CategoryDTO categorydto;
    private UserDTO userDTO;
    private String categoryid;
}
