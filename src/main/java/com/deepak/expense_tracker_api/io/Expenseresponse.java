package com.deepak.expense_tracker_api.io;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expenseresponse 
{
	    private String expenseId;
	    private String name;
	    private String description;
	    private BigDecimal amount;
	    private Date date;
	    private Timestamp createdAt;
	    private Timestamp updatedAt;
	    private Categoryresponse category;
}
